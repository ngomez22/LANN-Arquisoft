package models;
import emums.*;

public class Detenido implements Estado{

    public void handle()
    {

    }

    public EstadoPozo get()
    {
        return EstadoPozo.PARADO;
    }

}
