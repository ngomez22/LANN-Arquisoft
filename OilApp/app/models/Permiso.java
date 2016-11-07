package models;

import be.objectify.deadbolt.java.models.Permission;
import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * OilApp
 * Created by Nicolás on 07/11/16.
 *
 */
@Entity
public class Permiso extends Model implements Permission{

    @Id
    @GeneratedValue
    private Long id;

    private String value;

    public static final Finder<Long, Permiso> FINDER = new Model.Finder<>(Permiso.class);

    @Override
    public String getValue() {
        return value;
    }

    public static Permiso permisoPorValor(String v){
        return FINDER.where().eq("value", v).findUnique();
    }
}
