package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.MensajeCaudal;
import models.MensajeEnergia;
import models.Pozo;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

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

    public CompletionStage<Result> updatePozo(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nPozo= request().body().asJson();
        Pozo pozo = Json.fromJson( nPozo , Pozo.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    Pozo pozo1 = Pozo.FINDER.byId(idPozo);
                    pozo1.setCampo(pozo.getCampo());
                    pozo1.setEstado(pozo.getEstado());
                    pozo1.setSensorCaudal(pozo.getSensorCaudal());
                    pozo1.setSensorEmergencia(pozo.getSensorEmergencia());
                    pozo1.setSensorEnergia(pozo.getSensorEnergia());
                    pozo1.setSensorTemperatura(pozo.getSensorTemperatura());
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

    public CompletionStage<Result> registroEnergiaDiario(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()-> {
                    List<MensajeEnergia> mensaje = MensajeEnergia.FINDER.where().eq("pozo_id",idPozo).findList();
                    return mensaje;
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }



}
