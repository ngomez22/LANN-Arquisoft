package models;
import emums.*;

public class Produccion implements Estado{

    public Estado change(EstadoPozo estado)
    {
        if(estado.equals(EstadoPozo.PARADO))
        {
            return new Detenido();
        }
        else if(estado.equals(EstadoPozo.CLAUSURADO))
        {
            return new Clausurado();
        }
        else
        {
            return this;
        }

    }


    public EstadoPozo get()
    {
        return EstadoPozo.PRODUCCION;
    }
}
