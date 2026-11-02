package modelo.clinica;
import modelo.mascotas.Mascota; //import: Importa la clase Mascota del paquete modelo.mascotas
import modelo.personas.Veterinario; //import: Importa la clase Veterinario del paquete modelo.personas
import modelo.servicios.Facturable; //import: Importa la interfaz Facturable del paquete modelo.servicios
import java.time.LocalDateTime; //import: Importa la clase LocalDateTime para guardar la fecha y la hora
import java.time.format.DateTimeFormatter; //import: Importa la clase DateTimeFormatter para dar formato a la fecha

/**
 * Cita que relaciona una mascota, un veterinario y un consultorio en una fecha y hora
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public class Cita {

    // static: el contador es de la clase y se comparte entre todas las citas,
    // asi cada cita nueva recibe un codigo distinto
    private static int contadorCitas = 0;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private String codigo;
    private Mascota mascota;
    private Veterinario veterinario;
    private Consultorio consultorio;
    private LocalDateTime fechaHora;
    private EstadoCita estado;
    private Facturable servicio;

    /**
     * Constructor con los datos de la cita. La cita siempre inicia como PROGRAMADA.
     * @param mascota Mascota que sera atendida
     * @param veterinario Veterinario que atendera la cita
     * @param consultorio Consultorio donde se realizara la cita
     * @param fechaHora Fecha y hora de la cita
     * @param servicio Servicio que se realizara (consulta, vacunacion o cirugia)
     * @throws IllegalArgumentException si algun dato es nulo
     */
    public Cita(Mascota mascota, Veterinario veterinario, Consultorio consultorio,
            LocalDateTime fechaHora, Facturable servicio) {
        if (mascota == null || veterinario == null || consultorio == null
                || fechaHora == null || servicio == null) {
            throw new IllegalArgumentException("Todos los datos de la cita son obligatorios");
        }
        contadorCitas++;
        this.codigo = "C" + contadorCitas;
        this.mascota = mascota;
        this.veterinario = veterinario;
        this.consultorio = consultorio;
        this.fechaHora = fechaHora;
        this.servicio = servicio;
        this.estado = EstadoCita.PROGRAMADA;
    }

    /**
     * Obtiene el codigo de la cita
     * @return Codigo de la cita, por ejemplo C1
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Obtiene la mascota de la cita
     * @return Mascota que sera atendida
     */
    public Mascota getMascota() {
        return mascota;
    }

    /**
     * Obtiene el veterinario de la cita
     * @return Veterinario que atendera la cita
     */
    public Veterinario getVeterinario() {
        return veterinario;
    }

    /**
     * Obtiene el consultorio de la cita
     * @return Consultorio donde se realizara la cita
     */
    public Consultorio getConsultorio() {
        return consultorio;
    }

    /**
     * Obtiene la fecha y la hora de la cita
     * @return Fecha y hora de la cita
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Obtiene el estado de la cita
     * @return Estado actual de la cita
     */
    public EstadoCita getEstado() {
        return estado;
    }

    /**
     * Obtiene el servicio de la cita
     * @return Servicio que se realizara
     */
    public Facturable getServicio() {
        return servicio;
    }

    /**
     * Marca la cita como atendida y la guarda en el historial de la mascota
     * @throws IllegalStateException si la cita ya fue atendida o esta cancelada
     */
    public void atender() {
        if (estado == EstadoCita.CANCELADA) {
            throw new IllegalStateException("Una cita cancelada no puede pasar a atendida");
        }
        if (estado == EstadoCita.ATENDIDA) {
            throw new IllegalStateException("La cita ya fue atendida");
        }
        estado = EstadoCita.ATENDIDA;
        mascota.getHistorial().agregarRegistro(this);
    }

    /**
     * Cancela la cita
     * @throws IllegalStateException si la cita ya fue atendida
     */
    public void cancelar() {
        if (estado == EstadoCita.ATENDIDA) {
            throw new IllegalStateException("Una cita atendida no puede cancelarse");
        }
        estado = EstadoCita.CANCELADA;
    }

    /**
     * Genera el comprobante de cobro de la cita
     * @return Comprobante con el total del servicio
     * @throws IllegalStateException si la cita todavia no fue atendida
     */
    public Comprobante generarComprobante() {
        if (estado != EstadoCita.ATENDIDA) {
            throw new IllegalStateException("Solo se puede generar comprobante de una cita atendida");
        }
        return new Comprobante(this);
    }

    /**
     * Indica si la cita ocupa una fecha y hora determinada.
     * No lleva modificador (acceso de paquete): solo la usa la clase Clinica, que esta en el
     * mismo paquete, para revisar choques de horario. Las clases de otros paquetes no la necesitan.
     * @param otraFecha Fecha y hora que se quiere comparar
     * @return true si la cita no esta cancelada y tiene esa misma fecha y hora
     */
    boolean coincideHorario(LocalDateTime otraFecha) {
        return estado != EstadoCita.CANCELADA && fechaHora.equals(otraFecha);
    }

    /**
     * Representacion en texto de la cita
     * @return Datos principales de la cita
     */
    @Override
    public String toString() {
        return "Cita " + codigo + " | Mascota: " + mascota.getNombre()
                + " | Veterinario: " + veterinario.getNombreCompleto()
                + " | " + consultorio + " | " + fechaHora.format(FORMATO_FECHA)
                + " | " + servicio.obtenerDescripcion() + " | " + estado;
    }
}
