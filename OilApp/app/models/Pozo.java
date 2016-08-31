package models;


import com.avaje.ebean.Model;

import javax.persistence.*;

import models.*;
@Entity
public class Pozo extends Model{
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Sensor sensorCaudal;

    @OneToOne
    private Sensor sensorEnergia;

    @OneToOne
    private Sensor sensorTemperatura;

    @OneToOne
    private Sensor sensorEmergencia;

    private String estado;

    @ManyToOne
    private Campo campo;

    private static final String CLAUSURADO ="Clausurado";
    private static final String ENPRODUCCION ="En produccion";
    private static final String PARADO ="Parado";
    private static final String ABIERTO ="Abierto";

    public Pozo(Sensor sensorCaudal, Sensor sensorEnergia, Sensor sensorTemperatura, Sensor sensorEmergencia, String estado, Campo campo) {
        this.sensorCaudal = sensorCaudal;
        this.sensorEnergia = sensorEnergia;
        this.sensorTemperatura = sensorTemperatura;
        this.sensorEmergencia = sensorEmergencia;
        this.estado = estado;
        this.campo = campo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sensor getSensorCaudal() {
        return sensorCaudal;
    }

    public void setSensorCaudal(Sensor sensorCaudal) {
        this.sensorCaudal = sensorCaudal;
    }

    public Sensor getSensorEnergia() {
        return sensorEnergia;
    }

    public void setSensorEnergia(Sensor sensorEnergia) {
        this.sensorEnergia = sensorEnergia;
    }

    public Sensor getSensorTemperatura() {
        return sensorTemperatura;
    }

    public void setSensorTemperatura(Sensor sensorTemperatura) {
        this.sensorTemperatura = sensorTemperatura;
    }

    public Sensor getSensorEmergencia() {
        return sensorEmergencia;
    }

    public void setSensorEmergencia(Sensor sensorEmergencia) {
        this.sensorEmergencia = sensorEmergencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Campo getCampo() {
        return campo;
    }

    public void setCampo(Campo campo) {
        this.campo = campo;
    }

    public void clausurarPozo()
    {
        this.estado = Pozo.CLAUSURADO;
    }
    public String generarReporte()
    {
        if(this.estado!=Pozo.ENPRODUCCION)
        {
            return null;        }
        else
        {
            return "";
        }

    }

    @Override
    public String toString()
    {
        return "Pozo{"+"id:"+id+"," +
                " estado:"+estado+", " +
                "sensores[sensorEmergencia:"+sensorEmergencia.toString()+", " +
                "sensorEnergia: "+sensorEnergia.toString()+", " +
                "sensorTemperatura:"+ sensorTemperatura.toString()+", " +
                "sensorCaudal:" +sensorCaudal.toString()+"}";
    }


}
