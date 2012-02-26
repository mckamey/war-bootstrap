#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.util.Map;

public interface ServletServer {

	String getName();

	void start(Map<String, String> contexts, int httpPort, int httpsPort) throws Exception;

	void stop() throws Exception;
}
