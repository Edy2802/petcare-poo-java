package modelo.personas;

/**
 * Persona que atiende las citas de la clinica
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public class Veterinario extends Persona {

    private String cedulaProfesional;
    private String especialidad;

    /**
     * Constructor con todos los datos del veterinario
     * @param nombre Nombre del veterinario
     * @param apellido Apellido del veterinario
     * @param telefono Telefono de contacto
     * @param correo Correo electronico
     * @param cedulaProfesional Cedula profesional del veterinario
     * @param especialidad Especialidad del veterinario
     * @throws IllegalArgumentException si algun dato no es valido
     */
    public Veterinario(String nombre, String apellido, String telefono, String correo,
            String cedulaProfesional, String especialidad) {
        super(nombre, apellido, telefono, correo);
        this.cedulaProfesional = validarTexto(cedulaProfesional, "La cedula profesional");
        this.especialidad = validarTexto(especialidad, "La especialidad");
    }

    /**
     * Obtiene la cedula profesional
     * @return Cedula profesional del veterinario
     */
    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    /**
     * Obtiene la especialidad
     * @return Especialidad del veterinario
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * Indica el rol de la persona
     * @return El texto "Veterinario"
     */
    @Override
    public String obtenerRol() {
        return "Veterinario";
    }

    /**
     * Representacion en texto del veterinario
     * @return Datos principales del veterinario
     */
    @Override
    public String toString() {
        return super.toString() + " | Cedula: " + cedulaProfesional + " | Especialidad: " + especialidad;
    }
}
