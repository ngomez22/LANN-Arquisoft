package models;

import be.objectify.deadbolt.java.models.Role;
import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Nicolás on 07/11/16.
 */
@Entity
public class Rol extends Model implements Role {

    @Id
    @GeneratedValue
    public Long id;

    public String name;

    public static final Finder<Long, Rol> FINDER = new Model.Finder<>(Rol.class);

    @Override
    public String getName() {
        return name;
    }

    public static Rol rolPorNombre(String n){
        return FINDER.where().eq("name", n).findUnique();
    }
}
