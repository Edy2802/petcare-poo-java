package modelo.mascotas;
import modelo.clinica.Cita; //import: Importa la clase Cita del paquete modelo.clinica
import java.util.ArrayList; //import: Importa la clase ArrayList para crear la lista de registros
import java.util.List; //import: Importa la interfaz List para declarar la lista de registros

/**
 * Historial clinico de una mascota. Guarda las citas que ya fueron atendidas.
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public class HistorialClinico {

    private List<Cita> registros;

    /**
     * Constructor que crea un historial sin registros
     */
    public HistorialClinico() {
        registros = new ArrayList<>();
    }

    /**
     * Agrega una cita atendida al historial
     * @param cita Cita que se va a registrar
     * @throws IllegalArgumentException si la cita es nula
     */
    public void agregarRegistro(Cita cita) {
        if (cita == null) {
            throw new IllegalArgumentException("La cita no puede ser nula");
        }
        registros.add(cita);
    }

    /**
     * Obtiene los registros del historial
     * @return Copia de la lista de citas registradas
     */
    public List<Cita> obtenerRegistros() {
        return new ArrayList<>(registros);
    }

    /**
     * Representacion en texto del historial
     * @return Lista de las citas registradas o un aviso si no hay ninguna
     */
    @Override
    public String toString() {
        if (registros.isEmpty()) {
            return "Historial clinico: sin registros";
        }
        String texto = "Historial clinico:";
        for (Cita cita : registros) {
            texto += "\n  - " + cita;
        }
        return texto;
    }
}
