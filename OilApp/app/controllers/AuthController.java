package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Usuario;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.signin;

import static play.data.Form.form;

/**
 * OilApp
 * Created by Nicolás on 17/11/16.
 */
public class AuthController extends Controller {

    public Result login() {
        Form<AuthController.Login> loginForm = form(AuthController.Login.class);
        return ok(signin.render(loginForm));
    }

    public Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(signin.render(loginForm));
        } else {
            session().clear();
            session("user", loginForm.get().username);
            return redirect(
                    routes.HomeController.index()
            );
        }
    }

    public Result logout() {
        session().clear();
        return redirect(
                routes.HomeController.index()
        );
    }

    public Result externalAuth() {
        JsonNode login = request().body().asJson();
        Login l = Json.fromJson(login, Login.class);
        if(l.validate()== null){
            session().clear();
            session("user", l.username);
        }
        return ok();
    }

    public static class Login {
        public String username;
        public String password;

        public String validate() {
            if(Usuario.authenticate(username, password) == null) {
                return "Usuario o constraseña inválidos";
            }
            return null;
        }
    }
}
