package modelo.servicios;

/**
 * Servicio de cirugia. Su costo depende de las horas de quirofano y de la complejidad.
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public class Cirugia implements Facturable {

    // static final: costo por cada hora de quirofano, es igual para todas las cirugias
    private static final double COSTO_HORA_QUIROFANO = 800.0;

    private int complejidad;
    private double horasQuirofano;

    /**
     * Constructor con los datos de la cirugia
     * @param complejidad Complejidad de la cirugia (de 1 a 3)
     * @param horasQuirofano Horas de quirofano (mayor que cero)
     * @throws IllegalArgumentException si la complejidad o las horas no son validas
     */
    public Cirugia(int complejidad, double horasQuirofano) {
        if (complejidad < 1 || complejidad > 3) {
            throw new IllegalArgumentException("La complejidad debe estar entre 1 y 3");
        }
        if (horasQuirofano <= 0) {
            throw new IllegalArgumentException("Las horas de quirofano deben ser mayores que cero");
        }
        this.complejidad = complejidad;
        this.horasQuirofano = horasQuirofano;
    }

    /**
     * Calcula el costo de la cirugia
     * @return Horas de quirofano por el costo de la hora por la complejidad
     */
    @Override
    public double calcularCosto() {
        return horasQuirofano * COSTO_HORA_QUIROFANO * complejidad;
    }

    /**
     * Describe el servicio
     * @return El texto "Cirugia" con su complejidad y sus horas
     */
    @Override
    public String obtenerDescripcion() {
        return "Cirugia (complejidad " + complejidad + ", " + horasQuirofano + " horas)";
    }
}
