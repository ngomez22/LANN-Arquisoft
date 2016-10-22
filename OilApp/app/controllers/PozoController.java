package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.MensajeCaudal;
import models.MensajeEnergia;
import models.MensajeTemperatura;
import models.Pozo;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.libs.Json.toJson;

/**
 * Created by Nicolas Vasquez on 30/08/2016.
 */
public class PozoController extends Controller{

    public CompletionStage<Result> getPozos() {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(
                        () -> {
                            return Pozo.FINDER.all();
                        }
                        ,jdbcDispatcher)
                .thenApply(
                        pozos -> {
                            return ok(toJson(pozos));
                        }
                );
    }

    public CompletionStage<Result> createPozo(){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nPozo = request().body().asJson();
        Pozo pozo = Json.fromJson( nPozo , Pozo.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    pozo.save();
                    return pozo;
                }
        ).thenApply(
                pozo1 -> {
                    return ok(Json.toJson(pozo1));
                }
        );
    }

    public CompletionStage<Result> updatePozo(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nPozo= request().body().asJson();
        Pozo pozo = Json.fromJson( nPozo , Pozo.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    Pozo pozo1 = Pozo.FINDER.byId(idPozo);
                    pozo1.setCampo(pozo.getCampo());
                    pozo1.setEstado(pozo.getEstado());
                    pozo1.save();
                    return pozo1;

                }
        ).thenApply(
                ppozo-> {
                    return ok(Json.toJson(ppozo));
                }
        );
    }

    public CompletionStage<Result> deletePozo(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()-> {
                    Pozo.FINDER.deleteById(idPozo);
                    return idPozo;
                }
        ).thenApply(
                pozo -> {
                    return ok(Json.toJson(pozo));
                }
        );
    }

    public CompletionStage<Result> clausurarPozo(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()->{
                    Pozo pozo1 = Pozo.FINDER.byId(idPozo);
                    pozo1.clausurarPozo();
                    pozo1.save();
                    return pozo1;
                }
        ).thenApply(
                ppozo-> {
                    return ok(Json.toJson(ppozo));
                }
        );
    }

    public CompletionStage<Result> detenerPozo(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()->{
                    Pozo pozo1 = Pozo.FINDER.byId(idPozo);
                    pozo1.detenerPozoEmergencia();
                    pozo1.save();
                    return pozo1;
                }
        ).thenApply(
                ppozo-> {
                    return ok(Json.toJson(ppozo));
                }
        );
    }


    public CompletionStage<Result> reabrirPozo(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()->{
                    Pozo pozo1 = Pozo.FINDER.byId(idPozo);
                    pozo1.reabrirPozo();
                    pozo1.save();
                    return pozo1;
                }
        ).thenApply(
                ppozo-> {
                    return ok(Json.toJson(ppozo));
                }
        );
    }

    public Date diaAntes(Date dia){
        long milis = dia.getTime();
        milis -= 1000*60*60*24;
        return new Date(milis);
    }

    public Date mesAntes(Date dia){
        long milis = dia.getTime();
        milis -= 1000*60*60*24*30;
        return new Date(milis);
    }

    public CompletionStage<Result> registroEnergiaDiario(Long idPozo, String dia){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.supplyAsync(
                ()-> {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date fecha=null;
                    Date fecha3 = null;
                    try {
                        fecha = df.parse(dia);
                        fecha3 = diaAntes(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return MensajeEnergia.FINDER.where().eq("pozo_id",idPozo).conjunction().between("fecha_envio",fecha3,fecha).findList();
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }

    public CompletionStage<Result> registroEnergiaMensual(Long idPozo, String dia){

        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()-> {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date fecha=null;
                    Date fecha3 = null;
                    try {
                        fecha = df.parse(dia);
                        fecha3 = mesAntes(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return MensajeEnergia.FINDER.where().eq("pozo_id",idPozo).conjunction().between("fecha_envio",fecha3,fecha).findList();
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }

    public CompletionStage<Result> registroEnergiaPorFechas(Long idPozo, String dia, String dia2){

        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()-> {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-YYYY");
                    Date fecha=null;
                    Date fecha2 = null;
                    try {
                        fecha = df.parse(dia);
                        fecha2 = df.parse(dia2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return MensajeEnergia.FINDER.where().eq("pozo_id",idPozo).conjunction().between("fecha_envio",fecha,fecha2);
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }


    public CompletionStage<Result> registroCaudalDiario(Long idPozo, String dia){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.supplyAsync(
                ()-> {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date fecha=null;
                    Date fecha3 = null;
                    try {
                        fecha = df.parse(dia);
                        fecha3 = diaAntes(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return MensajeCaudal.FINDER.where().eq("pozo_id",idPozo).conjunction().between("fecha_envio",fecha3,fecha).findList();
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }

    public CompletionStage<Result> registroCaudalMensual(Long idPozo, String dia){

        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()-> {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date fecha=null;
                    Date fecha3 = null;
                    try {
                        fecha = df.parse(dia);
                        fecha3 = mesAntes(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return MensajeCaudal.FINDER.where().eq("pozo_id",idPozo).conjunction().between("fecha_envio",fecha3,fecha).findList();
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }

    public CompletionStage<Result> registroTemperaturaDiario(Long idPozo, String dia){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.supplyAsync(
                ()-> {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date fecha=null;
                    Date fecha3 = null;
                    try {
                        fecha = df.parse(dia);
                        fecha3 = diaAntes(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return MensajeTemperatura.FINDER.where().eq("pozo_id",idPozo).conjunction().between("fecha_envio",fecha3,fecha).findList();
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }

    public CompletionStage<Result> registroTemperaturaMensual(Long idPozo, String dia){

        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()-> {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date fecha=null;
                    Date fecha3 = null;
                    try {
                        fecha = df.parse(dia);
                        fecha3 = mesAntes(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return MensajeTemperatura.FINDER.where().eq("pozo_id",idPozo).conjunction().between("fecha_envio",fecha3,fecha).findList();
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }

}
