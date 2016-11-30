package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * OilApp
 * Created by Nicolás on 30/11/16.
 */
public class ReportHelper {
    public static String promedioEnergia (List<MensajeEnergia> reportes) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        if(reportes.isEmpty()) {
            return "No hay reportes de energía para este periodo de tiempo";
        }
        int total = 0;
        for(int i=0; i<reportes.size(); i++){
            total += reportes.get(i).getConsumoEnergia();
        }
        return "El promedio del consumo de energía entre " + sdf.format(reportes.get(0).getFechaEnvio()) + " y " + sdf.format(reportes.get(reportes.size()-1).getFechaEnvio()) + " es " + total/reportes.size();
    }

    public static String promedioCaudal (List<MensajeCaudal> reportes) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        if(reportes.isEmpty()) {
            return "No hay reportes de caudal para este periodo de tiempo";
        }
        int total = 0;
        for(int i=0; i<reportes.size(); i++){
            total += reportes.get(i).getCaudal();
        }
        return "El promedio de los barriles extraídos entre " + sdf.format(reportes.get(0).getFechaEnvio()) + " y " + sdf.format(reportes.get(reportes.size()-1).getFechaEnvio()) + " es " + total/reportes.size();
    }

    public static String promedioTemperatura (List<MensajeTemperatura> reportes) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        if(reportes.isEmpty()) {
            return "No hay reportes de temperatura para este periodo de tiempo";
        }
        int total = 0;
        for(int i=0; i<reportes.size(); i++){
            total += reportes.get(i).getTemperatura();
        }
        return "El promedio de temperatura entre " + sdf.format(reportes.get(0).getFechaEnvio()) + " y " + sdf.format(reportes.get(reportes.size()-1).getFechaEnvio()) + " es " + total/reportes.size();
    }

    public static String maxEnergia (List<MensajeEnergia> reportes) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
        if(reportes.isEmpty()) {
            return "";
        }
        double max = Double.NEGATIVE_INFINITY;
        Date fecha = null;
        for(int i=0; i<reportes.size(); i++) {
            if(reportes.get(i).getConsumoEnergia()>max) {
                max = reportes.get(i).getConsumoEnergia();
                fecha = reportes.get(i).getFechaEnvio();
            }
        }
        return "El valor máximo de la energía fue " + max + " en la fecha " + sdf.format(fecha);
    }

    public static String maxCaudal (List<MensajeCaudal> reportes) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
        if(reportes.isEmpty()) {
            return "";
        }
        double max = -1;
        Date fecha = null;
        for(int i=0; i<reportes.size(); i++) {
            if(reportes.get(i).getCaudal()>max) {
                max = reportes.get(i).getCaudal();
                fecha = reportes.get(i).getFechaEnvio();
            }
        }
        return "El valor máximo del caudal fue " + max + " en la fecha " + sdf.format(fecha);
    }

    public static String maxTemperatura (List<MensajeTemperatura> reportes) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
        if(reportes.isEmpty()) {
            return "";
        }
        double max = -1;
        Date fecha = null;
        for(int i=0; i<reportes.size(); i++) {
            if(reportes.get(i).getTemperatura()>max) {
                max = reportes.get(i).getTemperatura();
                fecha = reportes.get(i).getFechaEnvio();
            }
        }
        return "El valor máximo de la temperatura fue " + max + " en la fecha " + sdf.format(fecha);
    }

    public static String minEnergia (List<MensajeEnergia> reportes) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
        if(reportes.isEmpty()) {
            return "";
        }
        double min = Double.POSITIVE_INFINITY;
        Date fecha = null;
        for(int i=0; i<reportes.size(); i++) {
            if(reportes.get(i).getConsumoEnergia()<min) {
                min = reportes.get(i).getConsumoEnergia();
                fecha = reportes.get(i).getFechaEnvio();
            }
        }
        return "El valor mínimo de la energía fue " + min + " en la fecha " + sdf.format(fecha);
    }

    public static String minCaudal (List<MensajeCaudal> reportes) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
        if(reportes.isEmpty()) {
            return "";
        }
        double min = Double.POSITIVE_INFINITY;
        Date fecha = null;
        for(int i=0; i<reportes.size(); i++) {
            if(reportes.get(i).getCaudal()<min) {
                min = reportes.get(i).getCaudal();
                fecha = reportes.get(i).getFechaEnvio();
            }
        }
        return "El valor mínimo del caudal fue " + min + " en la fecha " + sdf.format(fecha);
    }

    public static String minTemperatura (List<MensajeTemperatura> reportes) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
        if(reportes.isEmpty()) {
            return "";
        }
        double min = Double.POSITIVE_INFINITY;
        Date fecha = null;
        for(int i=0; i<reportes.size(); i++) {
            if(reportes.get(i).getTemperatura()<min) {
                min = reportes.get(i).getTemperatura();
                fecha = reportes.get(i).getFechaEnvio();
            }
        }
        return "El valor mínimo de la temperatura fue " + min + " en la fecha " + sdf.format(fecha);
    }

    public static String generarReporteEnergia(List<MensajeEnergia> reportes) {
        return promedioEnergia(reportes) + "." + maxEnergia(reportes) + "." + minEnergia(reportes);
    }

    public static String generarReporteCaudal(List<MensajeCaudal> reportes) {
        return promedioCaudal(reportes) + ". " + maxCaudal(reportes) + ". " + minCaudal(reportes);
    }

    public static String generarReporteTemperatura(List<MensajeTemperatura> reportes) {
        return promedioTemperatura(reportes) + ". " + maxTemperatura(reportes) + ". " + minTemperatura(reportes);
    }
}
