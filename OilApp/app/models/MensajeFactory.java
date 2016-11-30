package models;

/**
 * OilApp
 * Created by Nicolás on 29/11/16.
 */
public class MensajeFactory {

    public static final int ENERGIA = 1;
    public static final int CAUDAL = 2;
    public static final int TEMPERATURA = 3;
    public static final int EMERGENCIA = 4;

    public Mensaje createMensaje (int tipo, Pozo pozo, double data) throws Exception {
        switch (tipo) {
            case ENERGIA:
                return new MensajeEnergia(data, pozo);
            case CAUDAL:
                return new MensajeCaudal(data, pozo);
            case TEMPERATURA:
                return new MensajeTemperatura(data, pozo);
            default:
                throw new Exception("Argumentos innválidos para el tipo de mensaje deseado");

        }
    }
    public Mensaje createMensaje (int tipo, Pozo pozo, String data) throws Exception {
        if(tipo == EMERGENCIA) {
            return new MensajeEmergencia(data, pozo);
        } else {
            throw new Exception("Argumentos innválidos para el tipo de mensaje deseado");
        }
    }
}
