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

    @ManyToOne
    private Pozo pozo;

    private Date fechaEnvio;

    private Double caudal;

    public MensajeCaudal() {
        id = null;
        fechaEnvio = null;
        caudal = -1.0;
    }

    public MensajeCaudal(Long id) {
        this();
        this.id = id;
    }

    public MensajeCaudal(Long id, Date fechaEnvio, Double caudal) {
        this.id = id;
        this.fechaEnvio = fechaEnvio;
        this.caudal = caudal;
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

    public double getCaudal() {
        return caudal;
    }

    public void setCaudal(double caudal) {
        this.caudal = caudal;
    }

    @Override
    public String toString() {
        return "MensajeCaudal{" +
                "id=" + id +
                ", pozo=" + pozo +
                ", fechaEnvio=" + fechaEnvio +
                ", caudal=" + caudal +
                '}';
    }
}
