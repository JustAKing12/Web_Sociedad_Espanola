package ar.edu.unnoba.Proyecto.model;

import jakarta.persistence.*;

import java.util.logging.Handler;

@Entity
@Table(name = "actividad")
public class Actividad{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String titulo;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String horario;

    public Actividad(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHorario(){
        return horario;
    }

    public void setHorario(String horario){
        this.horario = horario;
    }

}
