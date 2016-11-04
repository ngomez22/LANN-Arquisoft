package models;


import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import emums.EstadoPozo;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="pozos")
public class Pozo extends Model{

    public static Finder<Long,Pozo> FINDER = new Finder<>(Pozo.class);

    @Id
    @GeneratedValue
    private Long id;

    @Constraints.Required
    private Double latitud;

    @Constraints.Required
    private Double longitud;

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="pozo")
    @JsonBackReference(value = "reportes_Emergencia")
    @OrderBy("fecha_Envio asc")
    private List<MensajeEmergencia> sensorEmergencia;

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="pozo")
    @JsonBackReference(value = "reportes_Caudal")
    @OrderBy("fecha_Envio asc")
    private List<MensajeCaudal> sensorCaudal;

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="pozo")
    @JsonBackReference(value = "reportes_Energia")
    @OrderBy("fecha_Envio asc")
    private List<MensajeEnergia> sensorEnergia;

    @OneToMany(fetch = FetchType.LAZY, mappedBy ="pozo")
    @JsonBackReference(value = "reportes_Temperatura")
    @OrderBy("fecha_Envio asc")
    private List<MensajeTemperatura> sensorTemperatura;

    @Enumerated(EnumType.STRING)
    private EstadoPozo estado;

    @ManyToOne
    private Campo campo;

    public Pozo(Double latitud, Double longitud, List<MensajeEmergencia> sensorEmergencia, List<MensajeCaudal> sensorCaudal, List<MensajeEnergia> sensorEnergia, List<MensajeTemperatura> sensorTemperatura, EstadoPozo estado, Campo campo) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.sensorEmergencia = sensorEmergencia;
        this.sensorCaudal = sensorCaudal;
        this.sensorEnergia = sensorEnergia;
        this.sensorTemperatura = sensorTemperatura;
        this.estado = estado;
        this.campo = campo;
    }

    public Pozo()
    {
        latitud =-1.0;
        longitud =-1.0;
        estado = EstadoPozo.ABIERTO;
        this.sensorEmergencia = new ArrayList();
        this.sensorCaudal = new ArrayList();
        this.sensorEnergia = new ArrayList();
        this.sensorTemperatura = new ArrayList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public EstadoPozo getEstado() {
        return estado;
    }

    public Campo getCampo() {
        return campo ;
    }

    public void setCampo(Campo campo) {
        this.campo = campo;
    }

    public List<MensajeEmergencia> getSensorEmergencia() {
        return sensorEmergencia;
    }

    public List<MensajeCaudal> getSensorCaudal() {
        return sensorCaudal;
    }

    public List<MensajeEnergia> getSensorEnergia() {
        return sensorEnergia;
    }

    public List<MensajeTemperatura> getSensorTemperatura() {
        return sensorTemperatura;
    }

    public void clausurarPozo() throws Exception {
        if(this.estado.equals(EstadoPozo.PARADO) || this.estado.equals(EstadoPozo.PRODUCCION))
            this.estado = EstadoPozo.CLAUSURADO;
        else
            throw new Exception("No se puede clausurar el pozo");
    }

    public void detenerPozoEmergencia() throws Exception {
        if(this.estado.equals(EstadoPozo.PRODUCCION))
            this.estado = EstadoPozo.PARADO;
        else
            throw new Exception("No se puede para el pozo porque no está en producción");
    }

    public void reabrirPozo() throws Exception
    {
        if(this.estado.equals(EstadoPozo.PARADO))
            this.estado = EstadoPozo.ABIERTO;
        else
            throw new Exception("El pozo no se puede reabrir porque no está parado");
    }

    public void iniciarProduccionPozo() throws Exception {
        if(this.estado.equals(EstadoPozo.ABIERTO) || this.estado.equals(EstadoPozo.PARADO))
            this.estado = EstadoPozo.PRODUCCION;
        else
            throw new Exception("El pozo está clausurado");
    }

    public boolean generarReporte() {
        return this.estado==EstadoPozo.PRODUCCION;
    }

    @Override
    public String toString() {
        return "Pozo{" + "id:" + id + "," +
                " estado:" + estado + ", " +
                "sensores[sensorEmergencia:" + sensorEmergencia.toString() + ", " +
                "sensorEnergia: " + sensorEnergia.toString() + ", " +
                "sensorTemperatura:" + sensorTemperatura.toString() + ", " +
                "sensorCaudal:" + sensorCaudal.toString() + ", " +
                "campo:" + campo.toString() + "}";
    }
}
