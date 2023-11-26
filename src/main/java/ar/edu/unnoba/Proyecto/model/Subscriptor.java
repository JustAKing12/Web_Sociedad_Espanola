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
}