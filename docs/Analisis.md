# Modelo de análisis: PetCare

Documento de análisis del sistema de consola para la clínica veterinaria **PetCare**.
Se obtuvo subrayando los sustantivos (clases y atributos) y los verbos (métodos) del
caso de estudio.

**Autor:** [Edy de Jesus Chable Hernandez] · **Matrícula:** [S25018145]

## 1. Tabla de análisis de clases

Se identificaron **20 elementos** (el mínimo pedido es 12).

### Paquete `modelo.personas`

| Clase candidata | Tipo | Atributos principales | Responsabilidades (métodos) | Justificación |
| --- | --- | --- | --- | --- |
| `Persona` | Abstracta | `-nombre:String`, `-apellido:String`, `-telefono:String`, `-correo:String` | Constructor con parámetros; getters; setters que validan que el dato no esté vacío; `+obtenerRol():String` (abstracto); `+getNombreCompleto():String`; `+toString():String` | Reúne lo que comparten dueños, veterinarios y recepcionistas para no repetir código. Es abstracta porque en la clínica nunca existe "una persona" sin rol. |
| `Propietario` | Concreta | `-direccion:String` | Constructor con `super(...)`; `+getDireccion()`; `+setDireccion(String)`; `+obtenerRol()`; `+toString()` | Es el dueño de una o más mascotas. Su dato propio es la dirección. |
| `Veterinario` | Concreta | `-cedulaProfesional:String`, `-especialidad:String` | Constructor con `super(...)`; getters; `+obtenerRol()`; `+toString()` | Atiende las citas y es la base para consultar cobros por veterinario. Existe aunque la clínica cierre. |
| `Recepcionista` | Concreta | `-turno:String` | Constructor con `super(...)`; `+getTurno()`; `+setTurno(String)`; `+obtenerRol()`; `+toString()` | Personal administrativo con un dato propio (el turno). |

### Paquete `modelo.mascotas`

| Clase candidata | Tipo | Atributos principales | Responsabilidades (métodos) | Justificación |
| --- | --- | --- | --- | --- |
| `Mascota` | Abstracta | `-nombre:String`, `-edad:int`, `-peso:double`, `-propietario:Propietario`, `-historial:HistorialClinico` | Constructor que crea su propio historial; getters; `+setEdad(int)` y `+setPeso(double)` con validación (mayor que 0); `+hacerSonido():String` (abstracto); `+getHistorial()`; `+toString()` | Agrupa lo común de todas las mascotas. Es abstracta porque cada animal real es un perro, un gato o un ave, y cada uno suena distinto. |
| `Perro` | Concreta | `-raza:String`, `-nivelEnergia:int` (1 a 5) | Constructor con `super(...)`; `+hacerSonido()` devuelve "Guau"; `+toString()` | Tipo de mascota con raza y nivel de energía. |
| `Gato` | Concreta | `-esDomestico:boolean` | Constructor con `super(...)`; `+hacerSonido()` devuelve "Miau"; `+toString()` | Tipo de mascota con el dato propio `esDomestico`. |
| `AveExotica` | Concreta | `-longitudAlas:double` (mayor que 0) | Constructor con `super(...)`; `+hacerSonido()` devuelve "Pio"; `+toString()` | Tipo de mascota con longitud de alas. |
| `HistorialClinico` | Concreta | `-registros:List<Cita>` | `+agregarRegistro(Cita)`; `+obtenerRegistros():List<Cita>`; `+toString()` | Guarda las consultas de una mascota. Se crea dentro de la mascota y no tiene sentido sin ella (composición). |

### Paquete `modelo.servicios`

| Clase candidata | Tipo | Atributos principales | Responsabilidades (métodos) | Justificación |
| --- | --- | --- | --- | --- |
| `Facturable` | Interfaz | (ninguno) | `+calcularCosto():double`; `+obtenerDescripcion():String` | Define lo que debe saber hacer todo servicio que se cobra, sin importar cómo lo calcule. Permite sumar costos de distintos servicios por polimorfismo. |
| `ConsultaGeneral` | Concreta | `-tarifaBase:double`; constante `TARIFA_BASE` (static final) | Constructor por defecto (usa `TARIFA_BASE`) y otro que recibe la tarifa; `+calcularCosto()` devuelve la tarifa base; `+obtenerDescripcion()` | Servicio más simple. El costo es solo la tarifa base y no puede ser negativo. |
| `Vacunacion` | Concreta | `-nombreVacuna:String`, `-costoVacuna:double` | Constructor con parámetros; `+calcularCosto()` devuelve tarifa base + costo de la vacuna; `+obtenerDescripcion()` | Suma el costo de la vacuna. La vacuna se modela como atributo, no como clase. |
| `Cirugia` | Concreta | `-complejidad:int` (1 a 3), `-horasQuirofano:double`; constante `COSTO_HORA_QUIROFANO` | Constructor con parámetros; `+calcularCosto()` devuelve horas × costo por hora × complejidad; `+obtenerDescripcion()` | Es el cálculo más elaborado: depende de la complejidad y de las horas de quirófano. |

### Paquete `modelo.clinica`

