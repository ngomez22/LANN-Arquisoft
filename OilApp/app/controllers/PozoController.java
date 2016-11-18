package controllers;

import akka.dispatch.MessageDispatcher;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.*;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.pozos.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.data.Form.form;
import static play.libs.Json.toJson;




/**
 * Created by Nicolas Vasquez on 30/08/2016.
 */
@Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"}), @Group({"empleado"})})
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

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
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

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public CompletionStage<Result> updatePozo(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode nPozo= request().body().asJson();
        Pozo pozo = Json.fromJson( nPozo , Pozo.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    Pozo pozo1 = Pozo.FINDER.byId(idPozo);
                    pozo1.setCampo(pozo.getCampo());
                    pozo1.save();
                    return pozo1;

                }
        ).thenApply(
                ppozo-> {
                    return ok(Json.toJson(ppozo));
                }
        );
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
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

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public CompletionStage<Result> clausurarPozo(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()->{
                    Pozo pozo1 = Pozo.FINDER.byId(idPozo);
                    try {
                        pozo1.clausurarPozo();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    pozo1.save();
                    return pozo1;
                }
        ).thenApply(
                ppozo-> {
                    return ok(Json.toJson(ppozo));
                }
        );
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public CompletionStage<Result> detenerPozo(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()->{
                    Pozo pozo1 = Pozo.FINDER.byId(idPozo);
                    try {
                        pozo1.detenerPozoEmergencia();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    pozo1.save();
                    return pozo1;
                }
        ).thenApply(
                ppozo-> {
                    return ok(Json.toJson(ppozo));
                }
        );
    }


    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public CompletionStage<Result> reabrirPozo(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()->{
                    Pozo pozo1 = Pozo.FINDER.byId(idPozo);
                    try {
                        pozo1.reabrirPozo();
                    } catch(Exception e){
                        e.printStackTrace();
                    }                    pozo1.save();
                    return pozo1;
                }
        ).thenApply(
                ppozo-> {
                    return ok(Json.toJson(ppozo));
                }
        );
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public CompletionStage<Result> iniciarProducirPozo(Long idPozo){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()->{
                    Pozo pozo1 = Pozo.FINDER.byId(idPozo);
                    try {
                        pozo1.iniciarProduccionPozo();
                    } catch(Exception e){
                        e.printStackTrace();
                    }                    pozo1.save();
                    return pozo1;
                }
        ).thenApply(
                ppozo-> {
                    return ok(Json.toJson(ppozo));
                }
        );
    }

    public Date diaAntes(Date dia){
        long milis = dia.getTime();
        milis -= 1000*60*60*24;
        return new Date(milis);
    }

    public Date mesAntes(Date dia){
        long milis = dia.getTime();
        milis -= 1000*60*60*24*30;
        return new Date(milis);
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public CompletionStage<Result> registroEnergiaDiario(Long idPozo, String dia){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.supplyAsync(
                ()-> {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date fecha=null;
                    Date fecha3 = null;
                    try {
                        fecha = df.parse(dia);
                        fecha3 = diaAntes(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return MensajeEnergia.FINDER.where().eq("pozo_id",idPozo).conjunction().between("fecha_envio",fecha3,fecha).findList();
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public CompletionStage<Result> registroEnergiaMensual(Long idPozo, String dia){

        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()-> {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date fecha=null;
                    Date fecha3 = null;
                    try {
                        fecha = df.parse(dia);
                        fecha3 = mesAntes(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return MensajeEnergia.FINDER.where().eq("pozo_id",idPozo).conjunction().between("fecha_envio",fecha3,fecha).findList();
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public CompletionStage<Result> registroEnergiaPorFechas(Long idPozo, String dia, String dia2){

        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()-> {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-YYYY");
                    Date fecha=null;
                    Date fecha2 = null;
                    try {
                        fecha = df.parse(dia);
                        fecha2 = df.parse(dia2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return MensajeEnergia.FINDER.where().eq("pozo_id",idPozo).conjunction().between("fecha_envio",fecha,fecha2);
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public CompletionStage<Result> registroCaudalDiario(Long idPozo, String dia){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.supplyAsync(
                ()-> {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date fecha=null;
                    Date fecha3 = null;
                    try {
                        fecha = df.parse(dia);
                        fecha3 = diaAntes(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return MensajeCaudal.FINDER.where().eq("pozo_id",idPozo).conjunction().between("fecha_envio",fecha3,fecha).findList();
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public CompletionStage<Result> registroCaudalMensual(Long idPozo, String dia){

        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()-> {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date fecha=null;
                    Date fecha3 = null;
                    try {
                        fecha = df.parse(dia);
                        fecha3 = mesAntes(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return MensajeCaudal.FINDER.where().eq("pozo_id",idPozo).conjunction().between("fecha_envio",fecha3,fecha).findList();
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public CompletionStage<Result> registroTemperaturaDiario(Long idPozo, String dia){
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.supplyAsync(
                ()-> {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date fecha=null;
                    Date fecha3 = null;
                    try {
                        fecha = df.parse(dia);
                        fecha3 = diaAntes(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return MensajeTemperatura.FINDER.where().eq("pozo_id",idPozo).conjunction().between("fecha_envio",fecha3,fecha).findList();
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public CompletionStage<Result> registroTemperaturaMensual(Long idPozo, String dia){

        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()-> {
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date fecha=null;
                    Date fecha3 = null;
                    try {
                        fecha = df.parse(dia);
                        fecha3 = mesAntes(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return MensajeTemperatura.FINDER.where().eq("pozo_id",idPozo).conjunction().between("fecha_envio",fecha3,fecha).findList();
                }
        ).thenApply(
                mensajes->{
                    return ok(Json.toJson(mensajes));
                }
        );
    }

    public Result getPozosCampo(Long id) {
        return ok(pozos.render(Pozo.FINDER.where().eq("campo_id", id).findList(), id));
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public Result createPozo(Long idCampo) {
        Pozo poz = new Pozo();
        Form<Pozo> pozoForm = form(Pozo.class).fill(poz);
        return ok(views.html.pozos.createPozo.render(pozoForm, idCampo));
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public Result save(Long idCampo) {
        Form<Pozo> pozoForm = form(Pozo.class).bindFromRequest();
        if(pozoForm.hasErrors()){
            return badRequest(views.html.pozos.createPozo.render(pozoForm,0L));
        } else {
            Pozo pozo= pozoForm.get();
            pozo.setCampo(Campo.FINDER.byId(idCampo));
            pozo.save();
            return redirect(routes.PozoController.getPozosCampo(idCampo));
        }
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public Result delete(Long idCampo, Long idPozo) {
        Pozo.FINDER.byId(idPozo).delete();
        return redirect(routes.PozoController.getPozosCampo(idCampo));
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public Result reabrir( Long idCampo,Long idPozo){
        Pozo pozo = Pozo.FINDER.byId(idPozo);
        try {
            pozo.reabrirPozo();
        } catch(Exception e){
            e.printStackTrace();
        }        pozo.save();
        return redirect(routes.PozoController.getPozosCampo(idCampo));
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public Result clausurar( Long idCampo,Long idPozo){
        Pozo pozo = Pozo.FINDER.byId(idPozo);
        try {
            pozo.clausurarPozo();
        } catch(Exception e){
            e.printStackTrace();
        }        pozo.save();
        return redirect(routes.PozoController.getPozosCampo(idCampo));
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public Result detener( Long idCampo,Long idPozo){
        Pozo pozo = Pozo.FINDER.byId(idPozo);
        try {
            pozo.detenerPozoEmergencia();
        } catch(Exception e){
            e.printStackTrace();
        }        pozo.save();
        return redirect(routes.PozoController.getPozosCampo(idCampo));
    }

    @Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"})})
    public Result producir( Long idCampo,Long idPozo){
        Pozo pozo = Pozo.FINDER.byId(idPozo);
        try {
            pozo.iniciarProduccionPozo();
        } catch(Exception e){
            e.printStackTrace();
        }        pozo.save();
        return redirect(routes.PozoController.getPozosCampo(idCampo));
    }
}
