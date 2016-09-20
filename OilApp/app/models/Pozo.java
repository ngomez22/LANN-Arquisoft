package models;


import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="pozos")
public class Pozo extends Model{

    public static Finder<Long,Pozo> FINDER = new Finder<>(Pozo.class);

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy ="pozo")
    private List<MensajeEmergencia> sensorEmergencia;

    @OneToMany(mappedBy ="pozo")
    private List<MensajeCaudal> sensorCaudal;

    @OneToMany(mappedBy ="pozo")
    private List<MensajeEnergia> sensorEnergia;

    @OneToMany(mappedBy ="pozo")
    private List<MensajeTemperatura> sensorTemperatura;

    private String estado;

    @ManyToOne
    private Campo campo;

    private static final String CLAUSURADO ="Clausurado";
    private static final String ENPRODUCCION ="En produccion";
    private static final String PARADO ="Parado";
    private static final String ABIERTO ="Abierto";

    public Pozo(List<MensajeEmergencia> sensorEmergencia, List<MensajeCaudal> sensorCaudal, List<MensajeEnergia> sensorEnergia, List<MensajeTemperatura> sensorTemperatura, String estado, Campo campo) {
        this.sensorEmergencia = sensorEmergencia;
        this.sensorCaudal = sensorCaudal;
        this.sensorEnergia = sensorEnergia;
        this.sensorTemperatura = sensorTemperatura;
        this.estado = estado;
        this.campo = campo;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Campo getCampo() {
        return null ;}

    public void setCampo(Campo campo) {
        this.campo = campo;
    }

    public void clausurarPozo()
    {
        this.estado = Pozo.CLAUSURADO;
    }

    public void detenerPozoEmergencia()
    {
        this.setEstado(Pozo.PARADO);
    }

    public void reabrirPozo()
    {
        if(this.estado==Pozo.PARADO)
        {
            this.setEstado(Pozo.ENPRODUCCION);
        }
    }

    public boolean generarReporte()
    {
        boolean puede=false;
        if(this.estado==Pozo.ENPRODUCCION)
        {
            return puede;
        }

            return puede;

    }

    @Override
    public String toString()
    {
        return "Pozo{"+"id:"+id+"," +
                " estado:"+estado+", " +
                "sensores[sensorEmergencia:"+sensorEmergencia.toString()+", " +
                "sensorEnergia: "+sensorEnergia.toString()+", " +
                "sensorTemperatura:"+ sensorTemperatura.toString()+", " +
                "sensorCaudal:" +sensorCaudal.toString()+", " +
                "campo:"+campo.toString()+"}";
    }


}
