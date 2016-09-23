package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.libs.Json.toJson;

/**
 * Created by Nicolás on 30/08/16.
 */
public class MensajeController extends Controller {

    // ----- Servicios para los mensajes de caudal

    public CompletionStage<Result> getMensajesCaudal() {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.
                supplyAsync(
                        () -> {
                            return MensajeCaudal.FINDER.all();
                        }
                        ,jdbcDispatcher)
                .thenApply(
                        mensajesCaudal -> {
                            return ok(toJson(mensajesCaudal));
                        }
                );
    }

    public CompletionStage<Result> createMensajeCaudal(){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nMensaje = request().body().asJson();
        MensajeCaudal mensajeCaudal = Json.fromJson( nMensaje , MensajeCaudal.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    mensajeCaudal.save();
                    return mensajeCaudal;
                }
        ).thenApply(
                mensaje -> {
                    return ok(Json.toJson(mensaje));
                }
        );
    }

    // ----- Servicios para los mensajes de emergencia

    public CompletionStage<Result> getMensajesEmergencia() {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.
                supplyAsync(
                        () -> {
                            return MensajeEmergencia.FINDER.all();
                        }
                        ,jdbcDispatcher)
                .thenApply(
                        mensajesEmergencia -> {
                            return ok(toJson(mensajesEmergencia));
                        }
                );
    }

    public CompletionStage<Result> createMensajeEmergencia(){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nMensaje = request().body().asJson();
        MensajeEmergencia mensajeEmergencia = Json.fromJson( nMensaje , MensajeEmergencia.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    mensajeEmergencia.save();
                    return mensajeEmergencia;
                }
        ).thenApply(
                mensaje -> {
                    return ok(Json.toJson(mensaje));
                }
        );
    }

    // ----- Servicios para los mensajes de energía

    public CompletionStage<Result> getMensajesEnergia() {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.
                supplyAsync(
                        () -> {
                            return MensajeEnergia.FINDER.all();
                        }
                        ,jdbcDispatcher)
                .thenApply(
                        mensajesEnergia -> {
                            return ok(toJson(mensajesEnergia));
                        }
                );
    }

    public CompletionStage<Result> createMensajeEnergia(){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nMensaje = request().body().asJson();
        MensajeEnergia mensajeEnergia = Json.fromJson( nMensaje , MensajeEnergia.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    mensajeEnergia.save();
                    return mensajeEnergia;
                }
        ).thenApply(
                mensaje -> {
                    return ok(Json.toJson(mensaje));
                }
        );
    }

    // ----- Servicios para los mensajes de temperattura

    public CompletionStage<Result> getMensajesTemperatura() {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.
                supplyAsync(
                        () -> {
                            return MensajeTemperatura.FINDER.all();
                        }
                        ,jdbcDispatcher)
                .thenApply(
                        mensajesTemperatura -> {
                            return ok(toJson(mensajesTemperatura));
                        }
                );
    }

    public CompletionStage<Result> createMensajeTemperatura(){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nMensaje = request().body().asJson();
        MensajeTemperatura mensajeTemperatura = Json.fromJson( nMensaje , MensajeTemperatura.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
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
