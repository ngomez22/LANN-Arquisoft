package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Nicolás on 30/08/16.
 */
@Entity
public class Zona extends Model {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    private Double area;
    public static Finder<Long,Zona> FINDER = new Finder<>(Zona.class);

    public Zona() {
        id = null;
        nombre = "NO NAME";
        area = -1.0;
    }

    public Zona(Long id) {
        this();
        this.id = id;
    }

    public Zona(Long id, String nombre, Double area) {
        this.id = id;
        this.nombre = nombre;
        this.area = area;
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

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }
}
