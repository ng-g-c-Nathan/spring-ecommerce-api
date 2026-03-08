package com.example.tiendita.service;

import com.example.tiendita.DTO.ProductoConVSM;
import com.example.tiendita.DTO.ProductoVSMResponse;
import com.example.tiendita.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VsmService {

    private final ProductoRepository productoRepository;

    public VsmService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // -------------------------
    // TF
    // -------------------------
    private Map<String, Double> calcularTf(List<String> documento) {

        Map<String, Long> conteo = documento.stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        int total = documento.size();

        Map<String, Double> tf = new HashMap<>();

        conteo.forEach((termino, count) ->
                tf.put(termino, count / (double) total)
        );

        return tf;
    }

    // -------------------------
    // TF-IDF
    // -------------------------
    private Map<String, Double> calcularTfidf(List<String> documento,
                                              Map<String, Double> idf) {

        Map<String, Double> tf = calcularTf(documento);
        Map<String, Double> tfidf = new HashMap<>();

        tf.forEach((termino, valorTf) ->
                tfidf.put(termino, valorTf * idf.getOrDefault(termino, 0.0))
        );

        return tfidf;
    }

    // -------------------------
    // Coseno
    // -------------------------
    private double similitudCoseno(Map<String, Double> v1,
                                   Map<String, Double> v2) {

        double productoPunto = 0.0;

        for (String key : v1.keySet()) {
            if (v2.containsKey(key)) {
                productoPunto += v1.get(key) * v2.get(key);
            }
        }

        double mag1 = Math.sqrt(
                v1.values().stream().mapToDouble(v -> v * v).sum()
        );

        double mag2 = Math.sqrt(
                v2.values().stream().mapToDouble(v -> v * v).sum()
        );

        if (mag1 == 0 || mag2 == 0) return 0.0;

        return productoPunto / (mag1 * mag2);
    }

    // -------------------------
    // Método principal
    // -------------------------
    public List<ProductoVSMResponse> buscarVsm(String consulta) {

        consulta = consulta.toLowerCase();

        List<String> consultaArray = Arrays.stream(consulta.split("[\\s,]+"))
                .map(this::limpiar)
                .filter(s -> !s.isBlank())
                .toList();

        List<ProductoConVSM> filas = productoRepository.obtenerProductosParaVsm();

        List<List<String>> corpus = new ArrayList<>();
        List<ProductoVSMResponse> productos = new ArrayList<>();

        for (ProductoConVSM row : filas) {

            String texto =
                    (row.getNombre() + " " +
                            row.getDescripcion() + " " +
                            row.getCategoria() + " " +
                            row.getComentarios()).toLowerCase();

            List<String> palabras = Arrays.stream(texto.split("\\s+"))
                    .map(this::limpiar)
                    .filter(s -> !s.isBlank())
                    .toList();

            corpus.add(palabras);

            ProductoVSMResponse dto = new ProductoVSMResponse();
            dto.setIdProducto(row.getIdProducto());
            dto.setNombre(row.getNombre());
            dto.setDescripcion(row.getDescripcion());
            dto.setCategoria(row.getCategoria());
            dto.setComentarios(row.getComentarios());
            dto.setPrecio(row.getPrecio());
            productos.add(dto);
        }

        int numDocumentos = corpus.size();

        // -------------------------
        // Conteo documentos por término
        // -------------------------
        Map<String, Integer> conteoDocs = new HashMap<>();

        for (List<String> doc : corpus) {
            Set<String> unicos = new HashSet<>(doc);
            for (String t : unicos) {
                conteoDocs.merge(t, 1, Integer::sum);
            }
        }

        // -------------------------
        // IDF
        // -------------------------
        Map<String, Double> idf = new HashMap<>();

        conteoDocs.forEach((termino, count) -> {
            idf.put(termino, Math.log(numDocumentos / (double) (1 + count)));
        });

        // -------------------------
        // TF-IDF consulta
        // -------------------------
        Map<String, Double> consultaTfidf =
                calcularTfidf(consultaArray, idf);

        // -------------------------
        // Cada documento
        // -------------------------
        for (int i = 0; i < corpus.size(); i++) {

            Map<String, Double> docTfidf =
                    calcularTfidf(corpus.get(i), idf);

            double sim = similitudCoseno(consultaTfidf, docTfidf);

            productos.get(i).setSimilitud(sim);
        }

        // -------------------------
        // Ordenar
        // -------------------------
        productos.sort(
                Comparator.comparingDouble(ProductoVSMResponse::getSimilitud)
                        .reversed()
        );

        return productos;
    }

    private String limpiar(String palabra) {
        return palabra.replaceAll("[^a-z0-9áéíóúüñ]", "");
    }

    public List<ProductoVSMResponse> obtenerRelacionados(Long idProducto) {

        //Obtenemos todos los productos con comentarios
        List<ProductoConVSM> corpusProductos = productoRepository.obtenerProductosParaVsm();

        //Obtenemos el producto principal
        ProductoConVSM productoBase = productoRepository.obtenerProductoPorId(idProducto);
        if (productoBase == null) return Collections.emptyList();

        //Preparamos la "consulta" como si fuera un documento VSM
        String textoBase = (productoBase.getNombre() + " " +
                productoBase.getDescripcion() + " " +
                productoBase.getCategoria() + " " +
                productoBase.getComentarios()).toLowerCase();

        List<String> consultaArray = Arrays.stream(textoBase.split("\\s+"))
                .map(this::limpiar)
                .filter(s -> !s.isBlank())
                .toList();

        //Creamos corpus de documentos y lista de DTO
        List<List<String>> corpus = new ArrayList<>();
        List<ProductoVSMResponse> productosDTO = new ArrayList<>();

        for (ProductoConVSM p : corpusProductos) {
            // Ignorar el producto base
            if (p.getIdProducto().equals(idProducto)) continue;

            String texto = (p.getNombre() + " " + p.getDescripcion() + " " +
                    p.getCategoria() + " " + p.getComentarios()).toLowerCase();

            List<String> palabras = Arrays.stream(texto.split("\\s+"))
                    .map(this::limpiar)
                    .filter(s -> !s.isBlank())
                    .toList();

            corpus.add(palabras);

            ProductoVSMResponse dto = new ProductoVSMResponse();
            dto.setIdProducto(p.getIdProducto());
            dto.setNombre(p.getNombre());
            dto.setDescripcion(p.getDescripcion());
            dto.setCategoria(p.getCategoria());
            dto.setComentarios(p.getComentarios());
            dto.setPrecio(p.getPrecio());
            productosDTO.add(dto);
        }

        // Calculamos IDF
        int numDocumentos = corpus.size();
        Map<String, Integer> conteoDocs = new HashMap<>();
        for (List<String> doc : corpus) {
            Set<String> unicos = new HashSet<>(doc);
            for (String t : unicos) {
                conteoDocs.merge(t, 1, Integer::sum);
            }
        }

        Map<String, Double> idf = new HashMap<>();
        conteoDocs.forEach((termino, count) -> {
            idf.put(termino, Math.log(numDocumentos / (double) (1 + count)));
        });

        //TF-IDF consulta
        Map<String, Double> consultaTfidf = this.calcularTfidf(consultaArray, idf);

        //Calculamos similitudes
        for (int i = 0; i < corpus.size(); i++) {
            Map<String, Double> docTfidf = this.calcularTfidf(corpus.get(i), idf);
            double sim = this.similitudCoseno(consultaTfidf, docTfidf);
            productosDTO.get(i).setSimilitud(sim);
        }

        //Ordenamos por similitud y limitamos a 4
        return productosDTO.stream()
                .sorted(Comparator.comparingDouble(ProductoVSMResponse::getSimilitud)
                        .reversed())
                .limit(4)
                .toList();
    }
}
