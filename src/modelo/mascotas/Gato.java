package modelo.mascotas;
import modelo.personas.Propietario; //import: Importa la clase Propietario del paquete modelo.personas

/**
 * Mascota de tipo gato
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public class Gato extends Mascota {

    private boolean esDomestico;

    /**
     * Constructor con todos los datos del gato
     * @param nombre Nombre del gato
     * @param edad Edad en anios
     * @param peso Peso en kilogramos
     * @param propietario Propietario del gato
     * @param esDomestico Indica si el gato es domestico
     * @throws IllegalArgumentException si algun dato no es valido
     */
    public Gato(String nombre, int edad, double peso, Propietario propietario, boolean esDomestico) {
        super(nombre, edad, peso, propietario);
        this.esDomestico = esDomestico;
    }

    /**
     * Indica si el gato es domestico
     * @return true si es domestico, false en caso contrario
     */
    public boolean esDomestico() {
        return esDomestico;
    }

    /**
     * Sonido que hace el gato
     * @return El texto "Miau"
     */
    @Override
    public String hacerSonido() {
        return "Miau";
    }

    /**
     * Representacion en texto del gato
     * @return Datos principales del gato
     */
    @Override
    public String toString() {
        String tipo = esDomestico ? "Si" : "No";
        return "Gato: " + super.toString() + " | Domestico: " + tipo;
    }
}
