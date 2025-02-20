# Pasos para dockerizar

### 1. CRear un Dockerfile en el directorio raiz del proyecto

```bash
mi-aplicacion/
├── Dockerfile
├── target/
│   └── mi-aplicacion.jar
├── src/
└── pom.xml (si usas Maven)
```

### 2. 	Compilar y construir el jar

3. ANtes de consturir la imagen del docker, seguramente haya que compilar el proyecto. En consola escribe esto:

```
mvn clean install 

```
Esto generará las classes y el jar necesario para docker.



### 3. COntenido del dockerfil:

Asegúrate de que el Dockerfile copie el archivo JAR generado en la imagen Docker. Por ejemplo:

```
# Usa una imagen de OpenJDK
FROM openjdk:19-jdk-slim


# Copia el archivo JAR generado a la imagen Docker
COPY target/mi-aplicacion.jar /usr/app/mi-aplicacion.jar

# Define el punto de entrada de la aplicación
ENTRYPOINT ["java", "-jar", "/usr/app/mi-aplicacion.jar"]

# Exponer el puerto en el que tu app correrá
EXPOSE 8080

```


EN este punto es posible que haya que hacer un pull de la versión adecuada de Docker para que funcione correctamente.

docker pull openjdk:19-jdk-slim


### 4. Construcción de la imagen Docker: 

```bash
docker build -t mi-aplicacion .

# EN nuestro caso sería:
 docker build -t springbootroleplay .


```



### 6. Ejecutar la imagen Docker

```bash
docker run -p 8080:8080 springbootroleplay .
docker run -p 8080:8080 mi-aplicacion
```


### Etiquetar la imagen de docke rantes de subirla a docker hub


```bash
docker tag springbootroleplay <tu-usuario-docker-hub>/springbootroleplay:<tag>

docker tag springbootroleplay fjzamora93/springbootroleplay:latest

```
### Haz un push para subir a docker HUb

```bash

docker push fjzamora93/springbootroleplay:latest

```

SI todo salió bien, desde docker hub deberías poder ver que se ha subido el repositorio. Puedes acceder a una imagen concreta utilizando tu nombre de usuario, repositorio y tag, tal que así:

fjzamora93/springbootroleplay:latest

A partir de aquí, los siguientes pasos se harían en AWS.



