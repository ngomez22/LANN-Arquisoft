package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;

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

    private Date fechaEnvio;

    @ManyToOne
    @JsonBackReference
    private Pozo pozo;

    private String emergencia;

    public MensajeEmergencia() {
        id = null;
        fechaEnvio = null;
        emergencia = "NO MESSAGE";
    }

    public MensajeEmergencia(Long id) {
        this();
        this.id = id;
    }

    public MensajeEmergencia(Long id, Date fechaEnvio, String emergencia) {
        this.id = id;
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
