package models;
import emums.*;

public class Clausurado implements Estado{

    public void handle()
    {

    }

    public EstadoPozo get()
    {
        return EstadoPozo.CLAUSURADO;
    }
}
