package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Nicolás Gómez
 */
@Entity
@Table(name = "usuarios")
public class Usuario extends Model{

    public static final String DEFAULT_AVATAR = "http://i.imgur.com/u0gpu69.png";

    public static Model.Finder<Long,Usuario> FINDER = new Model.Finder<>(Usuario.class);

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "usuariosId")
    private Long id;

    private String nombre;

    private Integer nivelAcceso;

    private String avatar;

    private Integer edad;

    private String cargo;

    public Usuario() {
        this.id = null;
        this.nombre = "NO NAME";
        this.nivelAcceso = -1;
        this.avatar = DEFAULT_AVATAR;
        this.edad= -1;
        this.cargo = null;
    }
    public Usuario(Long id) {
        this();
        this.id = id;
    }

    public Usuario(Long id, String nombre, Integer nivelAcceso, String avatar, Integer edad, String cargo) {
        this.id = id;
        this.nombre = nombre;
        this.nivelAcceso = nivelAcceso;
        if(avatar == null || avatar.equals(""))
            this.avatar = DEFAULT_AVATAR;
        else
            this.avatar = avatar;
        this.edad = edad;
        this.cargo = cargo;
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
        if(avatar == null || avatar.equals(""))
            this.avatar = DEFAULT_AVATAR;
        else
            this.avatar = avatar;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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