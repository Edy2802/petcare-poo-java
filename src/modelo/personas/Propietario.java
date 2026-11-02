package modelo.personas;

/**
 * Persona duenia de una o mas mascotas
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public class Propietario extends Persona {

    private String direccion;

    /**
     * Constructor con todos los datos del propietario
     * @param nombre Nombre del propietario
     * @param apellido Apellido del propietario
     * @param telefono Telefono de contacto
     * @param correo Correo electronico
     * @param direccion Direccion del propietario
     * @throws IllegalArgumentException si algun dato no es valido
     */
    public Propietario(String nombre, String apellido, String telefono, String correo, String direccion) {
        super(nombre, apellido, telefono, correo);
        setDireccion(direccion);
    }

    /**
     * Obtiene la direccion del propietario
     * @return Direccion del propietario
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Cambia la direccion del propietario
     * @param direccion Nueva direccion
     * @throws IllegalArgumentException si la direccion esta vacia
     */
    public void setDireccion(String direccion) {
        this.direccion = validarTexto(direccion, "La direccion");
    }

    /**
     * Indica el rol de la persona
     * @return El texto "Propietario"
     */
    @Override
    public String obtenerRol() {
        return "Propietario";
    }

    /**
     * Representacion en texto del propietario
     * @return Datos principales del propietario
     */
    @Override
    public String toString() {
        return super.toString() + " | Direccion: " + direccion;
    }
}
