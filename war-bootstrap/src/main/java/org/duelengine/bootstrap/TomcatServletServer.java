package org.duelengine.bootstrap;

import java.io.File;
import java.util.Map;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;

class TomcatServletServer implements ServletServer {
	private Tomcat server;

	public String getName() {
		return "Tomcat";
	}

	public void start(Map<String, String> contexts, int httpPort, int httpsPort) throws Exception {
		if (server != null) {
			throw new IllegalStateException("Web server is already running.");
		}

		server = new Tomcat();
		server.setBaseDir(new File("tomcat").getCanonicalPath());
		server.setPort(httpPort);

//		server.addWebapp(contextPath, warPath);

		for (String contextPath : contexts.keySet()) {
			StandardContext ctx = new StandardContext();
			ctx.setPath(contextPath);
			ctx.setDocBase(contexts.get(contextPath));

			ctx.addLifecycleListener(new Tomcat.DefaultWebXmlListener());

			ContextConfig ctxCfg = new ContextConfig();
			ctx.addLifecycleListener(ctxCfg);

			// prevent it from looking ( if it finds one - it'll have dup error )
			ctxCfg.setDefaultWebXml("org/apache/catalin/startup/NO_DEFAULT_XML");

			server.getHost().addChild(ctx);
		}

		server.start();
	}

	public void stop() throws Exception {
		if (server == null) {
			throw new IllegalStateException("Web server is not running.");
		}

		server.stop();
		server.destroy();

		server = null;
	}
}
