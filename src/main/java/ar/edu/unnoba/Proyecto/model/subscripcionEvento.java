package ar.edu.unnoba.Proyecto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "subscripcion_evento")
public class subscripcionEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //los datos se cargan solo cuando se necesitan con LAZY
    @JoinColumn(name = "subscriptor_id", nullable = false)
    private Subscriptor subscriptor;

    @ManyToOne(fetch = FetchType.LAZY) //los datos se cargan solo cuando se necesitan con LAZY
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subscriptor getSubscriptor() {
        return subscriptor;
    }

    public void setSubscriptor(Subscriptor subscriptor) {
        this.subscriptor = subscriptor;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
