package edu.iudigital.tecnomovil.modelo;

/**
 * Representa el recorrido entre dos estaciones consecutivas de una ruta.
 *
 * @param origen  estación de partida del tramo
 * @param destino estación de llegada del tramo
 */
public record Tramo(String origen, String destino) {

    /**
     * Describe el tramo en un texto legible.
     *
     * @return el tramo en formato "Portal Norte hacia La Ceiba"
     */
    public String etiqueta() {
        return origen + " hacia " + destino;
    }
}