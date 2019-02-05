package org.vaadin.flow.light;

import com.vaadin.flow.server.startup.ServletContextListeners;

import java.net.URI;
import java.net.URL;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

/** Minimal Servlet bootstrap for Vaadin application.
 *
 * @author A.Mahdy Abdelaziz
 */
public class App {

    public static final String JAR_PATTERN = "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern";
    public static final String PRODUCTION_MODE = "productionMode";
    public static final String ORIGINAL_FRONTEND_RESOURCES = "original.frontend.resources";

    public static void main(String[] args) throws Exception {

        URL webRootLocation = App.class.getResource("/webapp/");
        URI webRootUri = webRootLocation.toURI();

        WebAppContext context = new WebAppContext();
        context.setBaseResource(Resource.newResource(webRootUri));
        context.setContextPath("/");
        context.setAttribute(JAR_PATTERN , ".*");
        context.setConfigurationDiscovered(true);
        context.setInitParameter(PRODUCTION_MODE , "true");
        context.setInitParameter(ORIGINAL_FRONTEND_RESOURCES , "true");
        context.setConfigurations(new Configuration[]{
                new AnnotationConfiguration(),
                new WebInfConfiguration(),
                new WebXmlConfiguration(),
                new MetaInfConfiguration()
        });
        context.getServletContext().setExtendedListenerTypes(true);
        context.addEventListener(new ServletContextListeners());

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
        server.join();
    }
}
