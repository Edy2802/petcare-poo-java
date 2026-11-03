package app;
import modelo.clinica.Cita; //import: Importa la clase Cita del paquete modelo.clinica
import modelo.clinica.Clinica; //import: Importa la clase Clinica del paquete modelo.clinica
import modelo.clinica.Comprobante; //import: Importa la clase Comprobante del paquete modelo.clinica
import modelo.clinica.Consultorio; //import: Importa la clase Consultorio del paquete modelo.clinica
import modelo.mascotas.AveExotica; //import: Importa la clase AveExotica del paquete modelo.mascotas
import modelo.mascotas.Gato; //import: Importa la clase Gato del paquete modelo.mascotas
import modelo.mascotas.Mascota; //import: Importa la clase Mascota del paquete modelo.mascotas
import modelo.mascotas.Perro; //import: Importa la clase Perro del paquete modelo.mascotas
import modelo.personas.Propietario; //import: Importa la clase Propietario del paquete modelo.personas
import modelo.personas.Veterinario; //import: Importa la clase Veterinario del paquete modelo.personas
import modelo.servicios.Cirugia; //import: Importa la clase Cirugia del paquete modelo.servicios
import modelo.servicios.ConsultaGeneral; //import: Importa la clase ConsultaGeneral del paquete modelo.servicios
import modelo.servicios.Facturable; //import: Importa la interfaz Facturable del paquete modelo.servicios
import modelo.servicios.Vacunacion; //import: Importa la clase Vacunacion del paquete modelo.servicios
import java.time.LocalDateTime; //import: Importa la clase LocalDateTime para leer fechas y horas
import java.time.format.DateTimeFormatter; //import: Importa la clase DateTimeFormatter para leer fechas con formato
import java.time.format.DateTimeParseException; //import: Importa la excepcion que ocurre cuando una fecha no tiene el formato correcto
import java.util.List; //import: Importa la interfaz List para trabajar con listas
import java.util.Scanner; //import: Importa la clase Scanner para leer datos del teclado

