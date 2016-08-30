package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Nicolás Gómez
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    //public static Finder<Long,Usuario> FINDER = new Finder<>(Usuario.class);

    @Id
    @GeneratedValue//(strategy= GenerationType.SEQUENCE,generator = "usuariosId")
    private Long id;

    private String nombre;

    private Integer nivelAcceso;

    public Usuario() {
        this.id = null;
        this.nombre = "NO NAME";
        this.nivelAcceso = -1;
    }

    public Usuario(Long id) {
        this();
        this.id = id;
    }

    public Usuario(Long id, String nombre, Integer nivelAcceso) {
        this.id = id;
        this.nombre = nombre;
        this.nivelAcceso = nivelAcceso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(Integer nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nivelAcceso=" + nivelAcceso +
                '}';
    }
}