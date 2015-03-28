package resources;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * An alternative to the Restlet way (@see HelloWorldRestlet)
 */
public class HelloWorldResource extends ServerResource {

    @Get("html")
    public String represent() {
        return "<html><body><h1>Hello, Resource!</h1></body></html>";
    }

}