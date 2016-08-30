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

    private int cantidadBarriles;

    private int consumoEnergia;

    private Usuario jefeDeCampo;


    public Campo(String regionN, Usuario jefe){
        region = regionN;
        cantidadBarriles = 0;
        consumoEnergia = 0;
        jefeDeCampo = jefe;
    }

    public String getRegion(){
        return region;
    }

    public void setRegion(String regionN){
        region=regionN;
    }

    public int getCaudal(){
        return cantidadBarriles;
    }

    public void setCantidadBarriles(int caudal)
    {
        cantidadBarriles = caudal;
    }

    public int getConsumoEnergia(){
        return  consumoEnergia;
    }

    public void setConsumoEnergia(int consumo){
        consumoEnergia = consumo;
    }

    public Usuario getJefeDeCampo(){
        return jefeDeCampo;
    }

    public void setJefeDeCampo(Usuario jefe){
        jefeDeCampo=jefe;
    }
}
