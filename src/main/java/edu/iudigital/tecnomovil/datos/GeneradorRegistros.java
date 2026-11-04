package edu.iudigital.tecnomovil.datos;

import edu.iudigital.tecnomovil.modelo.Accion;
import edu.iudigital.tecnomovil.modelo.Registro;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Genera registros de validación simulados para una jornada de transporte.
 *
 * <p>Usa una semilla fija, de modo que los mismos parámetros producen
 * siempre los mismos registros y los resultados se pueden repetir.
 */
public final class GeneradorRegistros {

    private static final LocalDate DIA = LocalDate.of(2026, 10, 26);

    /**
     * Horas en que suelen empezar los viajes. Las horas repetidas ocurren
     * con más frecuencia, lo que crea horas pico en la mañana y en la tarde.
     */
    private static final List<Integer> HORAS_DE_INICIO = List.of(
            5, 6, 6, 7, 7, 7, 7, 8, 8, 9, 10, 12, 13, 14, 16, 17, 17, 17, 18, 18, 19);

    private GeneradorRegistros() {
    }

    /**
     * Genera los registros de una jornada: un viaje por usuario.
     *
     * @param cantidadUsuarios número de usuarios que viajan ese día
     * @param semilla          valor que fija la secuencia aleatoria
     * @return lista inmutable con una entrada y una salida por cada viaje
     */
    public static List<Registro> generar(int cantidadUsuarios, long semilla) {
        Random aleatorio = new Random(semilla);
        List<Registro> registros = new ArrayList<>();

        for (int i = 0; i < cantidadUsuarios; i++) {
            String usuario = String.format("U-%05d", i);
            String ruta = CatalogoRed.RUTAS.get(aleatorio.nextInt(CatalogoRed.RUTAS.size()));
            List<String> estaciones = CatalogoRed.estacionesDe(ruta);

            int origen = aleatorio.nextInt(estaciones.size() - 1);
            int destino = origen + 1 + aleatorio.nextInt(estaciones.size() - 1 - origen);

            int hora = HORAS_DE_INICIO.get(aleatorio.nextInt(HORAS_DE_INICIO.size()));
            LocalDateTime entrada = DIA.atTime(hora, aleatorio.nextInt(60));
            LocalDateTime salida = entrada.plusMinutes(5 + 7L * (destino - origen));

            registros.add(new Registro(usuario, ruta, estaciones.get(origen), Accion.ENTRADA, entrada));
            registros.add(new Registro(usuario, ruta, estaciones.get(destino), Accion.SALIDA, salida));
        }
        return List.copyOf(registros);
    }
}