package edu.iudigital.tecnomovil.datos;

import java.util.List;
import java.util.Map;

/**
 * Catálogo de rutas y estaciones de la red de transporte simulada.
 *
 * <p>Solo contiene datos fijos: las colecciones son inmutables y la clase
 * no se puede instanciar.
 */
public final class CatalogoRed {

    /** Nombres de las rutas, en un orden fijo. */
    public static final List<String> RUTAS = List.of(
            "Ruta Norte", "Ruta Sur", "Ruta Centro", "Ruta Oriente");

    private static final Map<String, List<String>> ESTACIONES_POR_RUTA = Map.of(
            "Ruta Norte", List.of("Portal Norte", "La Ceiba", "San José", "Centro"),
            "Ruta Sur", List.of("Portal Sur", "El Mirador", "Las Palmas", "Centro"),
            "Ruta Centro", List.of("Centro", "Plaza Mayor", "Estadio", "Universidad"),
            "Ruta Oriente", List.of("Loma Verde", "El Rosal", "San José"));

    private CatalogoRed() {
    }

    /**
     * Obtiene las estaciones de una ruta, en el orden en que se recorren.
     *
     * @param ruta nombre de la ruta
     * @return la lista de estaciones, o una lista vacía si la ruta no existe
     */
    public static List<String> estacionesDe(String ruta) {
        return ESTACIONES_POR_RUTA.getOrDefault(ruta, List.of());
    }
}