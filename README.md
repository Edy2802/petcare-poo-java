# PetCare: sistema de consola para una clínica veterinaria

PetCare es un programa de consola hecho en Java para llevar el control básico de una clínica veterinaria. Con él se pueden registrar dueños, mascotas y veterinarios, agendar citas, atenderlas, generar el comprobante de cobro y consultar el historial clínico de cada mascota.

Lo que quise resolver es lo que normalmente se hace en papel o en una libreta: quién es el dueño de cada mascota, quién la atiende, en qué consultorio y a qué hora, y cuánto se le cobra según el servicio (consulta general, vacunación o cirugía). El programa también evita errores comunes, por ejemplo que un veterinario tenga dos citas a la misma hora o que se use el mismo consultorio al mismo tiempo.

Este proyecto lo hice para la materia de Diseño y Programación Orientada a Objetos. La idea principal no era solo que funcione, sino aplicar lo que vimos: clases, herencia, interfaces, polimorfismo, agregación, composición y paquetes.

**Versión:** 1.0.0

## Contenido

1. [Tecnologías](#tecnologías)
2. [Modelo de la solución](#modelo-de-la-solución)
3. [Estructura del proyecto](#estructura-del-proyecto)
4. [Conceptos aplicados](#conceptos-aplicados)
5. [Instalación y ejecución](#instalación-y-ejecución)
6. [Ejemplo de uso](#ejemplo-de-uso)
7. [Reglas de negocio](#reglas-de-negocio)
8. [Reflexión](#reflexión)
9. [Uso de la IA en este proyecto](#uso-de-la-ia-en-este-proyecto)
10. [Autor](#autor)

## Tecnologías

| Herramienta | Uso |
| --- | --- |
| Java (JDK 17 o superior) | Lenguaje del proyecto |
| Lucidchart | Diagrama de clases (UML) |
| Visual Studio Code | Editor donde escribí y ejecuté el código |
| Git y GitHub | Control de versiones y entrega |

No se usa ninguna librería externa. Todo lo que importa el código viene incluido en Java (`java.util` y `java.time`).

## Modelo de la solución

Este es el diagrama de clases del proyecto:

![Diagrama de clases de PetCare](docs/diagramas/diagrama-clases.png)

El diagrama también se puede abrir y editar en Lucidchart: [ver diagrama editable](https://lucid.app/lucidchart/8c05bdf1-78c5-462a-b42d-9633c456dfb5/edit).

Antes de hacer el diagrama armé una tabla con las clases que encontré en el caso de estudio. Esa tabla está en [docs/analisis.md](docs/analisis.md). Las justificaciones completas de cada relación, junto con lo que pasaría si se elimina alguna de las partes, están en [docs/Justificaciones.md](docs/Justificaciones.md). Aquí dejo un resumen de las decisiones principales.

### Herencia

Usé herencia en dos lugares:

- **`Persona`** es una clase abstracta de la que heredan `Propietario`, `Veterinario` y `Recepcionista`. Los tres tienen nombre, apellido, teléfono y correo, así que no tenía sentido repetirlos en cada clase. Es abstracta porque en la clínica nunca existe una "persona" sin rol, siempre es alguien en particular.
- **`Mascota`** es otra clase abstracta de la que heredan `Perro`, `Gato` y `AveExotica`. Todas tienen nombre, edad y peso, pero cada una hace un sonido distinto, y por eso `hacerSonido()` es un método abstracto.

### Interfaz

`Facturable` es una interfaz con dos métodos, `calcularCosto()` y `obtenerDescripcion()`. La implementan `ConsultaGeneral`, `Vacunacion` y `Cirugia`. Elegí una interfaz y no una clase padre `Servicio` porque los tres servicios calculan su costo de forma muy distinta y casi no comparten atributos. Lo único que necesita la cita es saber cuánto cuesta el servicio, sin importar cuál sea.

### Agregación y composición

| Relación | Tipo | Por qué |
| --- | --- | --- |
| `Clinica` con `Veterinario` | Agregación | El veterinario se crea fuera de la clínica y se le entrega ya creado. Si la clínica cierra, el veterinario sigue existiendo. |
| `Clinica` con `Recepcionista` | Agregación | Mismo razonamiento que con el veterinario. |
| `Clinica` con `Consultorio` | Composición | La clínica crea sus propios consultorios con `crearConsultorio()`. Si la clínica desaparece, los consultorios también. |
| `Mascota` con `HistorialClinico` | Composición | El historial se crea dentro del constructor de la mascota y no se comparte con nadie. Sin la mascota no tiene sentido. |

### Asociaciones y dependencias

La `Cita` es la clase que une todo: tiene una `Mascota`, un `Veterinario`, un `Consultorio`, un servicio (`Facturable`) y un `EstadoCita`. Son asociaciones porque la cita solo hace referencia a esos objetos, no los crea ni los posee. La cita depende de `Comprobante` porque lo crea cuando se genera, pero no lo guarda. `Main` depende de `MenuConsola` porque la crea y la inicia.

### Una nota sobre la multiplicidad

En el diagrama usé la multiplicidad (`1`, `0..*`, `1..*`) en las relaciones. Es lo mismo que la cardinalidad que vimos en Base de datos. En las justificaciones explico qué significa cada símbolo.

## Estructura del proyecto

```text
petcare-poo-java/
├── README.md
├── .gitignore
├── docs/
│   ├── analisis.md
│   ├── Justificaciones.md
│   ├── diagramas/
│   └── capturas/
└── src/
    ├── app/
    │   ├── Main.java
    │   └── MenuConsola.java
    └── modelo/
        ├── personas/
        │   ├── Persona.java
        │   ├── Propietario.java
        │   ├── Veterinario.java
        │   └── Recepcionista.java
        ├── mascotas/
        │   ├── Mascota.java
        │   ├── Perro.java
        │   ├── Gato.java
        │   ├── AveExotica.java
        │   └── HistorialClinico.java
        ├── servicios/
        │   ├── Facturable.java
        │   ├── ConsultaGeneral.java
        │   ├── Vacunacion.java
        │   └── Cirugia.java
        └── clinica/
            ├── Clinica.java
            ├── Cita.java
            ├── Consultorio.java
            ├── Comprobante.java
            └── EstadoCita.java
```

| Paquete | Para qué sirve |
| --- | --- |
| `app` | Lo que arranca el programa. `Main` crea la clínica y `MenuConsola` muestra el menú y pide los datos al usuario. |
| `modelo.personas` | Las personas de la clínica: propietarios, veterinarios y recepcionistas. |
| `modelo.mascotas` | Las mascotas (perro, gato y ave) y su historial clínico. |
| `modelo.servicios` | Los servicios que se cobran y la forma de calcular cada costo. |
| `modelo.clinica` | La clínica, las citas, los consultorios y los comprobantes. Aquí están las reglas principales. |

Dividí el código así para que cada clase tenga una sola responsabilidad. Por ejemplo, las clases del modelo no saben nada de la consola: solo `MenuConsola` imprime y lee datos.

## Conceptos aplicados

Los números de línea corresponden a la versión 1.0.0.

| Concepto | Archivo y línea | Qué se hace ahí |
| --- | --- | --- |
| Herencia | `Propietario.java` línea 10, `Perro.java` línea 11 | `Propietario extends Persona` y `Perro extends Mascota` |
| Clase abstracta | `Persona.java` línea 11, `Mascota.java` línea 11 | Las dos son abstractas y no se pueden instanciar |
| Método abstracto | `Persona.java` línea 100, `Mascota.java` línea 111 | `obtenerRol()` y `hacerSonido()` |
| Interfaz | `Facturable.java` línea 10 | Define `calcularCosto()` y `obtenerDescripcion()` |
| Implementación de interfaz | `Cirugia.java` línea 10, `Vacunacion.java` línea 10, `ConsultaGeneral.java` línea 10 | Los tres servicios implementan `Facturable` |
| Sobreescritura (`@Override`) | `Perro.java` líneas 59 y 60, `Cirugia.java` línea 40, `Persona.java` línea 123 | `hacerSonido()`, `calcularCosto()` y `toString()` |
| Polimorfismo | `MenuConsola.java` línea 291, `Clinica.java` línea 300 | Una lista de `Mascota` llama `hacerSonido()` y una lista de `Facturable` suma los costos, sin usar `instanceof` |
| Sobrecarga de métodos | `Clinica.java` líneas 160 y 174, líneas 189 y 204 | `buscarCita(String)` y `buscarCita(LocalDate)`, `totalCobrado(Veterinario)` y `totalCobrado(Mascota)` |
| Sobrecarga de constructores | `ConsultaGeneral.java` líneas 20 y 29 | Uno por defecto y otro que recibe la tarifa |
| Constructores con `super(...)` | `Perro.java` línea 28 | Llama al constructor de `Mascota` |
| Encapsulamiento y validación | `Mascota.java` líneas 72 y 84 | `setEdad` y `setPeso` validan y lanzan `IllegalArgumentException` |
| Asociación | `Cita.java` líneas 24 a 26 | La cita guarda su mascota, su veterinario y su consultorio |
| Agregación | `Clinica.java` líneas 62 a 71 | `agregarVeterinario` recibe un veterinario ya creado |
| Composición | `Mascota.java` línea 40, `Clinica.java` línea 98 | La mascota crea su historial y la clínica crea sus consultorios |
| `static` y constantes | `Comprobante.java` línea 16, `Cita.java` línea 19, `ConsultaGeneral.java` línea 13, `Cirugia.java` línea 13 | Contadores de comprobantes y de citas, y las tarifas con `static final` |
| Niveles de acceso | `Persona.java` línea 111, `Cita.java` línea 157 | `protected` en `validarTexto` y acceso de paquete en `coincideHorario` (los dos tienen comentario que explica por qué) |
| Enumeración | `EstadoCita.java` línea 10 | PROGRAMADA, ATENDIDA y CANCELADA |
| Paquetes e imports | Todos los archivos | Cada import tiene un comentario que explica qué trae |
| Eliminación y recolector de basura | `Clinica.java` línea 219, comentario en la línea 229 | `darDeBajaMascota` quita todas las referencias a la mascota y a su historial |
| Manejo de excepciones | `MenuConsola.java` líneas 56, 433 y 484 | El menú atrapa los errores y sigue funcionando |

## Instalación y ejecución

### Qué necesitas

- JDK 17 o superior. Para revisar si lo tienes, abre una terminal y escribe `javac -version`.
- Git.

### Pasos

1. Clona el repositorio. Cambia `TU-USUARIO` por el usuario de GitHub donde esté el proyecto:

   ```bash
   git clone https://github.com/TU-USUARIO/petcare-poo-java.git
   ```

2. Entra a la carpeta del proyecto:

   ```bash
   cd petcare-poo-java
   ```

3. Compila. Este comando compila `Main` y todas las clases que usa, y deja los archivos compilados en una carpeta llamada `out`:

   ```bash
   javac -d out -sourcepath src src/app/Main.java
   ```

4. Ejecuta el programa:

   ```bash
   java -cp out app.Main
   ```

Los mismos comandos funcionan en Windows (CMD o PowerShell), Linux y macOS. Al ejecutarlo aparece el menú principal y ya se puede empezar a usar.

> Al iniciar, el programa ya trae 3 consultorios y una recepcionista creados desde `Main`. Los veterinarios, los dueños y las mascotas los registras tú desde el menú.

## Ejemplo de uso

Esta es la ejecución del escenario de demostración del proyecto. Aquí dejé solo las partes importantes, sin repetir el menú cada vez. La ejecución completa está en [docs/capturas/ejecucion-escenario.txt](docs/capturas/ejecucion-escenario.txt).

### 1. Registro de dueños y mascotas

Se registran dos dueños (Ana y Beto) y tres mascotas. Ana tiene dos mascotas, por eso en el segundo registro se elige un propietario que ya existe.

```text
Registrado: Perro: Max | Edad: 4 anios | Peso: 22.5 kg | Duenio: Ana Ruiz | Raza: Labrador | Energia: 4
Registrado: Gato: Luna | Edad: 2 anios | Peso: 3.8 kg | Duenio: Ana Ruiz | Domestico: Si
Registrado: Ave exotica: Kiwi | Edad: 1 anios | Peso: 0.4 kg | Duenio: Beto Perez | Alas: 18.5 cm
```

### 2. Registro de veterinarios

```text
Registrado: Carlos Lopez (Veterinario) | Cedula: 12345678 | Especialidad: Cirugia general
Registrado: Marta Salas (Veterinario) | Cedula: 87654321 | Especialidad: Medicina general
```

### 3. Citas agendadas

Se agendan tres citas, una por cada tipo de servicio.

```text
Cita C1 | Mascota: Max | Veterinario: Carlos Lopez | Consultorio 1 | 10/11/2026 10:00 | Consulta general | PROGRAMADA
Cita C2 | Mascota: Luna | Veterinario: Marta Salas | Consultorio 2 | 10/11/2026 11:00 | Vacunacion (Rabia) | PROGRAMADA
Cita C3 | Mascota: Kiwi | Veterinario: Carlos Lopez | Consultorio 3 | 11/11/2026 09:00 | Cirugia (complejidad 2, 1.5 horas) | PROGRAMADA
```

### 4. Una regla de negocio que se viola

Se intenta agendar otra cita con el doctor Carlos Lopez el 10/11/2026 a las 10:00, que es la misma hora de la cita C1. El programa no se cierra, solo avisa del error:

```text
Seleccione una mascota: 1
Seleccione un veterinario: 1
Seleccione un consultorio: 2
Fecha y hora (dd/MM/yyyy HH:mm): 10/11/2026 10:00
Servicio: 1) Consulta general  2) Vacunacion  3) Cirugia
Opcion: 1
Error: El veterinario ya tiene una cita a esa hora
```

### 5. Atender dos citas y generar los comprobantes

Se atienden las citas C2 (vacunación) y C3 (cirugía). Los costos se calculan según cada servicio:

- Vacunación: tarifa base de 350 más 180 de la vacuna, igual a 530.
- Cirugía: 1.5 horas por 800 por complejidad 2, igual a 2400.

```text
Cita C2 | Mascota: Luna | Veterinario: Marta Salas | Consultorio 2 | 10/11/2026 11:00 | Vacunacion (Rabia) | ATENDIDA
Comprobante No. 1 | Fecha de emision: 03/11/2026 01:02 | Total: $530.00

Cita C3 | Mascota: Kiwi | Veterinario: Carlos Lopez | Consultorio 3 | 11/11/2026 09:00 | Cirugia (complejidad 2, 1.5 horas) | ATENDIDA
Comprobante No. 2 | Fecha de emision: 03/11/2026 01:02 | Total: $2400.00
```

La cita C1 (consulta general) se queda como PROGRAMADA. Si se atiende también, se cobra la tarifa base de $350.00.

### 6. Polimorfismo: el sonido de cada mascota

```text
Max dice: Guau
Luna dice: Miau
Kiwi dice: Pio
```

### 7. Total cobrado e historial clínico

```text
Total cobrado: $2400.00   (Carlos Lopez)
Total cobrado: $530.00    (Marta Salas)

Historial clinico:
  - Cita C2 | Mascota: Luna | Veterinario: Marta Salas | Consultorio 2 | 10/11/2026 11:00 | Vacunacion (Rabia) | ATENDIDA
```

## Reglas de negocio

Todas las reglas se validan en las clases del modelo, no en el menú. Cuando una regla se rompe, la clase lanza una excepción con un mensaje y el menú la atrapa, muestra el error y vuelve a mostrar las opciones.

| Regla | Dónde se valida | Cómo |
| --- | --- | --- |
| La edad y el peso de una mascota deben ser mayores que cero | `Mascota.java` líneas 74 y 86 | `setEdad` y `setPeso` lanzan `IllegalArgumentException` |
| Un veterinario no puede tener dos citas a la misma hora | `Clinica.java` línea 144, método `agendarCita` | Revisa las citas existentes antes de crear la nueva |
| Un consultorio no puede usarse en dos citas a la misma hora | `Clinica.java` línea 147, método `agendarCita` | Igual que la regla anterior, pero con el consultorio |
| Una cita cancelada no puede pasar a atendida | `Cita.java` línea 118, método `atender` | Lanza `IllegalStateException` si el estado es CANCELADA |
| Una cita atendida no puede cancelarse | `Cita.java` línea 133, método `cancelar` | Lanza `IllegalStateException` si el estado es ATENDIDA |
| El comprobante solo se genera de una cita atendida | `Cita.java` línea 145, método `generarComprobante` | Lanza `IllegalStateException` en cualquier otro estado |
| Los costos no pueden ser negativos | `ConsultaGeneral.java` línea 31, `Vacunacion.java` línea 29 | Los constructores lanzan `IllegalArgumentException` |
| La complejidad de una cirugía va de 1 a 3 y las horas deben ser mayores que cero | `Cirugia.java` líneas 26 y 29 | El constructor lanza `IllegalArgumentException` |
| El nivel de energía de un perro va de 1 a 5 | `Perro.java` línea 33 | El constructor lanza `IllegalArgumentException` |
| El propietario debe estar registrado antes que su mascota | `Clinica.java` línea 123 | `registrarMascota` lanza `IllegalArgumentException` |
| La cédula del veterinario y el número de consultorio no se repiten | `Clinica.java` líneas 68 y 95 | Se revisan las listas antes de agregar |
| Todo cobro se puede consultar por mascota y por veterinario | `Clinica.java` líneas 189 y 204 | `totalCobrado` suma los servicios de las citas atendidas |
| El programa no se cierra con entradas inválidas | `MenuConsola.java` líneas 56, 427 y 461 | Los métodos que leen datos repiten la pregunta, y el menú atrapa las excepciones |

## Reflexión

Este proyecto me sirvió para entender mejor la programación orientada a objetos. Antes sabía qué era una clase, pero no tenía tan claro cuándo conviene heredar, cuándo usar una interfaz o cómo se relacionan los objetos entre sí. Hacer el diagrama antes del código ayudó bastante, porque cuando llegué a programar ya sabía qué clases necesitaba y cómo se conectaban.

**Lo que más aprendí.** La diferencia entre clase abstracta e interfaz, y entre agregación y composición. Para saber cuál usar, me ayudó preguntarme qué pasa con una parte si se elimina el todo. También aprendí que la multiplicidad de los diagramas de clases es muy parecida a la cardinalidad de Base de datos, aunque no sabía si era correcto usarla porque en clase no la habíamos visto con ese nombre. Otra cosa que me quedó clara es por qué se separan `Main` y `MenuConsola`: el `Main` solo arranca el programa y el menú es el que se repite hasta que el usuario elige salir.

**Lo más difícil.** Decidir qué debía ser una clase y qué solo un atributo, por ejemplo con la vacuna, y elegir entre agregación y composición en cada relación. También me costó entender cómo evitar que el programa se cierre con datos incorrectos. Al principio no entendía cuándo debía validar en el menú y cuándo en la clase, y al final entendí que las reglas deben estar en las clases del modelo.

**Qué mejoraría.**

- Los datos se pierden al cerrar el programa. Lo ideal sería guardarlos en archivos o en una base de datos.
- La búsqueda de citas por fecha ya está programada (`buscarCita(LocalDate)`), pero no tiene una opción en el menú.
- No hay forma de reprogramar una cita ni de modificar los datos de una mascota.
- Falta agregar pruebas automáticas para no tener que probar todo a mano.

## Uso de la IA en este proyecto

Utilicé la IA (Claude) como un tutor para este proyecto, como si fuera mi maestro. Mi profesor no estuvo disponible esta semana y no quería quedarme atascado, así que la usé para que me explicara las cosas antes de hacerlas.

La forma en que trabajé fue esta:

- **Primero entender la actividad.** Antes de pedir cualquier cosa, le pedí que me explicara el documento paso por paso y que me organizara todo lo que tenía que hacer. Cuando ya lo tenía claro, empezamos a construir cada parte.
- **Análisis y diseño.** Me guió para sacar las clases del caso de estudio (sustantivos y verbos) y para decidir qué debía ser clase, atributo, interfaz o clase abstracta. De ahí salió la tabla de [docs/analisis.md](docs/analisis.md). Aquí también me recomendó usar la multiplicidad en el diagrama, un tema que yo conocía como cardinalidad en Base de datos.
- **Diagrama y justificaciones.** Me ayudó a organizar las ideas y a armar el diagrama de clases en Lucidchart. Las justificaciones de las relaciones y los "qué pasa si se elimina alguna de las partes" los fui redactando con su ayuda y revisándolos yo.
- **Código.** Me ayudó cuando presenté estancamientos por cuestiones de lógica en alguna de las múltiples clases que contiene este proyecto. Debo reconocer que este proyecto es uno de los más extensos y complejos que hemos realizado hasta ahora en POO. Por ello, más de una vez llegué a perder el hilo de la lógica de programación del mismo. Allí es donde necesité la IA para no perder tiempo significativo en el desarrollo. Pero quiero aclarar que nunca le solicité que me entregara el código; siempre le pedí ejemplos similares que me ayudaran a comprender la lógica de mi proyecto a partir de la lógica de otros códigos.
- **Git.** Me explicó qué significaban algunos avisos, como el de los saltos de línea, y cómo dividir un commit en dos.

Lo que quiero dejar claro es que la IA me sirvió para entender y organizar, pero las decisiones finales del proyecto las revisé yo y me aseguré de poder explicar cómo funciona cada parte.

## Autor

**Edy de Jesus Chable Hernandez**
Matrícula: S25018145
GitHub: [github.com/Edy2802](https://github.com/Edy2802)
