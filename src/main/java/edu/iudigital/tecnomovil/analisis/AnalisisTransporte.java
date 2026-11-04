package edu.iudigital.tecnomovil.analisis;

import edu.iudigital.tecnomovil.modelo.Registro;

import edu.iudigital.tecnomovil.modelo.FlujoPorHora;
import edu.iudigital.tecnomovil.modelo.UsoRuta;

import java.util.Comparator;

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
    /**
     * Calcula el flujo de usuarios en cada hora del día.
     *
     * @param registros registros de validación a analizar
     * @return lista inmutable de franjas horarias, ordenada por hora, con la
     *         cantidad de entradas de cada una
     */
    public static List<FlujoPorHora> flujoPorHora(List<Registro> registros) {
        return registros.stream()
                .filter(Registro::esEntrada)
                .collect(Collectors.groupingBy(Registro::hora, Collectors.counting()))
                .entrySet().stream()
                .map(par -> new FlujoPorHora(par.getKey(), par.getValue()))
                .sorted(Comparator.comparingInt(FlujoPorHora::hora))
                .toList();
    }

    /**
     * Identifica las horas de mayor flujo de usuarios.
     *
     * @param registros registros de validación a analizar
     * @param cuantas   cantidad de horas pico a devolver
     * @return lista inmutable con las franjas de mayor flujo, de la más
     *         concurrida a la menos concurrida
     */
    public static List<FlujoPorHora> horasPico(List<Registro> registros, int cuantas) {
        return flujoPorHora(registros).stream()
                .sorted(Comparator.comparingLong(FlujoPorHora::flujo).reversed())
                .limit(cuantas)
                .toList();
    }

    /**
     * Determina las rutas con mayor volumen de uso.
     *
     * @param registros registros de validación a analizar
     * @param cuantas   cantidad de rutas a devolver
     * @return lista inmutable con las rutas más utilizadas, de la más usada
     *         a la menos usada; si dos rutas empatan, se ordenan por nombre
     */
    public static List<UsoRuta> rutasMasUtilizadas(List<Registro> registros, int cuantas) {
        return registros.stream()
                .filter(Registro::esEntrada)
                .collect(Collectors.groupingBy(Registro::ruta, Collectors.counting()))
                .entrySet().stream()
                .map(par -> new UsoRuta(par.getKey(), par.getValue()))
                .sorted(Comparator.comparingLong(UsoRuta::validaciones).reversed()
                        .thenComparing(UsoRuta::ruta))
                .limit(cuantas)
                .toList();
    }

    /**
     * Genera, para cada usuario, la lista de estaciones visitadas en el orden
     * en que aparecen en los registros.
     *
     * @param registros registros de validación a analizar
     * @return mapa inmutable con el identificador del usuario como clave y la
     *         lista inmutable de sus estaciones como valor
     */
    public static Map<String, List<String>> patronesDeViaje(List<Registro> registros) {
        return Map.copyOf(registros.stream()
                .collect(Collectors.groupingBy(
                        Registro::idUsuario,
                        Collectors.mapping(Registro::estacion, Collectors.toUnmodifiableList()))));
    }

}