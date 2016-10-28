package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import emums.Region;
import models.Campo;
import models.Pozo;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.campos;
import views.html.createCampo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        return ok(campos.render(Campo.FINDER.where().eq("region", r).findList(), r));
    }

    public Result createCampoRegion(String r) {
        Region region = null;
        String localidad = "";
        switch (r){
            case "Caribre":
                region = Region.CARIBE;
                localidad = "Parque Tayrona";
                break;
            case "Andina":
                region = Region.ANDINA;
                localidad = "Popayan";
                break;
            case "Pacifico":
                region = Region.PACIFICO;
                localidad = "Serranía del Baudó";
                break;
            case "Orinoquia":
                region = Region.ORINOQUIA;
                localidad = "";
                break;
            case "Amazonas":
                region = Region.AMAZONAS;
                break;
        }
        Campo def = new Campo();
        def.setRegion(region);
        def.setLocalidad("Juanchaco");

        Form<Campo> campoForm = form(Campo.class).fill(def);
        return ok(createCampo.render(r, campoForm));
    }

    public Result save() {
        Form<Campo> campoForm = form(Campo.class).bindFromRequest();
        if(campoForm.hasErrors()){
            return badRequest(createCampo.render(campoForm.get().getRegion().toString(), campoForm));
        } else {
            Campo campo = campoForm.get();
            campo.save();
            return(redirect(routes.CampoController.getCamposRegion(campo.getRegion().toString())));
        }
    }


}
