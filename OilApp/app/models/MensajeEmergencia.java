package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.Date;

/**
 * Nicolás Gómez
 */
@Entity
@Table(name = "reportes_Emergencia")
public class MensajeEmergencia extends Model {

    public static Finder<Long,MensajeEmergencia> FINDER = new Finder<>(MensajeEmergencia.class);

    @Id
    @GeneratedValue
    private Long id;

    @Constraints.Required
    private Date fechaEnvio;

    @ManyToOne
    @Constraints.Required
    private Pozo pozo;

    @Constraints.Required
    private String emergencia;

    public MensajeEmergencia() {
        id = null;
        pozo = null;
        fechaEnvio = null;
        emergencia = "NO MESSAGE";
    }

    public MensajeEmergencia(Long id) {
        this();
        this.id = id;
    }

    public MensajeEmergencia(Long id, Pozo pozo, Date fechaEnvio, String emergencia) {
        this.id = id;
        this.pozo = pozo;
        this.fechaEnvio = fechaEnvio;
        this.emergencia= emergencia;
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

    public Pozo getPozo() {
        return pozo;
    }

    public void setPozo(Pozo pozo){this.pozo=pozo;}

    @Override
    public String toString() {
        return "MensajeEmergencia{" +
                "id=" + id +
                ", fechaEnvio=" + fechaEnvio +
                ", pozo=" + pozo +
                ", emergencia='" + emergencia + '\'' +
                '}';
    }
}
