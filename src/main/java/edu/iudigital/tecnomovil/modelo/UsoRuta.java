package edu.iudigital.tecnomovil.modelo;

/**
 * Resume el volumen de uso de una ruta de transporte.
 *
 * @param ruta         nombre de la ruta
 * @param validaciones cantidad de validaciones registradas en esa ruta
 */
public record UsoRuta(String ruta, long validaciones) {
}