package ar.edu.unnoba.comunidad.proyecto.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Evento {

    // Clave primaria id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idevento;

    // fecha y hora de publicación
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime publicationDatetime;

    private String titulo;
    private String contenido;

    public Long getIdevento() {
        return idevento;
    }

    public void setIdevento(Long idevento) {
        this.idevento = idevento;
    }

    public LocalDateTime getPublicationDatetime() {
        return publicationDatetime;
    }

    public void setPublicationDatetime(LocalDateTime publicationDatetime) {
        this.publicationDatetime = publicationDatetime;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
