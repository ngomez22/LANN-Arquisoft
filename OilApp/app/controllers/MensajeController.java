package controllers;

import akka.dispatch.MessageDispatcher;
import be.objectify.deadbolt.java.actions.DeferredDeadbolt;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectNotPresent;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.MensajeCaudal;
import models.MensajeEmergencia;
import models.MensajeEnergia;
import models.MensajeTemperatura;
import org.joda.time.DateTime;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.mensajes.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.libs.Json.toJson;

/**
 * Created by Nicolás on 30/08/16.
 */
@Restrict({@Group({"jefeProduccion"}), @Group({"jefeCampo"}), @Group({"sensor"})})
@DeferredDeadbolt
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

    @SubjectNotPresent
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

    @SubjectNotPresent
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

    @SubjectNotPresent
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

    @SubjectNotPresent
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


    public Result services(Long idPozo) {
        return ok(services.render(idPozo));
    }

    public Result emergenciasPozo(Long idPozo) {
        return ok(emergencia.render(MensajeEmergencia.FINDER.where().eq("pozo_id", idPozo).order().desc("fecha_Envio").findList(), idPozo));
    }

    public Result emergenciasPozoLista(Long idPozo, List<MensajeEmergencia> emergencias) {
        return ok(emergencia.render(emergencias, idPozo));
    }

    public Result emergenciasPozoDia(Long idPozo) {
        DateTime dt = new DateTime();
        Date fecha2 = dt.minusDays(1).toDate();
        System.out.println("1: " + dt.toString() + " - 2: " + fecha2.toString());
        return ok(temperatura.render(MensajeTemperatura.FINDER.where().between("fecha_Envio", fecha2, dt.toDate()).eq("pozo_id", idPozo).order().desc("fecha_Envio").findList(), idPozo));
    }

    public Result emergenciasPozoMes(Long idPozo) {
        DateTime dt = new DateTime();
        Date fecha2 = dt.minusMonths(1).toDate();
        System.out.println("1: " + dt.toString() + " - 2: " + fecha2.toString());
        return ok(emergencia.render(MensajeEmergencia.FINDER.where().between("fecha_Envio", fecha2, dt.toDate()).eq("pozo_id", idPozo).order().desc("fecha_Envio").findList(), idPozo));
    }

    public Result energiaPozo(Long idPozo) {
        return ok(energia.render(MensajeEnergia.FINDER.where().eq("pozo_id", idPozo).order().desc("fecha_Envio").findList(), idPozo));
    }

    public Result energiaPozoLista(Long idPozo, List<MensajeEnergia> energias) {
        return ok(energia.render(energias, idPozo));
    }

    public Result energiaPozoDia(Long idPozo) {
        DateTime dt = new DateTime();
        Date fecha2 = dt.minusDays(1).toDate();
        System.out.println("1: " + dt.toString() + " - 2: " + fecha2.toString());
        return ok(energia.render(MensajeEnergia.FINDER.where().between("fecha_Envio", fecha2, dt.toDate()).eq("pozo_id", idPozo).order().desc("fecha_Envio").findList(), idPozo));
    }

    public Result energiaPozoMes(Long idPozo) {
        DateTime dt = new DateTime();
        Date fecha2 = dt.minusMonths(1).toDate();
        System.out.println("1: " + dt.toString() + " - 2: " + fecha2.toString());
        return ok(energia.render(MensajeEnergia.FINDER.where().between("fecha_Envio", fecha2, dt.toDate()).eq("pozo_id", idPozo).order().desc("fecha_Envio").findList(), idPozo));
    }

    public Result caudalPozo(Long idPozo) {
        return ok(caudal.render(MensajeCaudal.FINDER.where().eq("pozo_id", idPozo).order().desc("fecha_Envio").findList(), idPozo));
    }

    public Result caudalPozoLista(Long idPozo, List<MensajeCaudal> caudales) {
        return ok(caudal.render(caudales, idPozo));
    }

    public Result caudalPozoDia(Long idPozo) {
        DateTime dt = new DateTime();
        Date fecha2 = dt.minusDays(1).toDate();
        return ok(caudal.render(MensajeCaudal.FINDER.where().between("fecha_Envio", fecha2, dt.toDate()).eq("pozo_id", idPozo).order().desc("fecha_Envio").findList(), idPozo));
    }

    public Result caudalPozoMes(Long idPozo) {
        DateTime dt = new DateTime();
        Date fecha2 = dt.minusMonths(1).toDate();
        return ok(caudal.render(MensajeCaudal.FINDER.where().between("fecha_Envio", fecha2, dt.toDate()).eq("pozo_id", idPozo).order().desc("fecha_Envio").findList(), idPozo));
    }

    public Result temperaturaPozo(Long idPozo) {
        return ok(temperatura.render(MensajeTemperatura.FINDER.where().eq("pozo_id", idPozo).order().desc("fecha_Envio").findList(), idPozo));
    }

    public Result temperaturaPozoLista(Long idPozo, List<MensajeTemperatura> temperaturas) {
        return ok(temperatura.render(temperaturas, idPozo));
    }

    public Result temperaturaPozoDia(Long idPozo) {
        DateTime dt = new DateTime();
        Date fecha2 = dt.minusDays(1).toDate();
        return ok(temperatura.render(MensajeTemperatura.FINDER.where().between("fecha_Envio", fecha2, dt.toDate()).eq("pozo_id", idPozo).order().desc("fecha_Envio").findList(), idPozo));
    }

    public Result temperaturaPozoMes(Long idPozo) {
        DateTime dt = new DateTime();
        Date fecha2 = dt.minusMonths(1).toDate();
        return ok(temperatura.render(MensajeTemperatura.FINDER.where().between("fecha_Envio", fecha2, dt.toDate()).eq("pozo_id", idPozo).order().desc("fecha_Envio").findList(), idPozo));
    }

    public Result reporteDiario(Long idPozo) {
        DateTime dt = new DateTime();
        Date fecha2 = dt.minusDays(1).toDate();
        List<MensajeCaudal> caudal = MensajeCaudal.FINDER.where().between("fecha_Envio", fecha2, dt.toDate()).eq("pozo_id", idPozo).order().desc("fecha_Envio").findList();
        List<MensajeEnergia> energia = MensajeEnergia.FINDER.where().between("fecha_Envio", fecha2, dt.toDate()).eq("pozo_id", idPozo).order().desc("fecha_Envio").findList();
        List<MensajeTemperatura> temperatura = MensajeTemperatura.FINDER.where().between("fecha_Envio", fecha2, dt.toDate()).eq("pozo_id", idPozo).order().desc("fecha_Envio").findList();
        List<MensajeEmergencia> emergencias = MensajeEmergencia.FINDER.where().between("fecha_Envio", fecha2, dt.toDate()).eq("pozo_id", idPozo).order().desc("fecha_Envio").findList();
        return ok(report.render("dia", idPozo, caudal, energia, temperatura, emergencias));
    }

    public Result reporteMensual(Long idPozo) {
        DateTime dt = new DateTime();
        Date fecha2 = dt.minusMonths(1).toDate();
        List<MensajeCaudal> caudal = MensajeCaudal.FINDER.where().between("fecha_Envio", fecha2, dt.toDate()).eq("pozo_id", idPozo).order().desc("fecha_Envio").findList();
        List<MensajeEnergia> energia = MensajeEnergia.FINDER.where().between("fecha_Envio", fecha2, dt.toDate()).eq("pozo_id", idPozo).order().desc("fecha_Envio").findList();
        List<MensajeTemperatura> temperatura = MensajeTemperatura.FINDER.where().between("fecha_Envio", fecha2, dt.toDate()).eq("pozo_id", idPozo).order().desc("fecha_Envio").findList();
        List<MensajeEmergencia> emergencias = MensajeEmergencia.FINDER.where().between("fecha_Envio", fecha2, dt.toDate()).eq("pozo_id", idPozo).order().desc("fecha_Envio").findList();
        return ok(report.render("mes", idPozo, caudal, energia, temperatura, emergencias));
    }
}
