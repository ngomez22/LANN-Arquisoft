package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Nicolás Gómez
 */
@Entity
@Table(name="sensores")
public class Sensor extends Model{

    @Id
    @GeneratedValue
    private Long id;

    //TODO: Revisar esta relación.
    @OneToOne
    private Pozo pozo;

    private String tipo;

    public  Sensor() {
        id = null;
        pozo = null;
        tipo = null;
    }

    public Sensor(Long id) {
        this();
        this.id = id;
    }

    public Sensor(Long id, Pozo pozo, String tipo) {
        this.id = id;
        this.pozo = pozo;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pozo getPozo() {
        return pozo;
    }

    public void setPozo(Pozo pozo) {
        this.pozo = pozo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", pozo=" + pozo.toString() +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
