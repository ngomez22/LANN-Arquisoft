package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Campo extends Model{

    @Id
    @GeneratedValue
    private Long id;

    private String region;

    private String tipoSensor;

    public Campo(String regionN){
        region = regionN;
    }

    public String getRegion(){
        return region;
    }

    public void setRegion(String regionN){
        region=regionN;
    }
}
