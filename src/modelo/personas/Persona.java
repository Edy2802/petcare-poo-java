package modelo.personas;


/**
 * Clase abstracta que agrupa los datos que comparten todas las personas de la clinica
 * (propietarios, veterinarios y recepcionistas)
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public abstract class Persona {

    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;

    /**
     * Constructor con todos los datos de la persona
     * @param nombre Nombre de la persona
     * @param apellido Apellido de la persona
     * @param telefono Telefono de contacto
     * @param correo Correo electronico (debe contener @)
     * @throws IllegalArgumentException si algun dato esta vacio o el correo no es valido
     */
    public Persona(String nombre, String apellido, String telefono, String correo) {
        this.nombre = validarTexto(nombre, "El nombre");
        this.apellido = validarTexto(apellido, "El apellido");
        setTelefono(telefono);
        setCorreo(correo);
    }

    /**
     * Obtiene el nombre de la persona
     * @return Nombre de la persona
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el apellido de la persona
     * @return Apellido de la persona
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Obtiene el telefono de la persona
     * @return Telefono de la persona
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Obtiene el correo de la persona
     * @return Correo de la persona
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Cambia el telefono de la persona
     * @param telefono Nuevo telefono
     * @throws IllegalArgumentException si el telefono esta vacio
     */
    public void setTelefono(String telefono) {
        this.telefono = validarTexto(telefono, "El telefono");
    }

    /**
     * Cambia el correo de la persona
     * @param correo Nuevo correo (debe contener @)
     * @throws IllegalArgumentException si el correo esta vacio o no contiene @
     */
    public void setCorreo(String correo) {
        String correoValido = validarTexto(correo, "El correo");
        if (!correoValido.contains("@")) {
            throw new IllegalArgumentException("El correo debe contener @");
        }
        this.correo = correoValido;
    }

    /**
     * Obtiene el nombre completo de la persona
     * @return Nombre y apellido juntos
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    /**
     * Indica el rol que tiene la persona dentro de la clinica.
     * Cada clase hija lo implementa a su manera.
     * @return Rol de la persona
     */
    public abstract String obtenerRol();

    /**
     * Valida que un texto no sea nulo ni vacio.
     * Es protected porque lo usan Persona y sus clases hijas (Propietario, Veterinario
     * y Recepcionista), pero no las clases que no pertenecen a esta jerarquia.
     * @param texto Texto que se va a validar
     * @param campo Nombre del dato, se usa en el mensaje de error
     * @return El texto sin espacios al inicio ni al final
     * @throws IllegalArgumentException si el texto es nulo o esta vacio
     */
    protected static String validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacio");
        }
        return texto.trim();
    }

    /**
     * Representacion en texto de la persona
     * @return Nombre completo y rol
     */
    @Override
    public String toString() {
        return getNombreCompleto() + " (" + obtenerRol() + ")";
    }
}
