package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "campos")
public class Campo extends Model{

    public static Finder<Long,Campo> FINDER = new Finder<>(Campo.class);

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Usuario jefeDeCampo;

    private String region;

    @OneToMany(mappedBy = "campo")
    private List<Pozo> pozos;

    public Campo() {
        this.id = null;
        this.jefeDeCampo = null;
        this.region = null;
    }

    public Campo(Long id) {
        this();
        this.id = id;
    };

    public Campo(Usuario jefe, String regionN){
        jefeDeCampo = jefe;
        region = regionN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion(){
        return region;
    }

    public void setRegion(String regionN){
        region=regionN;
    }

    public Usuario getJefeDeCampo(){
        return jefeDeCampo;
    }

    public void setJefeDeCampo(Usuario jefe){
        jefeDeCampo=jefe;
    }

    public List<Pozo> getPozos() {
        return pozos;
    }

    public void setPozos(List<Pozo> pozos) {
        this.pozos = pozos;
    }

    @Override
    public String toString() {
        return "Campo{" +
                "id=" + id +
                ", jefeDeCampo=" + jefeDeCampo +
                ", region='" + region + '\'' +
                ", pozos=" + pozos +
                '}';
    }
}
