package app;
import modelo.clinica.Clinica; //import: Importa la clase Clinica del paquete modelo.clinica
import modelo.personas.Recepcionista; //import: Importa la clase Recepcionista del paquete modelo.personas

/**
 * Aplicacion principal del sistema de la clinica veterinaria PetCare
 *
 * @author Edy de Jesus
 * @version 1.0
 * @since 01-11-2026
 */
public class Main {

    /**
     * Punto de entrada de la aplicacion
     * @param args Argumentos de linea de comandos
     */
    public static void main(String[] args) {
        Clinica clinica = new Clinica("PetCare");

        clinica.crearConsultorio(1);
        clinica.crearConsultorio(2);
        clinica.crearConsultorio(3);

        Recepcionista recepcionista = new Recepcionista("Laura", "Mendoza", "2295551234",
                "laura@petcare.com", "Matutino");
        clinica.agregarRecepcionista(recepcionista);

        MenuConsola menu = new MenuConsola(clinica);
        menu.iniciar();
    }
}
