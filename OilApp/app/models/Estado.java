package models;
import emums.*;

public interface Estado
{
        public Estado change(EstadoPozo estado);
        public EstadoPozo get();

}