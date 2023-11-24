package ar.edu.unnoba.Proyecto.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "subscriptor")
public class Subscriptor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nombre;

    @ManyToMany
    @JoinTable(name = "subscripcion_evento",
            joinColumns = @JoinColumn(name = "subscriptor_id"), //joinColumns indican las columnas que se relacionan con la entidad propietaria de la asociación
            inverseJoinColumns = @JoinColumn(name = "evento_id")) //inverseJoinColumns indican las columnas que se relacionan con la entidad inversa de la asociación
    private Set<Evento> eventos; //un suscriptor puede suscribirse a muchos eventos, un evento puede tener muchos suscriptores

    public Subscriptor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(Set<Evento> eventos) {
        this.eventos = eventos;
    }

    public void enviarNotificacion(Evento evento) {
        //aquí puedes usar alguna librería o API para enviar un correo electrónico al suscriptor
        //por ejemplo, puedes usar JavaMail API (https://javaee.github.io/javamail/)
        //o puedes usar algún servicio externo como SendGrid (https://sendgrid.com/docs/for-developers/sending-email/java/)
        //el contenido del correo podría ser algo como:

        String asunto = "Nuevo evento: " + evento.getTitulo();
        String mensaje = "Hola " + this.nombre + ",\n\n"
                + "Te informamos que hay un nuevo evento disponible: " + evento.getTitulo() + ".\n"
                + "Descripción: " + evento.getDescripcion() + ".\n"
                + "Fecha: " + evento.getFecha() + ".\n"
                + "Publicado por: " + evento.getUsuario().getUsername() + ".\n\n"
                + "Esperamos que te interese y puedas asistir.\n\n"
                + "Saludos,\n"
                + "Equipo de Eventos";
        //aquí iría el código para enviar el correo con el asunto y el mensaje
    }
}