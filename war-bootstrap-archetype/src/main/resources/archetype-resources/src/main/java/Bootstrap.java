#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Bootstrap {

	private static final String SEPARATOR = "========================================";
	private static final String HELP = "java -jar ${artifactId}.jar${symbol_escape}n"+
			"  --help                 : this help text${symbol_escape}n"+
			"  -war <context>=<path>  : set the context of war (e.g., /=./root.war)${symbol_escape}n" +
			"  -p <port>              : set the HTTP listening port (default: 8080)${symbol_escape}n"+
			"  -s <port>              : set the HTTPS listening port (default: none)${symbol_escape}n" +
			"  --tomcat               : use Tomcat as servlet container (default)${symbol_escape}n" +
			"  --jetty                : use Jetty as servlet container${symbol_escape}n" +
			"  --glassfish            : use GlassFish as servlet container";

	public static void main(String[] args) {

		Map<String, String> contexts = new HashMap<String, String>();
		int port = 8080;
		int https = -1;
		ServletServer server = null;
		try {
			System.out.println(SEPARATOR);
			System.out.println("WAR Bootstrap\n");
			for (int i=0; i<args.length; i++) {
				String arg = args[i];
				if ("-p".equals(arg)) {
					port = Integer.parseInt(args[++i]);

				} else if ("-s".equals(arg)) {
					https = Integer.parseInt(args[++i]);

				} else if ("-war".equals(arg)) {
					String contextPath;
					String warPath = args[++i];
					int delim = warPath.indexOf('=');
					if (delim < 1) {
						contextPath = "/";
					} else {
						contextPath = warPath.substring(0, delim);
					}
					warPath = new File(warPath.substring(delim+1)).getCanonicalPath();
					contexts.put(contextPath, warPath);

				} else if ("--glassfish".equalsIgnoreCase(arg)) {
					server = new GlassFishServletServer();

				} else if ("--jetty".equalsIgnoreCase(arg)) {
					server = new JettyServletServer();

				} else if ("--tomcat".equalsIgnoreCase(arg)) {
					server = new TomcatServletServer();

				} else if ("--help".equalsIgnoreCase(arg)) {
					System.out.println(HELP);
					System.out.println(SEPARATOR);
					return;

				} else {
					System.out.println(HELP);
					System.out.println(SEPARATOR);
					return;
				}
			}

			if (server == null) {
				server = new TomcatServletServer();
			}

			System.out.println(" - servlet container: "+server.getName());

			for (String contextPath : contexts.keySet()) {
				System.out.println(" - context root: "+contextPath);
				System.out.println(" - war path: "+contexts.get(contextPath));
			}
			if (port > 0) {
				System.out.println(" - Listening to HTTP on port: "+port);
			} else {
				System.out.println(" - Not listening to HTTP");
			}
			if (https > 0) {
				System.out.println(" - Listening to HTTPS on port: "+https);
			} else {
				System.out.println(" - Not listening to HTTPS");
			}

			System.out.println("${symbol_escape}nPress ENTER to exit.");
			System.out.println(SEPARATOR);
			System.out.println();

			server.start(contexts, port, https);

			System.in.read();

			System.out.println(SEPARATOR);
			System.out.println("WAR Bootstrap exiting...");
			System.out.println(SEPARATOR);
			System.out.println();

			server.stop();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
