package models;

import java.util.Date;

/**
 * OilApp
 * Created by Nicolás on 29/11/16.
 */
public interface Mensaje {
    Long getId ();
    void setId (Long id);
    Date getFechaEnvio ();
    void setFechaEnvio (Date fechaEnvio);
    Pozo getPozo ();
    void setPozo (Pozo pozo);

}
