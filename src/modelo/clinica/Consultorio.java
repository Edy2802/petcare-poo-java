package modelo.clinica;

/**
 * Consultorio numerado que pertenece a la clinica
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public class Consultorio {

    private int numero;

    /**
     * Constructor con el numero del consultorio
     * @param numero Numero del consultorio (mayor que cero)
     * @throws IllegalArgumentException si el numero no es mayor que cero
     */
    public Consultorio(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("El numero de consultorio debe ser mayor que cero");
        }
        this.numero = numero;
    }

    /**
     * Obtiene el numero del consultorio
     * @return Numero del consultorio
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Representacion en texto del consultorio
     * @return El texto "Consultorio" con su numero
     */
    @Override
    public String toString() {
        return "Consultorio " + numero;
    }
}
