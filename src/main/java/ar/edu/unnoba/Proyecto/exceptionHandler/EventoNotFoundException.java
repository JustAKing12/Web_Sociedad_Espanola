package ar.edu.unnoba.Proyecto.exceptionHandler;

public class EventoNotFoundException extends RuntimeException {

    public EventoNotFoundException(String message) {
        super(message);
    }

    public EventoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventoNotFoundException(Throwable cause) {
        super(cause);
    }
}
