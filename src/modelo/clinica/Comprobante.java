package modelo.clinica;
import java.time.LocalDateTime; //import: Importa la clase LocalDateTime para guardar la fecha de emision
import java.time.format.DateTimeFormatter; //import: Importa la clase DateTimeFormatter para dar formato a la fecha

/**
 * Comprobante de cobro de una cita atendida
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public class Comprobante {

    // static: el contador es de la clase y se comparte entre todos los comprobantes,
    // asi cada comprobante nuevo recibe el siguiente numero consecutivo
    private static int contadorComprobantes = 0;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private int numero;
    private double total;
    private LocalDateTime fechaEmision;

    /**
     * Constructor que genera el comprobante de una cita
     * @param cita Cita atendida de la que se toma el total a cobrar
     * @throws IllegalArgumentException si la cita es nula
     */
    public Comprobante(Cita cita) {
        if (cita == null) {
            throw new IllegalArgumentException("La cita no puede ser nula");
        }
        contadorComprobantes++;
        this.numero = contadorComprobantes;
        this.total = cita.getServicio().calcularCosto();
        this.fechaEmision = LocalDateTime.now();
    }

    /**
     * Obtiene el numero del comprobante
     * @return Numero consecutivo del comprobante
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Obtiene el total a cobrar
     * @return Total del comprobante en pesos
     */
    public double getTotal() {
        return total;
    }

    /**
     * Obtiene la fecha en que se emitio el comprobante
     * @return Fecha y hora de emision
     */
    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    /**
     * Representacion en texto del comprobante
     * @return Numero, fecha de emision y total
     */
    @Override
    public String toString() {
        return "Comprobante No. " + numero + " | Fecha de emision: " + fechaEmision.format(FORMATO_FECHA)
                + " | Total: $" + String.format("%.2f", total);
    }
}
