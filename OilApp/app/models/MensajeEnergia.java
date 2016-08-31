package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Nicolás Gómez
 */
@Entity
public class MensajeEnergia extends Model {

    public static Finder<Long,MensajeEnergia> FINDER = new Finder<>(MensajeEnergia.class);

    @Id
    @GeneratedValue
    private Long id;

    private Sensor sensor;

    private Date fechaEnvio;

    private Double consumoEnergia;

    public MensajeEnergia() {
        id = null;
        sensor = null;
        fechaEnvio = null;
        consumoEnergia = -1.0;
    }

    public MensajeEnergia(Long id) {
        this();
        this.id = id;
    }

    public MensajeEnergia (Long id, Sensor sensor, Date fechaEnvio, Double consumoEnergia) {
        this.id = id;
        this.sensor = sensor;
        this.fechaEnvio = fechaEnvio;
        this.consumoEnergia = consumoEnergia;
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

    public Double getConsumoEnergia() {
        return consumoEnergia;
    }

    public void setConsumoEnergia(Double consumoEnergia) {
        this.consumoEnergia = consumoEnergia;
    }
}
