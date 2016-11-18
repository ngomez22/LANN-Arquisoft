/*
 * Copyright 2012 Steve Chaloner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package security;

import actions.CustomRestrictAction;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.models.Role;
import play.mvc.With;

import java.lang.annotation.*;

public enum MyRoles implements Role {
    jefeProduccion,
    jefeCampo,
    empleado,
    otro;

    @Override
    public String getName() {
        return name();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface MyRolesGroup {
        MyRoles value();

    }

    @With(CustomRestrictAction.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Documented
    @Inherited
    public @interface CustomRestrict {
        MyRolesGroup[] value();

        Restrict config();
    }
}
