package ar.edu.unnoba.Proyecto.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY) //los datos se cargan solo cuando se necesitan con LAZY
    @JoinColumn(name = "usuario")//, nullable = false)
    //Si se accede con frecuencia a los datos de Usuario, es recomendable EAGER. Aunque también existen otros criterios para decidir.
    private Usuario usuario; //un usuario puede publicar muchas novedades, cada novedad es publicada por un solo usuario

    @Column(name = "usuario_id", nullable = false)
    private Long idUsuario;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha = new Date(); //por cada instancia se crea una fecha que solo se tendrá dia/mes/anio

    @Lob //permite trabajar con objetos pesados (imagen)
    @Column
    private byte[] imagen;

    public Evento() { //JPA necesita constructor sin parametros
    }

    public Evento(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
