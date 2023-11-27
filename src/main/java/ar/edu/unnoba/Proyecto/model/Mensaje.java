package ar.edu.unnoba.Proyecto.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Mensaje {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min=2, max=30, message = "El nombre debe tener entre 2 y 30 caracteres")
    private String nombre;

    @NotBlank (message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

    @NotBlank (message = "La consulta es obligatoria")
    private String cuerpo;

    // Los getters y setters de cada atributo
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }
}