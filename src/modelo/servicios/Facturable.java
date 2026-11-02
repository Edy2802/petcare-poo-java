package modelo.servicios;

/**
 * Interfaz que define lo que debe saber hacer todo servicio que se cobra en la clinica
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public interface Facturable {

    /**
     * Calcula el costo del servicio
     * @return Costo del servicio en pesos
     */
    double calcularCosto();

    /**
     * Describe el servicio
     * @return Descripcion corta del servicio
     */
    String obtenerDescripcion();
}
