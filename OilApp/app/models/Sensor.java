package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Sensor extends Model{
    @Id
    @GeneratedValue
    private Long id;
    private String tipoSensor;

    public Sensor(String tipo){
        tipoSensor = tipo;
    }

    public String getTipo(){
        return tipoSensor;
    }

    public void setTipo(String tipo){
        tipoSensor=tipo;
    }
}
