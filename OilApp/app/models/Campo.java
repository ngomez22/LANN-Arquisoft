package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

@Entity
public class Campo extends Model{

    public static Finder<Long,Campo> FINDER = new Finder<>(Campo.class);

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Usuario jefeDeCampo;

    @ManyToOne
    private Zona region;

    public Campo() {
        this.id = null;
        this.jefeDeCampo = null;
        this.region = null;
    }

    public Campo(Long id) {
        this();
        this.id = id;
    };

    public Campo(Usuario jefe, Zona regionN){
        jefeDeCampo = jefe;
        region = regionN;
    }

    public Zona getRegion(){
        return region;
    }

    public void setRegion(Zona regionN){
        region=regionN;
    }

    public Usuario getJefeDeCampo(){
        return jefeDeCampo;
    }

    public void setJefeDeCampo(Usuario jefe){
        jefeDeCampo=jefe;
    }
}
