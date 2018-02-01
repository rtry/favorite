package sicau.edu.cn.favorite.ano;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

import sicau.edu.cn.favorite.AppClient;
import sicau.edu.cn.favorite.constant.JettyConstant;

/**
 * ServerWithAnnotations
 *
 *
 */
public class ServerWithAnnotations {
	public static final void main(String args[]) throws Exception {
		String log4jPath = "props/log4j.properties";
		PropertyConfigurator.configure(AppClient.class.getClassLoader().getResource(log4jPath));
		// Create the server
		Server server = new Server(8080);

		// Enable parsing of jndi-related parts of web.xml and jetty-env.xml
		// org.eclipse.jetty.webapp.Configuration.ClassList classlist =
		// org.eclipse.jetty.webapp.Configuration.ClassList
		// .setServerDefault(server);
		// classlist.addAfter("org.eclipse.jetty.annotations.AnnotationConfiguration",
		// "org.eclipse.jetty.plus.webapp.EnvConfiguration",
		// "org.eclipse.jetty.plus.webapp.PlusConfiguration");
		// classlist.addBefore("org.eclipse.jetty.annotations.AnnotationConfiguration");

		// Create a WebApp
		// WebAppContext webapp = new WebAppContext();
		// webapp.setContextPath("/");
		// webapp.setWar("../../tests/test-webapps/test-servlet-spec/test-spec-webapp/target/test-spec-webapp-9.0.4-SNAPSHOT.war");
		// server.setHandler(webapp);

		WebAppContext context = new WebAppContext();
		context.setContextPath(JettyConstant.urlseparator);
		String proPath = System.getProperty("user.dir");

		context.setResourceBase(proPath + "/src/main/webapp");
		// context.addServlet(SearchServlet.class, "/search");
		context.setConfigurations(new Configuration[] { new AnnotationConfiguration(),
				new WebXmlConfiguration(), new WebInfConfiguration(), new PlusConfiguration(),
				new MetaInfConfiguration(), new FragmentConfiguration(), new EnvConfiguration() });
		server.setHandler(context);

		// Configure a LoginService
		HashLoginService loginService = new HashLoginService();
		loginService.setName("Test Realm");
		loginService.setConfig("src/test/resources/realm.properties");
		server.addBean(loginService);

		server.start();
		server.join();
	}
}