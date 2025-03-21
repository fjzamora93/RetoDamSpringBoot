
# Autentificación

1. Para realizar cualquier petición, a partir de ahora, hay que disponer de tun token JWT.

2. Para disponer del token de Acceso JWT hay que hacer en primer lugar un login. En la respuesta al login, encontraréis el token, bastará con copiar y pegarlo en la cabecera para el resto de peticiones.

## Otros aspectos importantes
1. Las contraseñas están hasheadas, eso quiere decir que si se inserta directamente una contraseña en la base de datos, la aplicación va a tratar de desencriptarlas, pero como jamás habían sido encriptadas, siempre van a provocar error.
2. Cualquier usuario-contraseña que se quiera utilizar para postman, thunder o testar la API, debe haber sido creada con un login. Por ejemplo:
   
```
email: test@test.com
contraseña: 1234
```
 Ese usuario y contraseña deberían devolver un token válido. AUnque podéis usar el signup para crear tantos usuarios de prueba como queráis.




