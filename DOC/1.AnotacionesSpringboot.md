# Conceptos clave en la estructura Controller - Service - Repository


### @Autowired: Inyección de dependencias

**¿Qué es?**
Sistema de inyección de dependencias para no tener que estar instanciando manualmente cada Servicio y cada Repositorio.

**¿Cuándo usarlo?**

Por ejemplo, si tienes una clase CharacterService que depende de un CharacterRepository para interactuar con la base de datos, puedes utilizar @Autowired para que Spring inyecte automáticamente una instancia del repositorio en el servicio.

```java

// SIN AUTOWIRED: Tenemos que ir instanciando manualmente todo

@Service
public class CharacterService {

    private CharacterRepository characterRepository;

    // Constructor con inyección manual de dependencia
    public CharacterService() {
        this.characterRepository = new CharacterRepository();  // Aquí lo creas manualmente
    }



// CON AUTOWIRED: declaras el repositorio y servicio y automáticamente queda instanciado él y todas sus dependencias.

@Service
public class CharacterService {
    @Autowired private CharacterRepository characterRepository;
}


@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    @Autowired private CharacterService characterService;
}

```

### Optional<Class> : la alternativa al operador elvis ?:

**¿Qué es?**
La clase Optional en Java es una clase contenedora que puede o no contener un valor. En lugar de devolver null cuando no hay un valor presente, Optional permite que se maneje de forma más segura y explícita la ausencia de valores. Esto ayuda a evitar errores como NullPointerException y mejora la legibilidad del código.

```

public List<CharacterResponseDTO> getAllCharacters() {
        List<CharacterEntity> characters = characterRepository.findAll();
        return characters.stream()
                .map(this::mapToCharacterResponseDTO)
                .collect(Collectors.toList());
    }
    
```
 
 
En el ejemplo anterior es posible que no se devuevla ningún resultado. Pero en lugar de generar un nullPointerException, manejamos la situacion con un Optional.
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 

