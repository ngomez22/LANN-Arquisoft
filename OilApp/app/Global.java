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
            for (String name : Arrays.asList("jefeProduccion", "jefeCampo", "empleado", "otro"))
            {
                Rol rol = new Rol();
                rol.name = name;
                rol.save();
            }
        }

        if (Usuario.FINDER.findRowCount() == 0)
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
    }
}
