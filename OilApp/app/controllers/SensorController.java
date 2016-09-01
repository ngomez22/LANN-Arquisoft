package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.Sensor;
import models.Usuario;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.libs.Json.toJson;


public class SensorController extends Controller {

    public CompletionStage<Result> getUsuarios() {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.
                supplyAsync(
                        () -> {
                            return Sensor.FINDER.all();
                        }
                        ,jdbcDispatcher)
                .thenApply(
                        sensorEntities -> {
                            return ok(toJson(sensorEntities));
                        }
                );
    }

    public CompletionStage<Result> createUsuario(){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nUsuario = request().body().asJson();
        Usuario usuario = Json.fromJson( nUsuario , Usuario.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    usuario.save();
                    return usuario;
                }
        ).thenApply(
                usuario1 -> {
                    return ok(Json.toJson(usuario1));
                }
        );
    }

    public CompletionStage<Result> getUsuario(Long id) {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.supplyAsync(
                ()->{
                    return Usuario.FINDER.byId(id);
                }
                ,jdbcDispatcher
        ).thenApply(
                usuarioEntity -> {
                    return ok(Json.toJson(usuarioEntity));
                }
        );
    }

    public CompletionStage<Result> updateUsuario(Long id) {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nUsuario = request().body().asJson();
        Usuario usuario = Json.fromJson( nUsuario , Usuario.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    usuario.update();
                    return usuario;
                }
        ).thenApply(
                usuario1 -> {
                    return ok(Json.toJson(usuario1));
                }
        );
    }

    public CompletionStage<Result> deleteUsuario(Long id) {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.
                supplyAsync(
                        () -> {
                            Usuario.FINDER.ref(id).delete();
                            return Usuario.FINDER.byId(id);
                        }
                        , jdbcDispatcher)
                .thenApply(
                        usuarioEntity -> {
                            if (usuarioEntity == null) return ok();
                            else return badRequest();
                        }
                );
    }

}
