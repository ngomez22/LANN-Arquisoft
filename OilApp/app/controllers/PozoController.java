package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

    public CompletionStage<Result> registroCaudalDiario(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()-> {
                    List<MensajeCaudal> mensaje = MensajeCaudal.FINDER.where().eq("pozo_id",idPozo).findList();
                    return mensaje;
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }

    public CompletionStage<Result> registroEnergiaDiario(Long idPozo,String dia){

        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()-> {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-YYYY");
                    Date fecha=null;
                    try {
                        fecha = df.parse(dia);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    List<MensajeEnergia> mensaje = MensajeEnergia.FINDER.where().eq("pozo_id",idPozo).eq("fecha_envio",fecha).findList();
                    return mensaje;
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }

    public CompletionStage<Result> createMensajeEnergia(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nMensaje = request().body().asJson();
        MensajeEnergia mensajeEnergia = Json.fromJson( nMensaje , MensajeEnergia.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    Pozo pozo = Pozo.FINDER.byId(idPozo);
                    pozo.getSensorEnergia().add(mensajeEnergia);
                    mensajeEnergia.save();
                    return mensajeEnergia;
                }
        ).thenApply(
                mensaje -> {
                    return ok(Json.toJson(mensaje));
                }
        );
    }
    public CompletionStage<Result> createMensajeCaudal(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nMensaje = request().body().asJson();
        MensajeCaudal mensajeCaudal = Json.fromJson( nMensaje , MensajeCaudal.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    Pozo pozo = Pozo.FINDER.byId(idPozo);
                    pozo.getSensorCaudal().add(mensajeCaudal);
                    mensajeCaudal.save();
                    return mensajeCaudal;
                }
        ).thenApply(
                mensaje -> {
                    return ok(Json.toJson(mensaje));
                }
        );
    }
    public CompletionStage<Result> createMensajeEmergencia(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nMensaje = request().body().asJson();
        MensajeEmergencia mensajeEmergencia = Json.fromJson( nMensaje , MensajeEmergencia.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    Pozo pozo = Pozo.FINDER.byId(idPozo);
                    pozo.getSensorEmergencia().add(mensajeEmergencia);
                    mensajeEmergencia.save();
                    return mensajeEmergencia;
                }
        ).thenApply(
                mensaje -> {
                    return ok(Json.toJson(mensaje));
                }
        );
    }
    public CompletionStage<Result> createMensajeTemperatura(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nMensaje = request().body().asJson();
        MensajeTemperatura mensajeTemperatura = Json.fromJson( nMensaje , MensajeTemperatura.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    Pozo pozo = Pozo.FINDER.byId(idPozo);
                    pozo.getSensorTemperatura().add(mensajeTemperatura);
                    mensajeTemperatura.save();
                    return mensajeTemperatura;
                }
        ).thenApply(
                mensaje -> {
                    return ok(Json.toJson(mensaje));
                }
        );
    }

}
