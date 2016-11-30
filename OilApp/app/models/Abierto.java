package models;

import emums.*;

public class Abierto implements Estado{

    public Abierto() {}

    public Estado change(EstadoPozo estado)
    {

        if(estado.equals(EstadoPozo.PRODUCCION))
        {
            return new Produccion();
        }
        else
        {
            return this;
        }
    }


    public EstadoPozo get()
    {
        return EstadoPozo.ABIERTO;
    }


}
