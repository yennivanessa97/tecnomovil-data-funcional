# TecnoMovil Data

Trabajo de la Unidad 2 de la clase de programación orientada a objetos (programación funcional y manejo de repositorio).

TecnoValle tiene un sistema de transporte con troncales, alimentadores y una ruta circular, y cada vez que alguien pasa la tarjeta queda un registro. La idea de este proyecto es sacar información útil de esos registros sin modificar ningún dato y sin variables que cambien por el camino: solo funciones que reciben una lista y devuelven un resultado nuevo.

Está hecho en Java 21, sin librerías externas. Maven se usa únicamente para compilar.

## Qué responde

Seis preguntas sobre un día de operación:

1. ¿Cuánta gente entra por cada estación?
2. ¿A qué horas hay más flujo?
3. ¿Qué rutas se usan más?
4. ¿Por qué estaciones pasó cada usuario?
5. ¿Cuánto tarda el viaje entre dos estaciones?
6. ¿Qué rutas superan un umbral de ocupación?

## Cómo está organizado

El paquete base es `edu.iudigital.tecnomovil` y tiene tres carpetas y una clase suelta:

- `modelo`: los datos del problema. `Registro` es cada validación (usuario, ruta, estación, entrada o salida, y la fecha y hora). Todo son `record` y un `enum`, así que nada se puede cambiar después de creado. También están los tipos donde se guardan los resultados: `FlujoPorHora`, `UsoRuta`, `Tramo` y `RutaCritica`.
- `datos`: el catálogo de rutas y estaciones, y un generador que fabrica los registros de prueba.
- `analisis`: las seis tareas, todas en `AnalisisTransporte`. Ninguna imprime ni guarda nada; solo reciben la lista de registros y devuelven un resultado. Por eso se podrían correr también con `parallelStream()`.
- `Main`: arma los datos, llama a cada tarea y muestra los resultados. Es lo único que imprime.

## Cómo correrlo

Hace falta tener instalado el JDK 21.

```
git clone https://github.com/yennivanessa97/tecnomovil-data-funcional.git
cd tecnomovil-data-funcional
mvn compile
java -cp target/classes edu.iudigital.tecnomovil.Main
```

Si prefieres IntelliJ, abre la carpeta del proyecto y ejecuta `Main`.

## Sobre los datos

Los registros son simulados: 1000 usuarios, un viaje cada uno (una entrada y una salida), o sea 2000 registros. La semilla está fija en 42, así que cada ejecución da los mismos números.

Un par de decisiones que conviene saber para leer los resultados:

- Para contar gente solo se cuentan las entradas. Si se contaran también las salidas, cada viaje valdría doble.
- Por eso `Universidad`, que es la última estación de la Ruta Centro, no aparece en la afluencia: nadie entra ahí, solo salen.
- El umbral de la tarea de rutas críticas es el promedio de entradas por ruta (250 con estos datos). Es un valor inventado para que se vea el filtro funcionando, no una cifra real.

## Cómo se trabajó el repositorio

`main` guarda la versión estable y no se tocó hasta el final. El trabajo salió de `dev`, y cada bloque tuvo su propia rama: `feature/modelo`, `feature/datos-simulados`, `feature/analisis-funcional` y `feature/ejecucion`. Todas se unieron con `git merge --no-ff` para que el historial muestre cuándo se abrió y se cerró cada una. Se ve con:

```
git log --oneline --graph --all
```

---

Yenni Vanessa Delgado
