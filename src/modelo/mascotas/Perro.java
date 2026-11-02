package modelo.mascotas;
import modelo.personas.Propietario; //import: Importa la clase Propietario del paquete modelo.personas

/**
 * Mascota de tipo perro
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public class Perro extends Mascota {

    private String raza;
    private int nivelEnergia;

    /**
     * Constructor con todos los datos del perro
     * @param nombre Nombre del perro
     * @param edad Edad en anios
     * @param peso Peso en kilogramos
     * @param propietario Propietario del perro
     * @param raza Raza del perro
     * @param nivelEnergia Nivel de energia del perro (de 1 a 5)
     * @throws IllegalArgumentException si algun dato no es valido
     */
    public Perro(String nombre, int edad, double peso, Propietario propietario,
            String raza, int nivelEnergia) {
        super(nombre, edad, peso, propietario);
        if (raza == null || raza.trim().isEmpty()) {
            throw new IllegalArgumentException("La raza no puede estar vacia");
        }
        if (nivelEnergia < 1 || nivelEnergia > 5) {
            throw new IllegalArgumentException("El nivel de energia debe estar entre 1 y 5");
        }
        this.raza = raza.trim();
        this.nivelEnergia = nivelEnergia;
    }

    /**
     * Obtiene la raza del perro
     * @return Raza del perro
     */
    public String getRaza() {
        return raza;
    }

    /**
     * Obtiene el nivel de energia del perro
     * @return Nivel de energia (de 1 a 5)
     */
    public int getNivelEnergia() {
        return nivelEnergia;
    }

    /**
     * Sonido que hace el perro
     * @return El texto "Guau"
     */
    @Override
    public String hacerSonido() {
        return "Guau";
    }

    /**
     * Representacion en texto del perro
     * @return Datos principales del perro
     */
    @Override
    public String toString() {
        return "Perro: " + super.toString() + " | Raza: " + raza + " | Energia: " + nivelEnergia;
    }
}
