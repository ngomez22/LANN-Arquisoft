package controllers;
import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.Zona;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.libs.Json.toJson;


public class ZonaController extends Controller {

    public CompletionStage<Result> getZonas() {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.
                supplyAsync(
                        () -> {
                            return Zona.FINDER.all();
                        }
                        ,jdbcDispatcher)
                .thenApply(
                        zonaEntities -> {
                            return ok(toJson(zonaEntities));
                        }
                );
    }

    public CompletionStage<Result> createZona(){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nZona = request().body().asJson();
        Zona zona = Json.fromJson( nZona , Zona.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    zona.save();
                    return zona;
                }
        ).thenApply(
                zona1 -> {
                    return ok(Json.toJson(zona1));
                }
        );
    }

    public CompletionStage<Result> getZona(Long id) {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.supplyAsync(
                ()->{
                    return  Zona.FINDER.byId(id);
                }
                ,jdbcDispatcher
        ).thenApply(
                zonaEntity -> {
                    return ok(Json.toJson(zonaEntity));
                }
        );
    }

    public CompletionStage<Result> updateZona(Long id) {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nZona = request().body().asJson();
        Zona zona = Json.fromJson( nZona , Zona.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    zona.update();
                    return zona;
                }
        ).thenApply(
                zona1 -> {
                    return ok(Json.toJson(zona1));
                }
        );
    }

    public CompletionStage<Result> deleteZona(Long id) {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.
                supplyAsync(
                        () -> {
                            Zona.FINDER.ref(id).delete();
                            return Zona.FINDER.byId(id);
                        }
                        , jdbcDispatcher)
                .thenApply(
                        zonaEntity -> {
                            if (zonaEntity == null) return ok();
                            else return badRequest();
                        }
                );
    }

}
