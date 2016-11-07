package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.Usuario;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.users.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.data.Form.form;
import static play.libs.Json.toJson;

/**
 * Nicolás Gómez
 */
public class UsuarioController extends Controller {

    public CompletionStage<Result> getUsuarios() {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.
                supplyAsync(
                        () -> {
                            return Usuario.FINDER.all();
                        }
                        , jdbcDispatcher)
                .thenApply(
                        usuarioEntities -> {
                            return ok(toJson(usuarioEntities));
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

    //FRONT END METHODS

    public Result fetch() {
        return ok(about.render(Usuario.FINDER.all()));
    }

    public Result create(){
        Usuario def = new Usuario();
        def.setUsername("juanito10");
        def.setPassword("password");
        def.setNombre("Juan");
        def.setAvatar(Usuario.DEFAULT_AVATAR);
        def.setEdad(25);
        def.setCargo("Empleado");
        Form<Usuario> usuarioForm = form(Usuario.class).fill(def);
        return ok(createUser.render(usuarioForm));
    }

    public Result save() {
        Form<Usuario> usuarioForm = form(Usuario.class).bindFromRequest();
        if(usuarioForm.hasErrors()) {
            flash("error", "Hay errores en los campos.");
            return badRequest(createUser.render(usuarioForm));
        }
        Usuario usuario = usuarioForm.get();
        usuario.save();
        flash("success", "Se agregó con éxito el usuario " + usuario.getNombre() + " con el cargo " + usuario.getCargo());
        return redirect(routes.UsuarioController.fetch());
    }

    public Result delete(Long id) {
        Usuario.FINDER.byId(id).delete();
        return redirect(routes.UsuarioController.fetch());
    }

    public List jefeIds (){
        return Usuario.FINDER.where().eq("cargo", Usuario.JEFE_CAMPO).findIds();
    }
}
