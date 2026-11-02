package modelo.clinica;
import modelo.mascotas.Mascota; //import: Importa la clase Mascota del paquete modelo.mascotas
import modelo.personas.Propietario; //import: Importa la clase Propietario del paquete modelo.personas
import modelo.personas.Recepcionista; //import: Importa la clase Recepcionista del paquete modelo.personas
import modelo.personas.Veterinario; //import: Importa la clase Veterinario del paquete modelo.personas
import modelo.servicios.Facturable; //import: Importa la interfaz Facturable del paquete modelo.servicios
import java.time.LocalDate; //import: Importa la clase LocalDate para buscar citas por fecha
import java.time.LocalDateTime; //import: Importa la clase LocalDateTime para agendar citas
import java.util.ArrayList; //import: Importa la clase ArrayList para crear las listas de la clinica
import java.util.List; //import: Importa la interfaz List para declarar las listas de la clinica

/**
 * Clinica veterinaria. Es el punto de entrada del modelo: registra personas y mascotas,
 * agenda las citas y calcula lo cobrado.
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public class Clinica {

    private String nombre;
    private List<Veterinario> veterinarios;
    private List<Recepcionista> recepcionistas;
    private List<Consultorio> consultorios;
    private List<Propietario> propietarios;
    private List<Mascota> mascotas;
    private List<Cita> citas;

    /**
     * Constructor que crea una clinica sin personal, mascotas ni citas
     * @param nombre Nombre de la clinica
     * @throws IllegalArgumentException si el nombre esta vacio
     */
    public Clinica(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la clinica no puede estar vacio");
        }
        this.nombre = nombre.trim();
        veterinarios = new ArrayList<>();
        recepcionistas = new ArrayList<>();
        consultorios = new ArrayList<>();
        propietarios = new ArrayList<>();
        mascotas = new ArrayList<>();
        citas = new ArrayList<>();
    }

    /**
     * Obtiene el nombre de la clinica
     * @return Nombre de la clinica
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Agrega un veterinario a la clinica. Es una agregacion: el veterinario se crea fuera
     * de la clinica y llega ya creado, por eso sigue existiendo aunque la clinica cierre.
     * @param veterinario Veterinario que se agrega
     * @throws IllegalArgumentException si es nulo o ya existe un veterinario con la misma cedula
     */
    public void agregarVeterinario(Veterinario veterinario) {
        if (veterinario == null) {
            throw new IllegalArgumentException("El veterinario no puede ser nulo");
        }
        for (Veterinario existente : veterinarios) {
            if (existente.getCedulaProfesional().equalsIgnoreCase(veterinario.getCedulaProfesional())) {
                throw new IllegalArgumentException("Ya existe un veterinario con esa cedula");
            }
        }
        veterinarios.add(veterinario);
    }

    /**
     * Agrega un recepcionista a la clinica. Es una agregacion, igual que con el veterinario.
     * @param recepcionista Recepcionista que se agrega
     * @throws IllegalArgumentException si es nulo
     */
    public void agregarRecepcionista(Recepcionista recepcionista) {
        if (recepcionista == null) {
            throw new IllegalArgumentException("El recepcionista no puede ser nulo");
        }
        recepcionistas.add(recepcionista);
    }

    /**
     * Crea un consultorio dentro de la clinica. Es una composicion: la clinica crea sus
     * consultorios y nadie mas los recibe, por eso desaparecen con ella.
     * @param numero Numero del nuevo consultorio
     * @throws IllegalArgumentException si el numero no es valido o ya existe
     */
    public void crearConsultorio(int numero) {
        for (Consultorio existente : consultorios) {
            if (existente.getNumero() == numero) {
                throw new IllegalArgumentException("Ya existe el consultorio " + numero);
            }
        }
        consultorios.add(new Consultorio(numero));
    }

    /**
     * Registra a un propietario en la clinica
     * @param propietario Propietario que se registra
     * @throws IllegalArgumentException si es nulo
     */
    public void registrarPropietario(Propietario propietario) {
        if (propietario == null) {
            throw new IllegalArgumentException("El propietario no puede ser nulo");
        }
        propietarios.add(propietario);
    }

    /**
     * Registra a una mascota en la clinica
     * @param mascota Mascota que se registra
     * @throws IllegalArgumentException si es nula o su propietario no esta registrado
     */
    public void registrarMascota(Mascota mascota) {
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no puede ser nula");
        }
        if (!propietarios.contains(mascota.getPropietario())) {
            throw new IllegalArgumentException("Primero debe registrarse al propietario de la mascota");
        }
        mascotas.add(mascota);
    }

    /**
     * Agenda una cita revisando las reglas de la clinica
     * @param mascota Mascota que sera atendida (debe estar registrada)
     * @param veterinario Veterinario que atendera (debe pertenecer a la clinica)
     * @param consultorio Consultorio que se usara (debe pertenecer a la clinica)
     * @param fechaHora Fecha y hora de la cita
     * @param servicio Servicio que se realizara
     * @return La cita agendada
     * @throws IllegalArgumentException si algun dato no es valido, si el veterinario ya tiene
     *         una cita a esa hora o si el consultorio ya esta ocupado a esa hora
     */
    public Cita agendarCita(Mascota mascota, Veterinario veterinario, Consultorio consultorio,
            LocalDateTime fechaHora, Facturable servicio) {
        validarRegistros(mascota, veterinario, consultorio);
        for (Cita existente : citas) {
            if (existente.getVeterinario() == veterinario && existente.coincideHorario(fechaHora)) {
                throw new IllegalArgumentException("El veterinario ya tiene una cita a esa hora");
            }
            if (existente.getConsultorio() == consultorio && existente.coincideHorario(fechaHora)) {
                throw new IllegalArgumentException("El consultorio ya esta ocupado a esa hora");
            }
        }
        Cita cita = new Cita(mascota, veterinario, consultorio, fechaHora, servicio);
        citas.add(cita);
        return cita;
    }

    /**
     * Busca una cita por su codigo
     * @param codigo Codigo de la cita, por ejemplo C1
     * @return La cita encontrada o null si no existe
     */
    public Cita buscarCita(String codigo) {
        for (Cita cita : citas) {
            if (cita.getCodigo().equalsIgnoreCase(codigo)) {
                return cita;
            }
        }
        return null;
    }

    /**
     * Busca las citas de un dia (sobrecarga de buscarCita)
     * @param fecha Dia que se quiere consultar
     * @return Lista con las citas de ese dia, vacia si no hay ninguna
     */
    public List<Cita> buscarCita(LocalDate fecha) {
        List<Cita> encontradas = new ArrayList<>();
        for (Cita cita : citas) {
            if (cita.getFechaHora().toLocalDate().equals(fecha)) {
                encontradas.add(cita);
            }
        }
        return encontradas;
    }

    /**
     * Calcula lo cobrado por un veterinario con sus citas atendidas
     * @param veterinario Veterinario que se consulta
     * @return Total cobrado en pesos
     */
    public double totalCobrado(Veterinario veterinario) {
        List<Facturable> servicios = new ArrayList<>();
        for (Cita cita : citas) {
            if (cita.getVeterinario() == veterinario && cita.getEstado() == EstadoCita.ATENDIDA) {
                servicios.add(cita.getServicio());
            }
        }
        return sumarCostos(servicios);
    }

    /**
     * Calcula lo cobrado por una mascota con sus citas atendidas (sobrecarga de totalCobrado)
     * @param mascota Mascota que se consulta
     * @return Total cobrado en pesos
     */
    public double totalCobrado(Mascota mascota) {
        List<Facturable> servicios = new ArrayList<>();
        for (Cita cita : citas) {
            if (cita.getMascota() == mascota && cita.getEstado() == EstadoCita.ATENDIDA) {
                servicios.add(cita.getServicio());
            }
        }
        return sumarCostos(servicios);
    }

    /**
     * Da de baja a una mascota junto con su historial y sus citas
     * @param mascota Mascota que se da de baja
     * @throws IllegalArgumentException si la mascota no esta registrada
     */
    public void darDeBajaMascota(Mascota mascota) {
        if (!mascotas.contains(mascota)) {
            throw new IllegalArgumentException("La mascota no esta registrada");
        }
        for (int i = citas.size() - 1; i >= 0; i--) {
            if (citas.get(i).getMascota() == mascota) {
                citas.remove(i);
            }
        }
        mascotas.remove(mascota);
        // Recolector de basura: al quitar la mascota de la lista y sus citas de la agenda,
        // ya no queda ninguna referencia hacia ella ni hacia su historial (que se creo dentro
        // de la mascota). Java los detecta como inalcanzables y libera su memoria
        // automaticamente en algun momento, sin que el programador tenga que borrarlos.
    }

    /**
     * Obtiene las mascotas registradas
     * @return Copia de la lista de mascotas
     */
    public List<Mascota> listarMascotas() {
        return new ArrayList<>(mascotas);
    }

    /**
     * Obtiene los veterinarios de la clinica
     * @return Copia de la lista de veterinarios
     */
    public List<Veterinario> getVeterinarios() {
        return new ArrayList<>(veterinarios);
    }

    /**
     * Obtiene los consultorios de la clinica
     * @return Copia de la lista de consultorios
     */
    public List<Consultorio> getConsultorios() {
        return new ArrayList<>(consultorios);
    }

    /**
     * Obtiene las citas agendadas
     * @return Copia de la lista de citas
     */
    public List<Cita> getCitas() {
        return new ArrayList<>(citas);
    }

    /**
     * Revisa que la mascota, el veterinario y el consultorio pertenezcan a la clinica
     * @param mascota Mascota que se revisa
     * @param veterinario Veterinario que se revisa
     * @param consultorio Consultorio que se revisa
     * @throws IllegalArgumentException si alguno no esta registrado en la clinica
     */
    private void validarRegistros(Mascota mascota, Veterinario veterinario, Consultorio consultorio) {
        if (!mascotas.contains(mascota)) {
            throw new IllegalArgumentException("La mascota no esta registrada en la clinica");
        }
        if (!veterinarios.contains(veterinario)) {
            throw new IllegalArgumentException("El veterinario no pertenece a la clinica");
        }
        if (!consultorios.contains(consultorio)) {
            throw new IllegalArgumentException("El consultorio no pertenece a la clinica");
        }
    }

    /**
     * Suma el costo de una lista de servicios. Cada servicio calcula su costo a su manera
     * (polimorfismo), sin importar si es consulta, vacunacion o cirugia.
     * @param servicios Servicios que se suman
     * @return Suma de los costos en pesos
     */
    private double sumarCostos(List<Facturable> servicios) {
        double total = 0;
        for (Facturable servicio : servicios) {
            total += servicio.calcularCosto();
        }
        return total;
    }
}