/**
 * Menu de consola que permite usar el sistema de la clinica. Solo se encarga de pedir
 * datos al usuario y de mostrar resultados; las reglas estan en las clases del modelo.
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public class MenuConsola {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Clinica clinica;
    private Scanner teclado;

    /**
     * Constructor del menu
     * @param clinica Clinica con la que trabaja el menu
     */
    public MenuConsola(Clinica clinica) {
        this.clinica = clinica;
        this.teclado = new Scanner(System.in);
    }

    /**
     * Muestra el menu y ejecuta las opciones hasta que el usuario elige salir
     */
    public void iniciar() {
        int opcion = 0;
        while (opcion != 10) {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");
            try {
                ejecutarOpcion(opcion);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Muestra las opciones del menu
     */
    private void mostrarMenu() {
        System.out.println();
        System.out.println("=============== " + clinica.getNombre() + " ===============");
        System.out.println("1. Registrar propietario y mascota");
        System.out.println("2. Registrar veterinario");
        System.out.println("3. Agendar cita");
        System.out.println("4. Atender o cancelar cita");
        System.out.println("5. Generar comprobante");
        System.out.println("6. Consultar historial de una mascota");
        System.out.println("7. Listar mascotas con su sonido");
        System.out.println("8. Ver total cobrado");
        System.out.println("9. Dar de baja una mascota");
        System.out.println("10. Salir");
        System.out.println("==========================================");
    }

    /**
     * Ejecuta la opcion elegida del menu
     * @param opcion Numero de la opcion elegida
     */
    private void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarPropietarioYMascota();
                break;
            case 2:
                registrarVeterinario();
                break;
            case 3:
                agendarCita();
                break;
            case 4:
                atenderOCancelarCita();
                break;
            case 5:
                generarComprobante();
                break;
            case 6:
                consultarHistorial();
                break;
            case 7:
                listarMascotas();
                break;
            case 8:
                verTotalCobrado();
                break;
            case 9:
                darDeBajaMascota();
                break;
            case 10:
                System.out.println("Gracias por usar PetCare. Hasta pronto.");
                break;
            default:
                System.out.println("Opcion no valida, elija un numero del 1 al 10.");
        }
    }

    /**
     * Opcion 1: registra a un propietario y a su mascota
     */
    private void registrarPropietarioYMascota() {
        Propietario propietario = crearPropietario();
        Mascota mascota = crearMascota(propietario);
        clinica.registrarPropietario(propietario);
        clinica.registrarMascota(mascota);
        System.out.println("Registrado: " + mascota);
    }

    /**
     * Pide los datos de un propietario
     * @return Propietario creado con los datos capturados
     */
    private Propietario crearPropietario() {
        System.out.println("--- Datos del propietario ---");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String telefono = leerTexto("Telefono: ");
        String correo = leerTexto("Correo: ");
        String direccion = leerTexto("Direccion: ");
        return new Propietario(nombre, apellido, telefono, correo, direccion);
    }

    /**
     * Pide los datos de una mascota. Devuelve un Perro, un Gato o un Ave
     * segun lo que elija el usuario.
     * @param propietario Propietario de la mascota
     * @return Mascota creada con los datos capturados
     * @throws IllegalArgumentException si el tipo de mascota no es valido
     */
    private Mascota crearMascota(Propietario propietario) {
        System.out.println("--- Datos de la mascota ---");
        System.out.println("Tipo de mascota: 1) Perro  2) Gato  3) Ave exotica");
        int tipo = leerEntero("Opcion: ");
        String nombre = leerTexto("Nombre: ");
        int edad = leerEntero("Edad (anios): ");
        double peso = leerDecimal("Peso (kg): ");
        if (tipo == 1) {
            String raza = leerTexto("Raza: ");
            int energia = leerEntero("Nivel de energia (1 a 5): ");
            return new Perro(nombre, edad, peso, propietario, raza, energia);
        } else if (tipo == 2) {
            String respuesta = leerTexto("Es domestico? (s/n): ");
            return new Gato(nombre, edad, peso, propietario, respuesta.equalsIgnoreCase("s"));
        } else if (tipo == 3) {
            double alas = leerDecimal("Longitud de alas (cm): ");
            return new AveExotica(nombre, edad, peso, propietario, alas);
        }
        throw new IllegalArgumentException("Tipo de mascota no valido");
    }

    /**
     * Opcion 2: registra a un veterinario y lo agrega a la clinica
     */
    private void registrarVeterinario() {
        System.out.println("--- Datos del veterinario ---");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String telefono = leerTexto("Telefono: ");
        String correo = leerTexto("Correo: ");
        String cedula = leerTexto("Cedula profesional: ");
        String especialidad = leerTexto("Especialidad: ");
        Veterinario veterinario = new Veterinario(nombre, apellido, telefono, correo, cedula, especialidad);
        clinica.agregarVeterinario(veterinario);
        System.out.println("Registrado: " + veterinario);
    }

    /**
     * Opcion 3: agenda una cita
     */
    private void agendarCita() {
        Mascota mascota = seleccionarMascota();
        Veterinario veterinario = seleccionarVeterinario();
        Consultorio consultorio = seleccionarConsultorio();
        LocalDateTime fechaHora = leerFecha("Fecha y hora (dd/MM/yyyy HH:mm): ");
        Facturable servicio = crearServicio();
        Cita cita = clinica.agendarCita(mascota, veterinario, consultorio, fechaHora, servicio);
        System.out.println("Cita agendada: " + cita);
    }

    /**
     * Pide los datos del servicio que se realizara en la cita
     * @return Servicio creado (consulta general, vacunacion o cirugia)
     * @throws IllegalArgumentException si el tipo de servicio no es valido
     */
    private Facturable crearServicio() {
        System.out.println("Servicio: 1) Consulta general  2) Vacunacion  3) Cirugia");
        int tipo = leerEntero("Opcion: ");
        if (tipo == 1) {
            return new ConsultaGeneral();
        } else if (tipo == 2) {
            String vacuna = leerTexto("Nombre de la vacuna: ");
            double costo = leerDecimal("Costo de la vacuna: ");
            return new Vacunacion(vacuna, costo);
        } else if (tipo == 3) {
            int complejidad = leerEntero("Complejidad (1 a 3): ");
            double horas = leerDecimal("Horas de quirofano: ");
            return new Cirugia(complejidad, horas);
        }
        throw new IllegalArgumentException("Tipo de servicio no valido");
    }

    /**
     * Opcion 4: atiende o cancela una cita
     */
    private void atenderOCancelarCita() {
        mostrarCitas();
        Cita cita = clinica.buscarCita(leerTexto("Codigo de la cita (ejemplo C1): "));
        if (cita == null) {
            System.out.println("No existe una cita con ese codigo.");
            return;
        }
        int accion = leerEntero("1) Atender  2) Cancelar: ");
        if (accion == 1) {
            cita.atender();
            System.out.println("Cita atendida y guardada en el historial de " + cita.getMascota().getNombre());
        } else if (accion == 2) {
            cita.cancelar();
            System.out.println("Cita cancelada.");
        } else {
            System.out.println("Opcion no valida.");
        }
    }

    /**
     * Opcion 5: genera el comprobante de una cita atendida
     */
    private void generarComprobante() {
        mostrarCitas();
        Cita cita = clinica.buscarCita(leerTexto("Codigo de la cita (ejemplo C1): "));
        if (cita == null) {
            System.out.println("No existe una cita con ese codigo.");
            return;
        }
        Comprobante comprobante = cita.generarComprobante();
        System.out.println(cita);
        System.out.println(comprobante);
    }

    /**
     * Opcion 6: muestra el historial clinico de una mascota
     */
    private void consultarHistorial() {
        Mascota mascota = seleccionarMascota();
        System.out.println(mascota.getHistorial());
    }

    /**
     * Opcion 7: lista las mascotas con el sonido que hace cada una
     */
    private void listarMascotas() {
        List<Mascota> mascotas = clinica.listarMascotas();
        if (mascotas.isEmpty()) {
            System.out.println("No hay mascotas registradas.");
            return;
        }
        for (Mascota mascota : mascotas) {
            System.out.println(mascota.getNombre() + " dice: " + mascota.hacerSonido());
        }
    }

    /**
     * Opcion 8: muestra el total cobrado por un veterinario o por una mascota
     */
    private void verTotalCobrado() {
        System.out.println("Ver total cobrado: 1) Por veterinario  2) Por mascota");
        int opcion = leerEntero("Opcion: ");
        double total;
        if (opcion == 1) {
            total = clinica.totalCobrado(seleccionarVeterinario());
        } else if (opcion == 2) {
            total = clinica.totalCobrado(seleccionarMascota());
        } else {
            System.out.println("Opcion no valida.");
            return;
        }
        System.out.println("Total cobrado: $" + String.format("%.2f", total));
    }

    /**
     * Opcion 9: da de baja una mascota junto con su historial
     */
    private void darDeBajaMascota() {
        Mascota mascota = seleccionarMascota();
        clinica.darDeBajaMascota(mascota);
        System.out.println(mascota.getNombre() + " fue dada de baja junto con su historial.");
    }

    /**
     * Muestra las citas agendadas
     * @throws IllegalStateException si todavia no hay citas
     */
    private void mostrarCitas() {
        List<Cita> citas = clinica.getCitas();
        if (citas.isEmpty()) {
            throw new IllegalStateException("No hay citas registradas");
        }
        for (Cita cita : citas) {
            System.out.println(cita);
        }
    }

    /**
     * Muestra las mascotas y pide elegir una
     * @return Mascota elegida
     * @throws IllegalStateException si no hay mascotas registradas
     * @throws IllegalArgumentException si el numero elegido esta fuera de rango
     */
    private Mascota seleccionarMascota() {
        List<Mascota> mascotas = clinica.listarMascotas();
        if (mascotas.isEmpty()) {
            throw new IllegalStateException("No hay mascotas registradas");
        }
        for (int i = 0; i < mascotas.size(); i++) {
            Mascota mascota = mascotas.get(i);
            System.out.println((i + 1) + ". " + mascota.getNombre()
                    + " (duenio: " + mascota.getPropietario().getNombreCompleto() + ")");
        }
        int opcion = leerEntero("Seleccione una mascota: ");
        if (opcion < 1 || opcion > mascotas.size()) {
            throw new IllegalArgumentException("La opcion esta fuera de rango");
        }
        return mascotas.get(opcion - 1);
    }

    /**
     * Muestra los veterinarios y pide elegir uno
     * @return Veterinario elegido
     * @throws IllegalStateException si no hay veterinarios registrados
     * @throws IllegalArgumentException si el numero elegido esta fuera de rango
     */
    private Veterinario seleccionarVeterinario() {
        List<Veterinario> veterinarios = clinica.getVeterinarios();
        if (veterinarios.isEmpty()) {
            throw new IllegalStateException("No hay veterinarios registrados");
        }
        for (int i = 0; i < veterinarios.size(); i++) {
            System.out.println((i + 1) + ". " + veterinarios.get(i).getNombreCompleto());
        }
        int opcion = leerEntero("Seleccione un veterinario: ");
        if (opcion < 1 || opcion > veterinarios.size()) {
            throw new IllegalArgumentException("La opcion esta fuera de rango");
        }
        return veterinarios.get(opcion - 1);
    }

    /**
     * Muestra los consultorios y pide elegir uno
     * @return Consultorio elegido
     * @throws IllegalStateException si la clinica no tiene consultorios
     * @throws IllegalArgumentException si el numero elegido esta fuera de rango
     */
    private Consultorio seleccionarConsultorio() {
        List<Consultorio> consultorios = clinica.getConsultorios();
        if (consultorios.isEmpty()) {
            throw new IllegalStateException("La clinica no tiene consultorios");
        }
        for (int i = 0; i < consultorios.size(); i++) {
            System.out.println((i + 1) + ". " + consultorios.get(i));
        }
        int opcion = leerEntero("Seleccione un consultorio: ");
        if (opcion < 1 || opcion > consultorios.size()) {
            throw new IllegalArgumentException("La opcion esta fuera de rango");
        }
        return consultorios.get(opcion - 1);
    }

    /**
     * Lee un numero entero del teclado. Repite la pregunta hasta que el dato sea valido.
     * @param mensaje Mensaje que se muestra al usuario
     * @return Numero entero capturado
     */
    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = teclado.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Debe escribir un numero entero.");
            }
        }
    }

    /**
     * Lee un numero decimal del teclado. Repite la pregunta hasta que el dato sea valido.
     * @param mensaje Mensaje que se muestra al usuario
     * @return Numero decimal capturado
     */
    private double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = teclado.nextLine().trim();
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Debe escribir un numero, por ejemplo 12.5");
            }
        }
    }

    /**
     * Lee un texto del teclado. Repite la pregunta hasta que el texto no este vacio.
     * @param mensaje Mensaje que se muestra al usuario
     * @return Texto capturado
     */
    private String leerTexto(String mensaje) {
        String entrada = "";
        while (entrada.isEmpty()) {
            System.out.print(mensaje);
            entrada = teclado.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("El dato no puede estar vacio.");
            }
        }
        return entrada;
    }

    /**
     * Lee una fecha y hora del teclado. Repite la pregunta hasta que el formato sea valido.
     * @param mensaje Mensaje que se muestra al usuario
     * @return Fecha y hora capturadas
     */
    private LocalDateTime leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = teclado.nextLine().trim();
            try {
                return LocalDateTime.parse(entrada, FORMATO_FECHA);
            } catch (DateTimeParseException e) {
                System.out.println("Formato invalido. Ejemplo: 10/11/2026 10:00");
            }
        }
    }
}
