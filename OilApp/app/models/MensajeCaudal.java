package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Nicolás Gómez
 */
@Entity
@Table(name = "reportes_caudal")
public class MensajeCaudal extends Model {

    public static Finder<Long,MensajeCaudal> FINDER = new Finder<>(MensajeCaudal.class);

    @Id
    @GeneratedValue
    private Long id;

    //TODO: Relacion sensor-mensaje
    private Sensor sensor;

    private Date fechaEnvio;

    private Double caudal;

    public MensajeCaudal() {
        id = null;
        sensor = null;
        fechaEnvio = null;
        caudal = -1.0;
    }

    public MensajeCaudal(Long id) {
        this();
        this.id = id;
    }

    public MensajeCaudal(Long id, Sensor sensor, Date fechaEnvio, Double caudal) {
        this.id = id;
        this.sensor = sensor;
        this.fechaEnvio = fechaEnvio;
        this.caudal = caudal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public double getCaudal() {
        return caudal;
    }

    public void setCaudal(double caudal) {
        this.caudal = caudal;
    }
}