| Clase candidata | Tipo | Atributos principales | Responsabilidades (métodos) | Justificación |
| --- | --- | --- | --- | --- |
| `Clinica` | Concreta | `-nombre:String`, `-veterinarios:List<Veterinario>`, `-recepcionistas:List<Recepcionista>`, `-consultorios:List<Consultorio>`, `-propietarios:List<Propietario>`, `-mascotas:List<Mascota>`, `-citas:List<Cita>` | `+agregarVeterinario(Veterinario)`; `+crearConsultorio(int)`; `+registrarPropietario(Propietario)`; `+registrarMascota(Mascota)`; `+agendarCita(...)` (valida horarios y consultorio); `+buscarCita(String)` y `+buscarCita(LocalDate)` (sobrecarga); `+totalCobrado(Veterinario)` y `+totalCobrado(Mascota)` (sobrecarga); `+darDeBajaMascota(Mascota)`; `+listarMascotas()` | Es el punto de entrada del sistema: contiene a los demás objetos y coordina el registro, la agenda y el cobro. Agrupa veterinarios (agregación) y crea sus consultorios (composición). |
| `Consultorio` | Concreta | `-numero:int` | Constructor que valida número mayor que 0; `+getNumero()`; `+toString()` | Espacio numerado que pertenece a la clínica y desaparece con ella. |
| `Cita` | Concreta | `-mascota:Mascota`, `-veterinario:Veterinario`, `-consultorio:Consultorio`, `-fechaHora:LocalDateTime`, `-estado:EstadoCita`, `-servicio:Facturable` | Constructor (estado inicial `PROGRAMADA`); `+atender()` (no permite si está cancelada y guarda el registro en el historial); `+cancelar()`; `+generarComprobante():Comprobante` (solo si fue atendida); getters; `+toString()` | Relaciona objetos independientes: una mascota, un veterinario y un consultorio en una fecha y hora (asociación). |
| `EstadoCita` | Enum | `PROGRAMADA`, `ATENDIDA`, `CANCELADA` | (constantes) | El estado solo puede tomar tres valores fijos; un enum evita valores inválidos. |
| `Comprobante` | Concreta | `-numero:int`, `-cita:Cita`, `-total:double`, `-fechaEmision:LocalDateTime`; `-contadorComprobantes:int` (static) | Constructor que toma el total del servicio de la cita; getters; `+toString()` | Documenta el cobro de una cita atendida. El contador static asigna el número consecutivo. |

### Paquete `app`

| Clase candidata | Tipo | Atributos principales | Responsabilidades (métodos) | Justificación |
| --- | --- | --- | --- | --- |
| `MenuConsola` | Concreta | `-clinica:Clinica`, `-teclado:Scanner` | `+iniciar()` (ciclo del menú); métodos privados para cada opción; lectura segura de números, fechas y textos no vacíos | Concentra la interacción con el usuario para que las clases del modelo no dependan de la consola. |
| `Main` | Concreta | (ninguno) | `+main(String[]):void` crea la clínica y lanza el menú | Punto de entrada del programa. |

## 2. Tres objetos de ejemplo

Los montos son propuestas y pueden ajustarse: `TARIFA_BASE = 350.00` y
`COSTO_HORA_QUIROFANO = 800.00`.

**Objeto 1: una mascota (con su dueño)**

| Objeto | Clase | Valores |
| --- | --- | --- |
| `ana` | `Propietario` | nombre = "Ana", apellido = "Ruiz", telefono = "2291234567", correo = "ana@correo.com", direccion = "Calle Hidalgo 12" |
| `max` | `Perro` | nombre = "Max", edad = 4, peso = 22.5, propietario = `ana`, raza = "Labrador", nivelEnergia = 4, historial = vacío |

**Objeto 2: un veterinario**

| Objeto | Clase | Valores |
| --- | --- | --- |
| `drLopez` | `Veterinario` | nombre = "Carlos", apellido = "López", telefono = "2297654321", correo = "clopez@petcare.com", cedulaProfesional = "12345678", especialidad = "Cirugía general" |

**Objeto 3: una cita con su servicio**

| Objeto | Clase | Valores |
| --- | --- | --- |
| `consultorio1` | `Consultorio` | numero = 1 |
| `vacunaRabia` | `Vacunacion` | nombreVacuna = "Rabia", costoVacuna = 180.00, `calcularCosto()` = 350.00 + 180.00 = **530.00** |
| `cita1` | `Cita` | mascota = `max`, veterinario = `drLopez`, consultorio = `consultorio1`, fechaHora = 2026-11-10 10:00, estado = `PROGRAMADA`, servicio = `vacunaRabia` |

## 3. Clases candidatas descartadas

| Candidata | Por qué se descartó |
| --- | --- |
| `Vacuna` | Solo aporta un nombre y un costo a la vacunación, sin comportamiento propio; basta con atributos de `Vacunacion`. |
| `Pago` | El caso solo pide el total de cada servicio y no maneja formas de pago; `Comprobante` ya cubre el cobro. |
| `Servicio` (abstracta) | Los tres servicios calculan el costo de forma distinta y comparten muy poco; la interfaz `Facturable` basta y evita una jerarquía innecesaria. |
| `Direccion` | Es un dato de `Propietario` sin comportamiento propio, por lo que es mejor un atributo `String`. |

