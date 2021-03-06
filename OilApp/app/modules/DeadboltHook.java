package modules;

import be.objectify.deadbolt.java.cache.HandlerCache;
import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;
import security.MyHandlerCache;

import javax.inject.Singleton;

/**
 * OilApp
 * Created by Nicolás on 07/11/16.
 */
public class DeadboltHook extends Module {

    @Override
    public Seq<Binding<?>> bindings(Environment environment, Configuration configuration) {
        //return seq(bind(TemplateFailureListener.class).to(MyCustomTemplateFailureListener.class).in(Singleton.class),
        //        bind(HandlerCache.class).to(MyHandlerCache.class).in(Singleton.class));
        return seq(bind(HandlerCache.class).to(MyHandlerCache.class).in(Singleton.class));
    }

}
