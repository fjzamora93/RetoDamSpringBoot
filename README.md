
# Autentificación

1. Para realizar cualquier petición, a partir de ahora, hay que disponer de tun token JWT. Para gestionar la autentificación se ha creado un nuevo directorio dentro del proyecto llamado "auth"

2. Para disponer del token de Acceso JWT hay que hacer en primer lugar un login. En la respuesta al login, encontraréis el token, bastará con copiar y pegarlo en la cabecera para el resto de peticiones.


# Arquitectura del módulo de auth

Al igual que el resto del proyecto, sigue una arquitectura básica de MVC dividido en las siguientes capas:

- Modelo, donde defineremos al usuario y una implementación de la interface UserDetails.
- Controller, con los endpoints para el usuario y la autentificación.
- Servicios, con la lógica de negocio a aplicar en cada petición.
- REpository, para establecer la conexión con la base de datos (postgres, en este caso).

Como observación, se ha optado por separar las responsabilidades del Usuario en sí mismo, de la autentificación. 

De esta manera, el @RestController relacionado con la autentificación tiene como responsabilidad única logear, registrar, hacer logout o refrescar un token. Este controlador no requiere de un bearer token dentro del header para poder realizar peticiones.

Por su parte, el @RestController UserControlller sí que requiere de un bearer token para poder realizar peticiones. Aquí el usuario podrá acceder a sus datos a través del @GetMapping(/"me"), u obtener todos los usuarios, borrar usuarios, etc. Como puede observarse en el controller para acceder a un usuario, este no se obtiene a través de un parámetro, sino que se obtiene desde el contexto de un usuario que ya esté autentificado, creando una capa de seguridad necesaria para que nadie excepto el propio usuario pueda realizar modificaciones sobre su perfil.

## Directorio security

DEntro de este directorio se encuentran las clases relacionadas con la configuración de seguridad:

- **JwtAuthenticationFIlter:** hereda de la clase ONcePerRequestFilter y se encarga de extraer el token y validarlo.
- **JwtTokenProvider:** crea un token de seguridad para que pueda ser usado en las peticiones.
- **SecurityConfig:** define qué endpoints están expuestos de manera pública y cuáles están restringidos.
- **SecurityCorsConfig:** simplemente define la política de qué tipo de peticiones se pueden hacer,  desde donde se pueden lanzar o si si aceptan cookies.

Además, las contraseñas están hasheadas gracias a la librería BCryptPasswordEncoder. Esto es algo que podemos ver en el @Service UserService y  quiere decir que si se inserta directamente una contraseña en la base de datos, la aplicación va a tratar de desencriptarlas, pero como jamás habían sido encriptadas, siempre van a provocar error.



# Deployment

Par el despliegue se han seguido los siguientes pasos:


## Hosting

Se han seleccionado un host para alojar la aplicación. Para el caso de SPringboot, se ha seleccionado Railway, y está disponible para realizar peticiones a través de esta URL:

https://retodam-production.up.railway.app/api

Lógicamente, ese endpoint va a dar error ya que será necesario estar autentificado para poder realizar cualquier tipo de petición.

## Dockerización

Se ha dockerizado la aplicación y se encuentra disponible en dockerhub: https://hub.docker.com/repository/docker/fjzamora93/retodam 

Para la dockerización, en primer lugar se ha creado un dockerfile tal que así:

```
# Usa una imagen de OpenJDK 19
FROM openjdk:19-jdk-slim

# Copia el archivo JAR generado a la imagen Docker
COPY target/SpringBootRetoDam-1.0-SNAPSHOT.jar /usr/app/SpringBootRetoDam-1.0-SNAPSHOT.jar

# Define el punto de entrada de la aplicación
ENTRYPOINT ["java", "-jar", "/usr/app/SpringBootRetoDam-1.0-SNAPSHOT.jar"]

# Exponer el puerto en el que tu app correrá
EXPOSE 8080
```

## Despliegue

Una vez tenemos el dockerfile, se ha creado un script que automatiza el proceso y siguen los siguientes pasos:


1. Compila el proyecto con Maven:


```
mvn clean install
```

2. Construye la imagen de docker:
```
docker build -t retodam .
```


3. Etiqueta la imagen de docker
```
docker tag retodam fjzamora93/retodam:latest
```


4. Sube la imagen a dockerhub
```
docker push fjzamora93/retodam:latest
```

El script completo (con logs para verificar errores o éxito) es el siguiente:


```
#!/bin/bash

set -e  # Detiene la ejecución si hay algún error

# Colores para mejorar la salida
green="\e[32m"
red="\e[31m"
reset="\e[0m"

# Paso 1: Compilar el proyecto con Maven
echo -e "${green}Compilando con Maven...${reset}"
if ! mvn clean install; then
    echo -e "${red}Error: Fallo en mvn clean install.${reset}"
    exit 1
fi

echo -e "${green}Compilación exitosa.${reset}"

# Paso 2: Construir la imagen Docker
echo -e "${green}Construyendo imagen Docker...${reset}"
if ! docker build -t retodam .; then
    echo -e "${red}Error: Fallo en docker build.${reset}"
    exit 1
fi

echo -e "${green}Imagen Docker creada con éxito.${reset}"

# Paso 3: Etiquetar la imagen Docker
echo -e "${green}Etiquetando imagen Docker...${reset}"
docker tag retodam fjzamora93/retodam:latest

echo -e "${green}Imagen etiquetada correctamente.${reset}"

# Paso 4: Subir la imagen a Docker Hub
echo -e "${green}Subiendo imagen a Docker Hub...${reset}"
if ! docker push fjzamora93/retodam:latest; then
    echo -e "${red}Error: Fallo en docker push. Verifica que estás autenticado.${reset}"
    exit 1
fi

echo -e "${green}Imagen subida con éxito a Docker Hub.${reset}"

echo -e "${green}Despliegue completado correctamente.${reset}"

```















