package models;
import emums.*;

public class Produccion implements Estado{

    public Produccion() {}

    public Estado change(EstadoPozo estado)
    {
        System.out.println(estado);
        if(estado.equals(EstadoPozo.PARADO))
        {
            Estado es =new Detenido();
            System.out.println(es);
            return es;
        }
        else if(estado.equals(EstadoPozo.CLAUSURADO))
        {
            return new Clausurado();
        }

            return this;


    }


    public EstadoPozo get()
    {
        return EstadoPozo.PRODUCCION;
    }
}
