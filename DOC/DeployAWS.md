# Despliegue en AWS

Existen varios sistemas para despliegue, nosotros vamos a escoger el siguiente.

Partimos de que tenemos la imagen de Docker ya subida a docker hub, lo que nos debería dar algo como esto:

fjzamora93/springbootroleplay:latest


## Opción 1: Usando Amazon ECS (Elastic Container Service)

Amazon ECS es una forma fácil de ejecutar y administrar contenedores Docker en AWS. A continuación, los pasos básicos:

### 1. Crear un Cluster ECS:

En la consola de AWS, ve a ECS. Dentro seleccionada "Create Cluster" y asigna un nombre. Inmediatamente le puedes dar a crear. Puedes elegir un cluster EC2 o uno Fargate (sin servidor, que no requiere administrar instancias EC2).

TRas dos minutos debe haberse creado el cluster.


### Task DEfinitions

EN el menú lateral de la izquierda, dentro del panel principal de ECS, selecciona Task definition -> create new task definition.

- INfraestructura: pon requisitos minimo sde .25 vCPU y .5GB.
- COntainer: define el nombre springbootroleplay y la imagen URI fjzamora93/springbootroleplay:latest

### 2. Crear una tarea ECS:

Una vez tengas la tarea creada, crea un servicio que describa el contenedor a ejecutar. En el menú selecciona el cluster sobre el que quieras crear el servicio y la pestaña de Services -> Create.


Services | Tasks | INfraestructure



Aquí dentro asegurate de que en Application type esté marcado SErvice, ya que es una aplicación de larga duración que debe estar corriendo todo el tiempo. Task es para procesos puntuales.


En la configuración de la tarea, asegúrate de usar la imagen de Docker que subiste a Docker Hub (fjzamora93/springbootroleplay:latest).

PUedes dejar la configuración del resto por defecto y confirmar creando.


DEspués, dentro de CLuster > tuCLuster > tasks verás la tarea en sí... SI haces click dentro, a la derecha, podrás ver la IP pública de dicha tarea.



### 4. Ejecutar la tarea en el Cluster:

Inicia la tarea en el cluster ECS. Una vez que el contenedor se ejecute, AWS asignará una IP pública si es necesario.


### 5. Configurar un Load Balancer (si es necesario):

Si deseas exponer la aplicación a Internet, puedes configurar un Elastic Load Balancer (ELB) y asignarlo a tu servicio ECS.
    
    
