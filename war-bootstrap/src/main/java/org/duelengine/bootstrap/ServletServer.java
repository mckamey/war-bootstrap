package org.duelengine.bootstrap;

import java.util.Map;

public interface ServletServer {

	String getName();

	void start(Map<String, String> contexts, int httpPort, int httpsPort) throws Exception;

	void stop() throws Exception;
}
