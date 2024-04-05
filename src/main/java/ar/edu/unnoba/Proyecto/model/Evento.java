package ar.edu.unnoba.Proyecto.model;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

@Entity
@Table(name = "evento")
public class Evento {
//Cada vez que se crea un evento el usuario carga titulo, descripcion, usuario
//Por ahora no es prioridad que se inserte una imagen, pero luego debe implementarse
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String titulo;

    @Column(nullable = false)
    @Size(max = 4000)
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER) //los datos se cargan solo cuando se necesitan con LAZY
    @JoinColumn(name = "usuario_id", nullable = false)
    //Si se accede con frecuencia a los datos de Usuario, es recomendable EAGER. Aunque también existen otros criterios para decidir.
    private Usuario usuario; //un usuario puede publicar muchas novedades, cada novedad es publicada por un solo usuario

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha = new Date(); //por cada instancia se crea una fecha que solo se tendrá dia/mes/anio

    @Lob //permite trabajar con objetos pesados (imagen)
    @Column

    private Blob imagen;

    public Evento() { //JPA necesita constructor sin parametros
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

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(MultipartFile file) throws IOException, SQLException {
        byte[] bytes = file.getBytes();
        this.imagen = new javax.sql.rowset.serial.SerialBlob(bytes);
    }
}
