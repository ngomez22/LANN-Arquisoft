import com.avaje.ebean.Ebean;
import models.Rol;
import models.Usuario;
import play.Application;
import play.GlobalSettings;

import java.util.Arrays;

/**
 * OilApp
 * Created by Nicolás on 17/11/16.
 */
public class Global extends GlobalSettings
{
    @Override
    public void onStart(Application application)
    {
        if (Rol.FINDER.findRowCount() == 0)
        {
            for (String name : Arrays.asList("jefeProduccion", "jefeCampo", "empleado", "otro", "sensor"))
            {
                Rol rol = new Rol();
                rol.name = name;
                rol.save();
            }
        }

        if (Usuario.FINDER.where().eq("username", "ngomez").findUnique() == null)
        {
            Usuario user = new Usuario();
            user.setNombre("Nicolás");
            user.setCargo(Usuario.JEFE_PRODUCCION);
            user.setEdad(20);
            user.setUsername("ngomez");
            user.setPassword("12345");
            user.roles.add(Rol.rolPorNombre("jefeProduccion"));
            user.save();

            Ebean.saveManyToManyAssociations(user,
                    "roles");
        }
        if(Usuario.FINDER.where().eq("username", "sensor").findUnique() == null){
            Usuario user = new Usuario();
            user.setNombre("sensor");
            user.setCargo("sensor");
            user.setEdad(0);
            user.setUsername("sensor");
            user.setPassword("clavesecreta");
            user.roles.add(Rol.rolPorNombre("sensor"));

            user.save();

            Ebean.saveManyToManyAssociations(user,
                    "roles");
        }
    }
}
