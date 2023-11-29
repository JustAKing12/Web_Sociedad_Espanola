package ar.edu.unnoba.Proyecto.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subscriptor")
public class Suscriptor {
//Cada vez que se crea un subcriptor el usuario carga email, nombre.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "suscriptores", cascade = CascadeType.ALL)
    private Set<Evento> eventos = new HashSet<>();

    public Suscriptor() {
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

    public void agregarEvento(Evento evento) {
        this.eventos.add(evento);
    }
}