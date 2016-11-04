package filters;

import akka.stream.Materializer;
import play.mvc.Filter;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

/**
 * Created by Nicolás on 03/11/16.
 */
public class AuthenticationFilter extends Filter {

    public AuthenticationFilter(Materializer mat) {
        super(mat);
    }

    @Override
    public CompletionStage<Result> apply(Function<RequestHeader, CompletionStage<Result>> next, RequestHeader rh) {
        return null;
    }
}
