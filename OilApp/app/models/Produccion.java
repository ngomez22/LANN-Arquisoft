package models;
import emums.*;

public class Produccion implements Estado{

    public void handle()
    {

    }

    public EstadoPozo get()
    {
        return EstadoPozo.PRODUCCION;
    }
}
