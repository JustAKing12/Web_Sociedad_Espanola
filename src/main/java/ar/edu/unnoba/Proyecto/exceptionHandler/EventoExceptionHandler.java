package ar.edu.unnoba.Proyecto.exceptionHandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class EventoExceptionHandler {

    @ExceptionHandler(EventoNotFoundException.class) //404
    public ModelAndView handleUsuarioNotFoundException(EventoNotFoundException usuario) {
        ModelAndView modelAndView = new ModelAndView("exception/evento-not-found");
        modelAndView.addObject("errorMessage", usuario.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class) //400
    public ModelAndView handleException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView("exception/error");
        modelAndView.addObject("errorMessage", exception.getMessage());
        return modelAndView;
    }
}
