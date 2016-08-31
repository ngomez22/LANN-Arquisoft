package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import models.Pozo;
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

    public CompletionStage<Result> updatePozo(){
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

    public CompletionStage<Result> deletePozo(){
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
}
