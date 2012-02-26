package org.duelengine.bootstrap;

import java.io.File;
import java.util.Map;

import org.glassfish.embeddable.*;

class GlassFishServletServer implements ServletServer {
	private GlassFish server;

	public String getName() {
		return "GlassFish";
	}

	public void start(Map<String, String> contexts, int httpPort, int httpsPort) throws Exception {
		if (server != null) {
			throw new IllegalStateException("Web server is already running.");
		}

		GlassFishProperties gfProps = new GlassFishProperties();
		if (httpPort > 0) {
			gfProps.setPort("http-listener", httpPort);
		}
//		if (httpsPort > 0) {
//			gfProps.setPort("https-listener", httpsPort);
//		}

		server = GlassFishRuntime.bootstrap().newGlassFish(gfProps);

		for (String contextPath : contexts.keySet()) {
			File war = new File(contexts.get(contextPath));
			Deployer deployer = server.getDeployer();
			deployer.deploy(war, "--name="+war.getName(), "--contextroot="+contextPath, "--force=true");
		}

		server.start();
	}

	public void stop() throws Exception {
		if (server == null) {
			throw new IllegalStateException("Web server is not running.");
		}

		server.stop();
		server.dispose();

		server = null;
	}
}
