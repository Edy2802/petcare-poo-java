package modelo.mascotas;
import modelo.personas.Propietario; //import: Importa la clase Propietario del paquete modelo.personas

/**
 * Clase abstracta que agrupa los datos que comparten todas las mascotas de la clinica
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public abstract class Mascota {

    private String nombre;
    private int edad;
    private double peso;
    private Propietario propietario;
    private HistorialClinico historial;

    /**
     * Constructor con los datos de la mascota
     * @param nombre Nombre de la mascota
     * @param edad Edad en anios (mayor que cero)
     * @param peso Peso en kilogramos (mayor que cero)
     * @param propietario Propietario de la mascota
     * @throws IllegalArgumentException si algun dato no es valido
     */
    public Mascota(String nombre, int edad, double peso, Propietario propietario) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la mascota no puede estar vacio");
        }
        if (propietario == null) {
            throw new IllegalArgumentException("La mascota debe tener un propietario");
        }
        this.nombre = nombre.trim();
        setEdad(edad);
        setPeso(peso);
        this.propietario = propietario;
        // Composicion: el historial se crea dentro de la mascota y nadie mas lo recibe,
        // por eso su vida depende de la mascota
        this.historial = new HistorialClinico();
    }

    /**
     * Obtiene el nombre de la mascota
     * @return Nombre de la mascota
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la edad de la mascota
     * @return Edad en anios
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Obtiene el peso de la mascota
     * @return Peso en kilogramos
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Cambia la edad de la mascota
     * @param edad Nueva edad en anios
     * @throws IllegalArgumentException si la edad no es mayor que cero
     */
    public void setEdad(int edad) {
        if (edad <= 0) {
            throw new IllegalArgumentException("La edad debe ser mayor que cero");
        }
        this.edad = edad;
    }

    /**
     * Cambia el peso de la mascota
     * @param peso Nuevo peso en kilogramos
     * @throws IllegalArgumentException si el peso no es mayor que cero
     */
    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero");
        }
        this.peso = peso;
    }

    /**
     * Obtiene al propietario de la mascota
     * @return Propietario de la mascota
     */
    public Propietario getPropietario() {
        return propietario;
    }

    /**
     * Obtiene el historial clinico de la mascota
     * @return Historial clinico de la mascota
     */
    public HistorialClinico getHistorial() {
        return historial;
    }

    /**
     * Sonido que hace la mascota. Cada clase hija lo implementa a su manera.
     * @return Sonido de la mascota
     */
    public abstract String hacerSonido();

    /**
     * Representacion en texto de la mascota
     * @return Datos principales de la mascota
     */
    @Override
    public String toString() {
        return nombre + " | Edad: " + edad + " anios | Peso: " + peso + " kg | Duenio: "
                + propietario.getNombreCompleto();
    }
}
