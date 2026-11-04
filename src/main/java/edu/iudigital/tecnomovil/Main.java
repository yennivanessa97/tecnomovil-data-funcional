package edu.iudigital.tecnomovil;

import edu.iudigital.tecnomovil.analisis.AnalisisTransporte;
import edu.iudigital.tecnomovil.datos.CatalogoRed;
import edu.iudigital.tecnomovil.datos.GeneradorRegistros;
import edu.iudigital.tecnomovil.modelo.Registro;
import edu.iudigital.tecnomovil.modelo.Tramo;

import java.util.List;
import java.util.Map;

/**
 * Punto de entrada: genera los datos simulados, ejecuta las seis tareas de
 * análisis y muestra los resultados en consola.
 *
 * <p>Imprimir es un efecto secundario, por eso se hace únicamente aquí, en el
 * borde del programa. Las funciones de análisis siguen siendo puras.
 */
public final class Main {

    private static final int USUARIOS = 1000;
    private static final long SEMILLA = 42L;

    private Main() {
    }

    public static void main(String[] args) {
        List<Registro> registros = GeneradorRegistros.generar(USUARIOS, SEMILLA);
        System.out.println("Registros generados: " + registros.size());

        System.out.println("\n== a) Afluencia por estación ==");
        AnalisisTransporte.afluenciaPorEstacion(registros).entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(par -> System.out.printf("%-15s %d entradas%n", par.getKey(), par.getValue()));

        System.out.println("\n== b) Horas pico (3 de mayor flujo) ==");
        AnalisisTransporte.horasPico(registros, 3)
                .forEach(f -> System.out.printf("%s  %d usuarios%n", f.etiqueta(), f.flujo()));

        System.out.println("\n== c) Rutas más utilizadas ==");
        AnalisisTransporte.rutasMasUtilizadas(registros, 4)
                .forEach(u -> System.out.printf("%-15s %d validaciones%n", u.ruta(), u.validaciones()));

        System.out.println("\n== d) Patrones de viaje (primeros 5 usuarios) ==");
        AnalisisTransporte.patronesDeViaje(registros).entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .limit(5)
                .forEach(par -> System.out.println(par.getKey() + " -> " + par.getValue()));

        System.out.println("\n== e) Tiempo promedio entre estaciones (5 tramos más lentos) ==");
        AnalisisTransporte.tiempoPromedioEntreEstaciones(registros).entrySet().stream()
                .sorted(Map.Entry.<Tramo, Double>comparingByValue().reversed())
                .limit(5)
                .forEach(par -> System.out.printf("%-35s %.1f min%n", par.getKey().etiqueta(), par.getValue()));

        // Umbral simulado: el promedio de entradas por ruta
        long entradas = registros.stream().filter(Registro::esEntrada).count();
        long umbral = entradas / CatalogoRed.RUTAS.size();

        System.out.println("\n== f) Rutas críticas (umbral = " + umbral + " entradas) ==");
        List<?> criticas = AnalisisTransporte.rutasCriticas(registros, umbral);
        if (criticas.isEmpty()) {
            System.out.println("Ninguna ruta supera el umbral.");
        }
        AnalisisTransporte.rutasCriticas(registros, umbral).forEach(r ->
                System.out.printf("%-15s ocupación %d (umbral %d, +%.1f%%)%n",
                        r.ruta(), r.ocupacion(), r.umbral(), r.porcentajeSobreUmbral()));
    }
}