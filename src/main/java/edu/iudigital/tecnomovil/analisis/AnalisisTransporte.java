package edu.iudigital.tecnomovil.analisis;

import edu.iudigital.tecnomovil.modelo.Registro;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Operaciones funcionales de análisis sobre los registros de validación.
 *
 * <p>Todas las operaciones son funciones puras: solo dependen de la lista que
 * reciben, no la modifican y no producen efectos secundarios. Por eso se
 * pueden ejecutar también con {@code parallelStream()}.
 */
public final class AnalisisTransporte {

    private AnalisisTransporte() {
    }

    /**
     * Calcula cuántos usuarios ingresan a cada estación durante el día.
     *
     * @param registros registros de validación a analizar
     * @return mapa inmutable con la estación como clave y la cantidad de
     *         entradas como valor
     */
    public static Map<String, Long> afluenciaPorEstacion(List<Registro> registros) {
        return Map.copyOf(registros.stream()
                .filter(Registro::esEntrada)
                .collect(Collectors.groupingBy(Registro::estacion, Collectors.counting())));
    }
}