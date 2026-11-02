package modelo.servicios;

/**
 * Servicio de vacunacion. Su costo es la tarifa base mas el costo de la vacuna.
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public class Vacunacion implements Facturable {

    // static final: tarifa base que se cobra siempre por aplicar una vacuna
    private static final double TARIFA_BASE = 350.0;

    private String nombreVacuna;
    private double costoVacuna;

    /**
     * Constructor con los datos de la vacunacion
     * @param nombreVacuna Nombre de la vacuna que se aplica
     * @param costoVacuna Costo de la vacuna (no puede ser negativo)
     * @throws IllegalArgumentException si el nombre esta vacio o el costo es negativo
     */
    public Vacunacion(String nombreVacuna, double costoVacuna) {
        if (nombreVacuna == null || nombreVacuna.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la vacuna no puede estar vacio");
        }
        if (costoVacuna < 0) {
            throw new IllegalArgumentException("El costo de la vacuna no puede ser negativo");
        }
        this.nombreVacuna = nombreVacuna.trim();
        this.costoVacuna = costoVacuna;
    }

    /**
     * Calcula el costo de la vacunacion
     * @return Tarifa base mas el costo de la vacuna
     */
    @Override
    public double calcularCosto() {
        return TARIFA_BASE + costoVacuna;
    }

    /**
     * Describe el servicio
     * @return El texto "Vacunacion" con el nombre de la vacuna
     */
    @Override
    public String obtenerDescripcion() {
        return "Vacunacion (" + nombreVacuna + ")";
    }
}
