package com.unir.roleapp.error;

public class ErrorResponse {
    /*
    POSIBLES ERRORES:
    "NOT_FOUND": Si el recurso no se encuentra.
    "UNAUTHORIZED": Si el usuario no está autenticado o las credenciales son incorrectas.
    "BAD_REQUEST": Si los datos enviados en la solicitud son incorrectos o no válidos.
    "FORBIDDEN": Si el usuario tiene la autorización, pero no está autorizado a acceder al recurso.
    "INTERNAL_SERVER_ERROR": Para errores inesperados en el servidor.
     */

    private String error;
    private String message;

    // Constructor
    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    // Getters y setters
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
