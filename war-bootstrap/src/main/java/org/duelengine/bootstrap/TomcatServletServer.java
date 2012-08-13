package org.duelengine.bootstrap;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import org.apache.catalina.LifecycleListener;
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

		boolean loadJSP = getClass().getResource("/javax/servlet/jsp/resources/jsp_2_0.xsd") != null;

		LifecycleListener minListener = loadJSP ? null : new MinimalLifecycleListener();

		for (String contextPath : contexts.keySet()) {
			String warPath = contexts.get(contextPath);

			if (loadJSP) {
				// alternatively do this to include JSP
				server.addWebapp(contextPath, warPath);

			} else {
				StandardContext cx = new StandardContext();
				cx.setPath(contextPath);
				cx.setDocBase(warPath);

				cx.addLifecycleListener(minListener);

				ContextConfig cxCfg = new ContextConfig();
				cx.addLifecycleListener(cxCfg);

				// prevent it from looking (if it finds one, it'll have dup error)
				// "org/apache/catalin/startup/NO_DEFAULT_XML"
				cxCfg.setDefaultWebXml(server.noDefaultWebXmlPath());

				server.getHost().addChild(cx);
			}
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
