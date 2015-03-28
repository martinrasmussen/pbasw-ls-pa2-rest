import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import resources.HelloWorldResource;
import resources.HelloWorldRestlet;

public class ServerApplication extends Application {

    public static void main(String[] args) throws Exception {
        // Create a new Component.
        Component component = new Component();

        // Add a new HTTP server
        component.getServers().add(Protocol.HTTP, 8080);

        // Attach the sample application.
        component.getDefaultHost().attach(new ServerApplication());

        // Start the component.
        component.start();
    }

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public synchronized Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a new instance of HelloWorldResource.
        Router router = new Router(getContext());

        // Defines only one route
        router.attach("/resource", HelloWorldResource.class);
        router.attach("/restlet", new HelloWorldRestlet());

        return router;
    }

}
