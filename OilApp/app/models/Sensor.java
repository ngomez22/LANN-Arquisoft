package models;

import com.avaje.ebean.Model;

import javax.persistence.Id;

/**
 * Nicolás Gómez
 */
public class Sensor extends Model{

    @Id
    private Long id;

    private Campo campo;

    private String tipo;

    public  Sensor() {
        id = null;
        campo = null;
        tipo = null;
    }

    public Sensor(Long id) {
        this();
        this.id = id;
    }

    public Sensor(Long id, Campo campo, String tipo) {
        this.id = id;
        this.campo = campo;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Campo getCampo() {
        return campo;
    }

    public void setCampo(Campo campo) {
        this.campo = campo;
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
                ", campo=" + campo +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
