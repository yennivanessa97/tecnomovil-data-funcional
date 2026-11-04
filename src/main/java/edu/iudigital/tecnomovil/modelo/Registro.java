package edu.iudigital.tecnomovil.modelo;

import java.time.LocalDateTime;

/**
 * Representa una validación de un usuario en una estación del sistema de
 * transporte, ya sea al entrar o al salir de ella.
 *
 * <p>Es un registro inmutable: una vez creado, sus datos no se pueden modificar.
 *
 * @param idUsuario identificador del usuario que realizó la validación
 * @param ruta      ruta en la que viaja el usuario
 * @param estacion  estación donde se valida la acción
 * @param accion    tipo de validación: entrada o salida de la estación
 * @param timestamp fecha y hora exactas en que ocurrió la validación
 */
public record Registro(String idUsuario, String ruta, String estacion, Accion accion, LocalDateTime timestamp) {

    /**
     * Indica si la validación corresponde a una entrada a la estación.
     *
     * @return {@code true} si la acción es {@link Accion#ENTRADA}
     */

    public boolean esEntrada() {
        return accion == Accion.ENTRADA;
    }

    /**
     * Indica si la validación corresponde a una salida de la estación.
     *
     * @return {@code true} si la acción es {@link Accion#SALIDA}
     */

    public boolean esSalida() {
        return accion == Accion.SALIDA;
    }

    /**
     * Obtiene la hora del día en que ocurrió la validación.
     *
     * @return la hora, en un rango de 0 a 23
     */

    public int hora(){
        return timestamp.getHour();
    }


}
