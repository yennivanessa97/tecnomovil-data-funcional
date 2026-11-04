package edu.iudigital.tecnomovil.modelo;

/**
 * Representa una ruta cuya ocupación superó el umbral permitido.
 *
 * @param ruta      nombre de la ruta marcada como crítica
 * @param ocupacion ocupación registrada en la ruta
 * @param umbral    límite de ocupación que la ruta superó
 */
public record RutaCritica(String ruta, long ocupacion, long umbral) {

    /**
     * Calcula cuánto excede la ocupación al umbral, en porcentaje.
     *
     * @return el exceso sobre el umbral; por ejemplo, 25.0 si la ocupación
     *         es un 25 % mayor que el umbral
     */
    public double porcentajeSobreUmbral() {
        return ((double) ocupacion / umbral - 1) * 100;
    }
}