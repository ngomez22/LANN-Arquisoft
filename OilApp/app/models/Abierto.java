package models;

import emums.*;

public class Abierto implements Estado{

    public void handle()
    {

    }


    public EstadoPozo get()
    {
        return EstadoPozo.ABIERTO;
    }


}
