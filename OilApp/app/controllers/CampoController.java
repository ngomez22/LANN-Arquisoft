package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.Campo;
import models.Pozo;
import models.Usuario;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.data.Form.form;
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

    public CompletionStage<Result> createCampo(){
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

    public CompletionStage<Result> registroCaudalDiario(Long idCampo)
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()-> {
                    List<Pozo> mensaje = Pozo.FINDER.where().eq("campo_id",idCampo).findList();
                    return mensaje;
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }

    public CompletionStage<Result> registroEnergiaDiario(Long idCampo, String dia) {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                () -> {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-YYYY");
                    Date fecha=null;
                    try {
                        fecha = df.parse(dia);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    List<Pozo> mensaje = Pozo.FINDER.where().eq("campo_id", idCampo).eq("fecha_envio", fecha).findList();
                    return mensaje;
                }

        ).thenApply(
                mensaje -> {
                    return ok(Json.toJson(mensaje));
                }
        );
    }

    public CompletionStage<Result> createMensajeEnergia(Long idCampo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nMensaje = request().body().asJson();
        Pozo pozo = Json.fromJson( nMensaje , Pozo.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    Campo campo = Campo.FINDER.byId(idCampo);
                    campo.getPozos().add(pozo);
                    pozo.setCampo(campo);
                    pozo.save();
                    return pozo;
                }
        ).thenApply(
                mensaje -> {
                    return ok(Json.toJson(mensaje));
                }
        );
    }

    public Result getCamposRegion(String r) {
        List<Campo> list = Campo.FINDER.where().eq("region", r).findList();
        return ok(campos.render(list, r));
    }

    public Result createCampoRegion(String r) {
        String localidad = "popo";
        switch (r){
            case "Caribe":
                localidad = "Parque Tayrona";
                break;
            case "Andina":
                localidad = "Popayan";
                break;
            case "Pacifico":
                localidad = "Serranía del Baudó";
                break;
            case "Orinoquia":
                localidad = "Serranía de la Macarena";
                break;
            case "Amazonas":
                localidad = "Llanuras del Guaviare";
                break;
        }
        Campo def = new Campo();
        def.setRegion(r);
        def.setLocalidad(localidad);

        Form<Campo> campoForm = form(Campo.class).fill(def);
        return ok(createCampo.render(r, campoForm));
    }

    public Result save(String r) {
        Form<Campo> campoForm = form(Campo.class).bindFromRequest();
        if(campoForm.hasErrors()){
            return badRequest(createCampo.render(r, campoForm));
        } else {
            List<Pozo> pozos = new ArrayList<Pozo>();
            Campo campo = campoForm.get();
            campo.setRegion(r);
            campo.setPozos(pozos);
            campo.save();
            return(redirect(routes.CampoController.getCamposRegion(r)));
        }
    }

    public Result delete(String r, Long id) {
        System.out.println("Region: " + r + " - ID campo: " + id);
        Campo.FINDER.byId(id).delete();
        return redirect(routes.CampoController.getCamposRegion(r));
    }


    public Result asignarJefe( String r,Long idCampo ,Long usuarioId)
    {
        Campo campo =Campo.FINDER.byId(idCampo);
        Usuario usuario = Usuario.FINDER.byId(usuarioId);
        campo.setJefeDeCampo(usuario);
        campo.save();
        return redirect(routes.CampoController.getCamposRegion(r));
    }

}
