package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import models.Campo;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.libs.Json.toJson;

/**
 * Created by a.sandoval1303 on 30/08/2016.
 */
public class CampoController extends Controller{

     public CompletionStage<Result> getCampos() {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(
                        () -> {
                            return Campo.FINDER.all();
                        }
                        ,jdbcDispatcher)
                .thenApply(
                        campos -> {
                            return ok(toJson(campos));
                        }
                );
    }

    public CompletionStage<Result> createCamp(){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nCampo = request().body().asJson();
        Campo campo = Json.fromJson( nCampo , Campo.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    campo.save();
                    return campo;
                }
        ).thenApply(
                campo1 -> {
                    return ok(Json.toJson(campo1));
                }
        );
    }

    public CompletionStage<Result> updateCampo(Long idCampo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nCampo= request().body().asJson();
        Campo campo = Json.fromJson( nCampo , Campo.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    Campo campo1 = Campo.FINDER.byId(idCampo);
                    campo1.setCantidadBarriles(campo.getCaudal());
                    campo1.setConsumoEnergia(campo.getConsumoEnergia());
                    campo1.setJefeDeCampo(campo.getJefeDeCampo());
                    campo1.setRegion(campo.getRegion());
                    campo1.save();
                    return campo1;

                }
        ).thenApply(
                campoN-> {
                    return ok(Json.toJson(campoN));
                }
        );
    }

    public CompletionStage<Result> deleteCampo(Long idCampo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()-> {
                    Campo.FINDER.deleteById(idCampo);
                    return idCampo;
                }
        ).thenApply(
                campo -> {
                    return ok(Json.toJson(campo));
                }
        );
    }
}
