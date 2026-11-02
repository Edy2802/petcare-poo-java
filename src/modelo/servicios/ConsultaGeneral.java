package modelo.servicios;

/**
 * Servicio de consulta general. Su costo es una tarifa base.
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public class ConsultaGeneral implements Facturable {

    // static final: es una constante compartida por todas las consultas, no cambia nunca
    private static final double TARIFA_BASE = 350.0;

    private double tarifaBase;

    /**
     * Constructor por defecto, usa la tarifa base de la clinica
     */
    public ConsultaGeneral() {
        this.tarifaBase = TARIFA_BASE;
    }

    /**
     * Constructor que recibe una tarifa distinta a la base
     * @param tarifaBase Tarifa de la consulta (no puede ser negativa)
     * @throws IllegalArgumentException si la tarifa es negativa
     */
    public ConsultaGeneral(double tarifaBase) {
        if (tarifaBase < 0) {
            throw new IllegalArgumentException("La tarifa no puede ser negativa");
        }
        this.tarifaBase = tarifaBase;
    }

    /**
     * Calcula el costo de la consulta
     * @return La tarifa de la consulta
     */
    @Override
    public double calcularCosto() {
        return tarifaBase;
    }

    /**
     * Describe el servicio
     * @return El texto "Consulta general"
     */
    @Override
    public String obtenerDescripcion() {
        return "Consulta general";
    }
}
