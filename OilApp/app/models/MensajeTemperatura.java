package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Nicolás Gómez
 */

@Entity
@Table(name = "reportes_Temperatura")
public class MensajeTemperatura extends Model{

    public static Model.Finder<Long,MensajeTemperatura> FINDER = new Model.Finder<>(MensajeTemperatura.class);


    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Pozo pozo;

    //TODO: Relacion sensor-mensaje
    private Sensor sensor;

    private Date fechaEnvio;

    private Double temperatura;

    public MensajeTemperatura() {
        id = null;
        sensor = null;
        fechaEnvio = null;
        temperatura = -1.0;
    }

    public MensajeTemperatura(Long id) {
        this();
        this.id = id;
    }

    public MensajeTemperatura(Long id, Sensor sensor, Date fechaEnvio, Double temperatura) {
        this.id = id;
        this.sensor = sensor;
        this.fechaEnvio = fechaEnvio;
        this.temperatura = temperatura;
    }

    @PrePersist
    void creado() {
        this.fechaEnvio = new Date();
    }

    @Override
    public void save() {
        creado();
        super.save();
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

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }
}
