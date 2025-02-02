
# Estructura del proyecto

Variación de MVC(Modelo-VIsta-Controlador) que incluye capas de servicio en lugar de vistas, centrando la arquitectura en un controlador, servicio y repositorio.

```
src/main/java/com/example/rpgapp/
    ├── controller
    │   └── CharacterController.java
    ├── service
    │   └── CharacterService.java
    ├── repository
    │   └── CharacterRepository.java
    ├── entity
    │   ├── Character.java
    │   ├── User.java
    │   ├── Item.java
    │   ├── Spell.java
    │   └── Skill.java
    ├── dto
    │   ├── CharacterRequestDTO.java
    │   ├── CharacterResponseDTO.java
    │   ├── UserDTO.java
    │   ├── ItemDTO.java
    │   ├── SpellDTO.java
    │   └── SkillDTO.java
    ├── enum
    │   └── Status.java
    │   └── Priority.java
```

- **Controller:** Maneja las solicitudes HTTP y delega la lógica de negocio al servicio.
- **Service:** Contiene la lógica de negocio.
- **Repository:** Interactúa con la base de datos.
- **Entity:** Representa las tablas de la base de datos.
- **DTO:** Transfiere datos entre las capas.
- **Enum:** Define constantes para el estado y la prioridad de las tareas.




### 1. Controller

- **Responsabilidad:** Recibe las solicitudes (a través de las rutas y los métodos HTTP, como GET, POST, PUT, DELETE) desde el cliente y las procesa.

- **Lo que hace:** Dependiendo de la operación, invoca el servicio correspondiente (mediante métodos de la capa de Service) para manejar la lógica de negocio.
    
### 2. Service

- **Responsabilidad:** Maneja la lógica de negocio. Es el intermediario entre el Controller y Repository (la base de datos).

- **Lo que hace:**  Recibe la solicitud desde el Controller. Realiza las transformaciones necesarias de datos entre el formato de DTO y pasa la solicitud al Repositorio. Controla la lógica específica de la aplicación (validaciones, reglas de negocio, etc.). Mapea el resultado de la base de datos (que normalmente es un Domain o un ENtity) a un DTO para devolverlo al Controller.


### 3. Repository

- **Responsabilidad:** Interactúa directamente con la base de datos. Proporciona una capa de acceso a datos de alto nivel usando JPA (Java Persistence API).

- **Lo que hace:** Extiende JPARepository (o una de sus variantes). Gracias a esta extensión, obtiene métodos CRUD listos para usar (como save(), findById(), deleteById(), etc.). Puedes definir métodos personalizados en el repositorio para consultas más específicas.




## Fujo cliente servidor

>> Controlador recibe petición http y pasa la operación al Service >> 

Service >> Aplica la lógica de negocio(validaciones) y llama al REpository para acceder a los datos >>

>> REpository simplemente proporciona una capa de acceso a datos de alto nivel... pero esta es la capa que está haciendo las operaciones CRUD.




## LOs modelos y las entidades

- **Entity**:  Las entidades representan las tablas de la base de datos. Son las clases vinculadas a la persistencia, utilizando anotaciones de JPA. Están ligadas a la base de datos (por ejemplo, a través de @Entity, @Table, @Id, etc.). Pueden tener relaciones de tipo @OneToMany, @ManyToOne, 

- **RequestDTO**:  Encapsula los datos enviados por el cliente (por ejemplo, desde una aplicación frontend o API) al backend para crear o actualizar un recurso. Valida la entrada para asegurarse de que los datos enviados sean correctos (por ejemplo, que un campo email sea válido, que una fecha esté en el formato correcto). Mapea los datos del cliente a objetos que el backend puede manejar, transformando tipos como String a objetos más complejos como Date o Status.

- **ResponseDTO**:Encapsula los datos que el servidor envía al cliente como respuesta a una solicitud. Permite limitar los datos que se devuelven, evitando enviar información sensible o innecesaria (por ejemplo, no incluir contraseñas o campos privados). Ayuda a enviar solo los datos relevantes y con un formato estructurado adecuado para el cliente.


## Arquitectura hexagonal
En una arquitectura hexagonal (QUE NO ES EL CASO) o de ddd, se incluiría una capa de Domain, por lo que todos los dto deberían mapearse primero a domain y luego a entity, y del mismo modo, todos los entities deberían mapearse primero a domain y luego a dto. 

Del mismo modo, se incluiría una capa intermedia entre Repository y Service llama "Gateway". Es aquí donde se realizaría el mapeado de las clases.





