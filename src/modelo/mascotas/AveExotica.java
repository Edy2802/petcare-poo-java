package modelo.mascotas;
import modelo.personas.Propietario; //import: Importa la clase Propietario del paquete modelo.personas

/**
 * Mascota de tipo ave exotica
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public class AveExotica extends Mascota {

    private double longitudAlas;

    /**
     * Constructor con todos los datos del ave
     * @param nombre Nombre del ave
     * @param edad Edad en anios
     * @param peso Peso en kilogramos
     * @param propietario Propietario del ave
     * @param longitudAlas Longitud de las alas en centimetros (mayor que cero)
     * @throws IllegalArgumentException si algun dato no es valido
     */
    public AveExotica(String nombre, int edad, double peso, Propietario propietario,
            double longitudAlas) {
        super(nombre, edad, peso, propietario);
        setLongitudAlas(longitudAlas);
    }

    /**
     * Obtiene la longitud de las alas
     * @return Longitud de las alas en centimetros
     */
    public double getLongitudAlas() {
        return longitudAlas;
    }

    /**
     * Cambia la longitud de las alas
     * @param longitudAlas Nueva longitud en centimetros
     * @throws IllegalArgumentException si la longitud no es mayor que cero
     */
    public void setLongitudAlas(double longitudAlas) {
        if (longitudAlas <= 0) {
            throw new IllegalArgumentException("La longitud de las alas debe ser mayor que cero");
        }
        this.longitudAlas = longitudAlas;
    }

    /**
     * Sonido que hace el ave
     * @return El texto "Pio"
     */
    @Override
    public String hacerSonido() {
        return "Pio";
    }

    /**
     * Representacion en texto del ave
     * @return Datos principales del ave
     */
    @Override
    public String toString() {
        return "Ave exotica: " + super.toString() + " | Alas: " + longitudAlas + " cm";
    }
}
