package ar.edu.unnoba.Proyecto.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "novedad")
public class Novedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) //obliga a que no sea nulo y que tenga una columna en la tabla
    private String titulo;

    @Column(nullable = false)
    private String descripcion;
//la idea es que se puedan utilizar los recursos de usuario, osea los metodos si es necesario
    //si no lo vamos a usar luego lo borro
    //por ahi puede ser necesario cuando abris una novedad, muestra quien es el creador
    @ManyToOne(fetch = FetchType.LAZY) //los datos se cargan solo cuando se necesitan con LAZY
    @JoinColumn(name = "usuario_id", nullable = false)
    //Si se accede con frecuencia a los datos de Usuario, es recomendable EAGER. Aunque también existen otros criterios para decidir.
    private Usuario usuario; //un usuario puede publicar muchas novedades, cada novedad es publicada por un solo usuario

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha = new Date(); //por cada instancia se crea una fecha que solo se tendrá dia/mes/anio

    @Lob //permite trabajar con objetos pesados (imagen)
    @Column(nullable = false)
    private byte[] imagen;

    public Novedad() { //JPA necesita constructor sin parametros
    }
    public Novedad(Long id, String titulo, String descripcion, Usuario usuario, byte[] imagen){
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.imagen = imagen;
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

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}