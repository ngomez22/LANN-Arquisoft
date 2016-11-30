package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.Date;

/**
 * Nicolás Gómez
 */

@Entity
@Table(name = "reportes_Temperatura")
public class MensajeTemperatura extends Model implements Mensaje{

    public static Model.Finder<Long,MensajeTemperatura> FINDER = new Model.Finder<>(MensajeTemperatura.class);

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @Constraints.Required
    private Pozo pozo;

    @Constraints.Required
    private Date fechaEnvio;

    @Constraints.Required
    private Double temperatura;

    public MensajeTemperatura() {
        id = null;
        pozo = null;
        fechaEnvio = null;
        temperatura = -1.0;
    }

    public MensajeTemperatura(Long id) {
        this();
        this.id = id;
    }

    public MensajeTemperatura(Long id, Pozo pozo, Date fechaEnvio, Double temperatura) {
        this.id = id;
        this.pozo = pozo;
        this.fechaEnvio = fechaEnvio;
        this.temperatura = temperatura;
    }

    public MensajeTemperatura (Double temperatura, Pozo pozo) {
        super();
        this.temperatura = temperatura;
        this.pozo=pozo;
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

    public Pozo getPozo() {
        return pozo;
    }

    public void setPozo(Pozo pozo) {
        this.pozo = pozo;
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

    @Override
    public String toString() {
        return "MensajeTemperatura{" +
                "id=" + id +
                ", pozo=" + pozo +
                ", fechaEnvio=" + fechaEnvio +
                ", temperatura=" + temperatura +
                '}';
    }
}
