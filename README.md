
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





