package edu.iudigital.tecnomovil.modelo;


/**
 * Resume el flujo de usuarios durante una hora del día.
 *
 * @param hora  hora del día, de 0 a 23
 * @param flujo cantidad de validaciones registradas en esa hora
 */

public record FlujoPorHora(int hora, long flujo) {
    /**
     * Describe la franja horaria que cubre el resumen.
     *
     * @return la franja en formato "07:00 a 07:59"
     */
    public String etiqueta() {
        return String.format("%02d:00 a %02d:59", hora, hora);
    }


}
