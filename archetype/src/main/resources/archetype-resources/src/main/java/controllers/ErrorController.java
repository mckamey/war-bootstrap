#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controllers;

import javax.ws.rs.*;
import com.google.inject.*;
import com.google.inject.name.Named;

import ${package}.model.*;
import ${package}.views.*;

/**
 * Error message controller
 */
@Singleton
public class ErrorController extends BaseController {

	private final boolean printStackTrace;

	@Inject
	public ErrorController(@Named("DEBUG") boolean isDebug) {
		this.printStackTrace = isDebug;
	}

	@GET
	@Path("{path:.*}")
	@Produces({"text/html", "application/xhtml+xml"})
	public Object errorView(Throwable ex) {
		ErrorResult error = this.errorData(ex);

		// TODO: customize error responses
		return view(ErrorPage.class).data(error);
	}

	@GET
	@Path("{path:.*}")
	@Produces({"application/json", "application/xml"})
	public ErrorResult errorData(Throwable ex) {
		return new ErrorResult(ex, this.printStackTrace);
	}
}
