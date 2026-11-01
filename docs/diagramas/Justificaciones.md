## 4. Justificación de relaciones

El motivo de este documento .md es **justificar** las relaciones que implemente en mi diagrama de clases, asi como **que ocurre si?...** se elimina alguna de las partes y/o relaciones.
Nota: Quiero aclarar que, en este diagrama implemente algo llamado **multiplicidad**. Es un tema que no conocia, pero en la seccion de la documentacion de este trabajo, fue la parte donde mas realice uso de la IA para la organizacion de las ideas, clases, etc. Lo que quiero aclarar es, que la IA me recomendo el uso de la multiplicidad. Este es un tema que en la materia de **Base de datos** la conozco como **cardinalidad**, que se refiere al tipo de relacion de una entidad a otra (uno a uno, uno a varios, varios a varios, etc). Asi que, decidi implementarla en mi diagrama de clases, aunque no se que tan correcto sea que lo haya hecho, ya que en los diagramas de clases que vimos anteriormente, esto no lo incluia. A continuacion, dejo la simbologia de multiplicidad utilizada en mi diagrama:

| Símbolo | Significado | Se lee como |
| --- | --- | --- |
| `1` | Exactamente uno | Obligatorio y único |
| `0..*` | Cero o más | Opcional, sin límite |
| `1..*` | Uno o más | Obligatorio, al menos uno, sin límite |

La multiplicidad pegada a una clase indica cuántos objetos **de esa clase**
participan en la relación con un objeto de la clase del otro extremo.

### 4.1 Asociaciones

| Relación | Multiplicidad | Si se elimina una de las partes | Justificación |
| --- | --- | --- | --- |
| `Mascota` → `Propietario` (rol: dueño) | `0..*` a `1` | El propietario sigue existiendo aunque se dé de baja una mascota, y una mascota no desaparece por sí sola si su dueño cambia. | Son objetos independientes que solo se conocen. No es agregación porque el propietario no "forma parte" de la mascota, solo es su dueño. |
| `Cita` → `Mascota` | `0..*` a `1` | Cancelar o borrar una cita no elimina a la mascota. | La cita solo referencia a la mascota para saber a quién se atiende. La mascota existe antes de la cita y después de ella. |
| `Cita` → `Veterinario` | `0..*` a `1` | Al cancelar una cita el veterinario sigue trabajando. | Igual que con la mascota: la cita lo referencia, no lo posee. Además un veterinario atiende muchas citas, lo que descarta composición. |
| `Cita` → `Consultorio` | `0..*` a `1` | El consultorio sigue disponible después de la cita. | El consultorio pertenece a la clínica, no a la cita. La cita solo registra en cuál ocurrió. |
| `Clinica` → `Cita` (agenda) | `1` a `0..*` | Si una cita se cancela, la clínica sigue existiendo. | La clínica administra las citas, pero cada cita es un objeto con sus propios datos. Es el punto de entrada que las coordina. |
| `Cita` → `Facturable` (servicio) | `0..*` a `1` | El servicio puede reutilizarse o descartarse sin afectar la cita ya atendida. | La cita solo necesita saber calcular su costo, sin importar si es consulta, vacunación o cirugía. Por eso apunta a la interfaz y no a una clase concreta. |
| `Cita` → `EstadoCita` | `0..*` a `1` | No aplica: un enum son valores fijos. | Es una referencia a uno de tres valores permitidos (`PROGRAMADA`, `ATENDIDA`, `CANCELADA`). |

### 4.2 Agregaciones

| Relación | Multiplicidad | Si se elimina una de las partes | Justificación |
| --- | --- | --- | --- |
| `Clinica` ◇── `Veterinario` | `1` a `0..*` | Si la clínica cierra, el veterinario sigue existiendo y puede trabajar en otra. | El veterinario **se crea fuera** de la clínica y se le entrega ya creado con `agregarVeterinario(...)`. Hay relación todo-parte, pero la parte vive sola. No es composición porque la clínica no lo crea ni lo posee. |
| `Clinica` ◇── `Recepcionista` | `1` a `0..*` | Si la clínica cierra, el recepcionista sigue existiendo como persona. | Mismo razonamiento: es personal que llega ya creado y puede cambiar de clínica. |

### 4.3 Composiciones

| Relación | Multiplicidad | Si se elimina una de las partes | Justificación |
| --- | --- | --- | --- |
| `Mascota` ◆── `HistorialClinico` | `1` a `1` | Si la mascota se elimina del sistema, su historial desaparece con ella. | El historial **se crea dentro del constructor de `Mascota`** y no se comparte con nadie. No tiene sentido sin su mascota. Al dar de baja la mascota, el historial queda sin referencias y el recolector de basura lo elimina. |
| `Clinica` ◆── `Consultorio` | `1` a `1..*` | Si la clínica desaparece, sus consultorios desaparecen con ella. | La clínica **crea sus consultorios** con `crearConsultorio(int)`; nadie más los recibe ni los usa fuera de ella. Pertenecen a la clínica de forma exclusiva. |

### 4.4 Otras relaciones (dependencias)

| Relación | Justificación |
| --- | --- |
| `Cita` ┄> `Comprobante` | La cita **crea** el comprobante al generarlo (`generarComprobante()`), pero no lo guarda como atributo. Por eso es dependencia y no asociación. |
| `MenuConsola` ┄> `Clinica`, `Main` ┄> `MenuConsola` | Son relaciones de uso entre la capa de interfaz y el modelo. El modelo no conoce la consola. |