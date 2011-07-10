#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controllers;

import javax.ws.rs.*;
import com.google.inject.*;

/**
 * Health monitoring controller
 */
@Singleton
@Path("/ping")
public class PingController extends BaseController {

	@GET
	@Produces("text/plain")
	public Object ping() {

		// TODO: perform service health test
		return "OK";
	}
}