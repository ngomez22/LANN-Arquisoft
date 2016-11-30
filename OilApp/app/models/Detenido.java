package models;
import emums.*;

public class Detenido implements Estado{

    public Detenido() {}
    public Estado change(EstadoPozo estado)
    {
        if(estado.equals(EstadoPozo.ABIERTO))
        {
            return new Abierto();
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
        return EstadoPozo.PARADO;
    }

}
