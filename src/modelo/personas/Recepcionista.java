package modelo.personas;


/**
 * Personal administrativo de la clinica
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public class Recepcionista extends Persona {

    private String turno;

    /**
     * Constructor con todos los datos del recepcionista
     * @param nombre Nombre del recepcionista
     * @param apellido Apellido del recepcionista
     * @param telefono Telefono de contacto
     * @param correo Correo electronico
     * @param turno Turno de trabajo (por ejemplo, Matutino o Vespertino)
     * @throws IllegalArgumentException si algun dato no es valido
     */
    public Recepcionista(String nombre, String apellido, String telefono, String correo, String turno) {
        super(nombre, apellido, telefono, correo);
        setTurno(turno);
    }

    /**
     * Obtiene el turno de trabajo
     * @return Turno del recepcionista
     */
    public String getTurno() {
        return turno;
    }

    /**
     * Cambia el turno de trabajo
     * @param turno Nuevo turno
     * @throws IllegalArgumentException si el turno esta vacio
     */
    public void setTurno(String turno) {
        this.turno = validarTexto(turno, "El turno");
    }

    /**
     * Indica el rol de la persona
     * @return El texto "Recepcionista"
     */
    @Override
    public String obtenerRol() {
        return "Recepcionista";
    }

    /**
     * Representacion en texto del recepcionista
     * @return Datos principales del recepcionista
     */
    @Override
    public String toString() {
        return super.toString() + " | Turno: " + turno;
    }
}
