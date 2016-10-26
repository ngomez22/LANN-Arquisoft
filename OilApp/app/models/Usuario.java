package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Nicolás Gómez
 */
@Entity
@Table(name = "usuarios")
public class Usuario extends Model{

    public static Model.Finder<Long,Usuario> FINDER = new Model.Finder<>(Usuario.class);

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "usuariosId")
    private Long id;

    private String nombre;

    private Integer nivelAcceso;

    private String avatar;

    public Usuario() {
        this.id = null;
        this.nombre = "NO NAME";
        this.nivelAcceso = -1;
        this.avatar = null;
    }
    public Usuario(Long id) {
        this();
        this.id = id;
        this.avatar = "http://imgur.com/u0gpu69";
    }

    public Usuario(Long id, String nombre, Integer nivelAcceso, String avatar) {
        this.id = id;
        this.nombre = nombre;
        this.nivelAcceso = nivelAcceso;
        this.avatar = avatar;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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