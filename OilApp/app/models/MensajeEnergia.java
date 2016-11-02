package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.Date;

/**
 * Nicolás Gómez
 */
@Entity
@Table(name = "reportes_Energia")
public class MensajeEnergia extends Model {

    public static Finder<Long,MensajeEnergia> FINDER = new Finder<>(MensajeEnergia.class);

    @ManyToOne
    private Pozo pozo;

    @Id
    @GeneratedValue
    private Long id;

    @Constraints.Required
    private Date fechaEnvio;

    @Constraints.Required
    private Double consumoEnergia;

    public MensajeEnergia() {
        id = null;
        fechaEnvio = null;
        consumoEnergia = -1.0;
        pozo=null;
    }

    public MensajeEnergia(Long id) {
        this();
        this.id = id;
    }

    public MensajeEnergia (Long id, Date fechaEnvio, Double consumoEnergia, Pozo pozo) {
        this.id = id;
        this.fechaEnvio = fechaEnvio;
        this.consumoEnergia = consumoEnergia;
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

    public Pozo getPozo(){ return pozo;}

    public void setPozo(Pozo pozo){this.pozo=pozo;}

    @Override
    public String toString() {
        return "MensajeEnergia{" +
                "pozo=" + pozo +
                ", id=" + id +
                ", fechaEnvio=" + fechaEnvio +
                ", consumoEnergia=" + consumoEnergia +
                '}';
    }
}
