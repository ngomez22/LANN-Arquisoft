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
public class MensajeEmergencia extends Model {

    public static Finder<Long,MensajeEmergencia> FINDER = new Finder<>(MensajeEmergencia.class);

    @Id
    @GeneratedValue
    private Long id;

    private Sensor sensor;

    private Date fechaEnvio;

    private String emergencia;

    public MensajeEmergencia() {
        id = null;
        sensor = null;
        fechaEnvio = null;
        emergencia = "NO MESSAGE";
    }

    public MensajeEmergencia(Long id) {
        this();
        this.id = id;
    }

    public MensajeEmergencia(Long id, Sensor sensor, Date fechaEnvio, String emergencia) {
        this.id = id;
        this.sensor = sensor;
        this.fechaEnvio = fechaEnvio;
        this.emergencia= emergencia;
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

    public String getEmergencia() {
        return emergencia;
    }

    public void setEmergencia(String emergencia) {
        this.emergencia = emergencia;
    }
}
