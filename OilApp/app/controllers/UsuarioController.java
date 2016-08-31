package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.Usuario;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.libs.Json.toJson;

/**
 * Nicolás Gómez
 */
public class UsuarioController extends Controller {

    public CompletionStage<Result> getUsuarios() {
        System.out.println("HOLAHOLA ENTRO AL METODO!");
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        System.out.println("HOLAHOLA CREÓ EL DISPATCHER!");
        return CompletableFuture.
                supplyAsync(
                        () -> {
                            return Usuario.FINDER.all();
                        }
                        ,jdbcDispatcher)
                .thenApply(
                        usuarios -> {
                            return ok(toJson(usuarios));
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

}
