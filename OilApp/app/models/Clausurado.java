package models;
import emums.*;

public class Clausurado implements Estado{

    public Estado change(EstadoPozo estado)
    {
        return this;
    }

    public EstadoPozo get()
    {
        return EstadoPozo.CLAUSURADO;
    }
}
