package application;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;
import resources.HelloWorldResource;
import resources.HelloWorldRestlet;
import resources.representations.countryinfo.CountryInfoResource;

import javax.security.auth.login.Configuration;

public class ServerApplication extends Application {

    public static void main(String[] args) throws Exception {
        Component component = new Component();

        component.getServers().add(Protocol.HTTP, 8080); // We want to allow HTTP requests
        component.getClients().add(Protocol.FILE); // We need this to map to our styles and images

        final ServerApplication application = new ServerApplication();
        component.getDefaultHost().attach(application);

        component.start();

    }

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public synchronized Restlet createInboundRoot() {
        Router router = new Router(getContext());

        router.attach("/resource", HelloWorldResource.class);
        router.attach("/restlet", new HelloWorldRestlet());
        router.attach("/country/{countryName}", CountryInfoResource.class);

        String classPath = getClass().getClassLoader().getResource("").getPath();
        final String protocol = "file://";
        router.attach("/images", new Directory(getContext(), protocol+classPath+"/images/"));
        router.attach("/styles", new Directory(getContext(), protocol+classPath+"/styles/"));

        return router;
    }

}
