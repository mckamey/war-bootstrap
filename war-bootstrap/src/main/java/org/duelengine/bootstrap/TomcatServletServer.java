package org.duelengine.bootstrap;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;

class TomcatServletServer implements ServletServer {
	private Tomcat server;
	private File tmpDir;

	public String getName() {
		return "Tomcat";
	}

	public void start(Map<String, String> contexts, int httpPort, int httpsPort) throws Exception {
		if (server != null) {
			throw new IllegalStateException("Web server is already running.");
		}

		tmpDir = new File("tomcat");

		server = new Tomcat();
		server.setBaseDir(tmpDir.getCanonicalPath());
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

		if (tmpDir != null) {
			Deque<File> stack = new ArrayDeque<File>();
			stack.push(tmpDir);
			while (stack.size() > 0) {
				File parent = stack.pop();
				if (parent.isDirectory()) {
					File[] children = parent.listFiles();
					if (children.length > 0) {
						stack.push(parent);
						for (File child : children) {
							stack.push(child);
						}
					}
				}
				parent.delete();
			}
			tmpDir = null;
		}

		server = null;
	}
}
