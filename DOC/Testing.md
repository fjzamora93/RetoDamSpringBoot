
# EJecución de tests

```bash
	mvn test
```


# GEneración de tests

## Controllers

Tests de endpoints y validación de respuestas utilizando herramientas como MockMvc. MockMvc es una herramienta proporcionada por Spring Boot para probar controladores sin necesidad de levantar un servidor real. Veamos como funciona:

```java

    @Test
    void testGetUserByEmailAndPassword() throws Exception {
        UserDTO user = new UserDTO(1L,"nombre ejemplo", "test@example.com", "password123",characterEntities );
        when(userService.getUserByEmailAndPassword("test@example.com", "password123")).thenReturn(user);

        mockMvc.perform(get("/api/user/login")
                        .param("email", "test@example.com")
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }
    
```

1. Configuramos el comportamiento de userSErvice (para eso usamos el when, que retornará el user que hemos creado).
2. Después, ejecutamos la petición HTTP con los parámetros que nos interesan.
3. MockMvc se encarga de realizar la comprobación sin necesidad de arrancar el servidor real. 


## Services

Tests unitarios que aislan la lógica de negocio. En estos tests, es común utilizar mocks (simulaciones de objetos reales) para aislar la lógica del servicio de otras capas (por ejemplo, utilizando Mockito para simular el comportamiento de los repositorios).

UN ejemplo clásico de mockit es la funcionalidad "when", que básicamente viene a decir lo siguiente:

*"Cuando se intente realizar X operación, en lugar de realizarla, devuelve este otro objeto que yo tengo creado y que sé que funciona (o que debe fallar)"*



```java

    @Test
    void testGetUserById() {
        User user = new User(1L,"nombre ejemplo", "test@example.com", "password123" , new ArrayList<>());
        UserDTO userDTO = new UserDTO(1L,"nombre ejemplo", "test@example.com", "password123", new ArrayList<>() );


    
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

```

En el ejemplo anterior se hacen dos supuesto de MOckito:
1. SI alguien busca en rel repositorio, que devuelva directamente el OPtional de User.
2. Si alguien intenta mapear, devuelve este objeto ya mapead (UserDTO).

A continaución, pasamos a las aserciones:
1. GUardamos el resultado del test unitoario de userService.
2. Hacemos una primera aserción de que el resultado no es nulo.
3. Hacemos una aserción de lo que esperamos de un campo, por ejemplo, el email.



## Repositories

Los test en los repositorio se centran en crear un repositorio con H2 en la memoria local con objetos simulados y realizar test sobre dicha memoria. Hay dos objetivos en estos test:


1. Comprobar qeu el objeto se recupera o no de la base de datos (assertTrue o assertFalse).

2. COmprobar que el objeto recuperado tiene la estructura correcta (por ejemplo, si hacemos una búsqueda por email, verficiar que efectivamente ese atributo existe en el objeto recuperado).



Veamos un ejemplo:

```java
    @Test
    public void testFindByEmail() {
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        assertTrue(foundUser.isPresent(), "Usuario debería ser encontrado");
        assertEquals(user.getEmail(), foundUser.get().getEmail(), "El email del usuario debe coincidir");
    }

    @Test
    public void testFindByEmailNotFound() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");
        assertFalse(foundUser.isPresent(), "Usuario no debería ser encontrado");
    }
```

#### POsibles fallos en los test del repositorio

Si se siguen las convenciones de SPringBoot, es muy difícil fallar estos tests siempre que la base de datos sea correcta.

- La configuración de la base de datos en los tests no está bien definida (por ejemplo, si no se crea correctamente la base de datos en memoria para las pruebas).
- Hay problemas con la lógica de las consultas personalizadas, como los @Query o criterios más complejos.
- La entidad o los datos en la base de datos no están configurados correctamente para los casos que se prueban.



