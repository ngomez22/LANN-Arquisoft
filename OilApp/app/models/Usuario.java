package models;

import be.objectify.deadbolt.java.models.Permission;
import be.objectify.deadbolt.java.models.Role;
import be.objectify.deadbolt.java.models.Subject;
import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Nicolás Gómez
 */
@Entity
@Table(name = "usuarios")
public class Usuario extends Model implements Subject {

    public static final String JEFE_PRODUCCION = "Jefe de produccion";
    public static final String JEFE_CAMPO = "Jefe de campo";
    public static final String EMPLEADO = "Empleado";
    public static final String OTRO = "Otro";
    public static final String DEFAULT_AVATAR = "http://i.imgur.com/u0gpu69.png";

    public static Model.Finder<Long,Usuario> FINDER = new Model.Finder<>(Usuario.class);

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    @Constraints.Required
    private String username;

    @Constraints.Required
    private String password;

    @Constraints.Required
    private String nombre;

    private String avatar;

    private Integer edad;

    @Constraints.Required
    private String cargo;

    @ManyToMany
    public List<Rol> roles;

    @ManyToMany
    public List<Permiso> permisos;

    public Usuario() {
        this.id = null;
        this.username = null;
        this.password = null;
        this.nombre = "NO NAME";
        this.avatar = DEFAULT_AVATAR;
        this.edad= -1;
        this.cargo = OTRO;
        this.roles = new ArrayList<>();
        this.permisos = new ArrayList<>();
    }
    public Usuario(Long id) {
        this();
        this.id = id;
    }

    public Usuario(Long id, String username, String password, String nombre, String avatar, Integer edad, String cargo, List<Rol> roles, List<Permiso> permisos) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        if(avatar == null || avatar.equals(""))
            this.avatar = DEFAULT_AVATAR;
        else
            this.avatar = avatar;
        this.edad = edad;
        if(cargo==null || cargo.equals(""))
            this.cargo = OTRO;
        else
            this.cargo = cargo;
        this.roles = roles;
        this.permisos = permisos;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nombre='" + nombre + '\'' +
                ", avatar='" + avatar + '\'' +
                ", edad=" + edad +
                ", cargo='" + cargo + '\'' +
                ", roles=" + roles +
                ", permisos=" + permisos +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        if(cargo==null || cargo.equals(""))
            this.cargo = OTRO;
        else
            this.cargo = cargo;
    }

    public void definirRoles() {
        this.roles = new ArrayList<>();
        switch(cargo) {
            case JEFE_PRODUCCION:
                roles.add(Rol.rolPorNombre("jefeProduccion"));
                break;
            case JEFE_CAMPO:
                roles.add(Rol.rolPorNombre("jefeCampo"));
                break;
            case EMPLEADO:
                roles.add(Rol.rolPorNombre("empleado"));
                break;
            default:
                roles.add(Rol.rolPorNombre("otro"));
        }
    }

    @Override
    public List<? extends Role> getRoles() {
        return roles;
    }

    @Override
    public List<? extends Permission> getPermissions() {
        return permisos;
    }

    @Override
    public String getIdentifier() {
        return username;
    }


    //--------------------------------------------------------------

    public static Usuario encontrarUsuario(String u){
        return FINDER.where().eq("username", u).findUnique();
    }

    public static Usuario authenticate(String u, String p) {
        return FINDER.where().eq("username", u).eq("password", p).findUnique();
    }
}