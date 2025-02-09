# DEspliegue en AWS

Existen varios sistemas para despliegue, nosotros vamos a escoger el siguiente.

Partimos de que tenemos la imagen de Docker ya subida a docker hub, lo que nos debería dar algo como esto:

fjzamora93/springbootroleplay:latest


## Opción 1: Usando Amazon ECS (Elastic Container Service)

Amazon ECS es una forma fácil de ejecutar y administrar contenedores Docker en AWS. A continuación, los pasos básicos:
1. Crear un Cluster ECS:

    En la consola de AWS, ve a ECS.
    Crea un nuevo cluster. Puedes elegir un cluster EC2 o uno Fargate (sin servidor, que no requiere administrar instancias EC2).

2. Crear una tarea ECS:

    Una vez tengas el cluster, crea una definición de tarea que describa el contenedor a ejecutar.
    En la configuración de la tarea, asegúrate de usar la imagen de Docker que subiste a Docker Hub (fjzamora93/springbootroleplay:latest).

3. Configurar el contenedor en la tarea:

    Especifica los puertos que debe exponer el contenedor (en tu caso, el puerto 8080).
    Si estás usando un servicio de red como Fargate, asegúrate de configurar la red y la seguridad.

4. Ejecutar la tarea en el Cluster:

    Inicia la tarea en el cluster ECS.
    Una vez que el contenedor se ejecute, AWS asignará una IP pública si es necesario.

5. Configurar un Load Balancer (si es necesario):

    Si deseas exponer la aplicación a Internet, puedes configurar un Elastic Load Balancer (ELB) y asignarlo a tu servicio ECS.
    
    
## OPcion 2: Despliegue con EKS (que usa KUbernetes)
