package resources;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;

/**
 * An alternative to the Resource way (@see HelloWorldResource)
 */
public class HelloWorldRestlet extends Restlet {
    @Override
    public void handle(Request request, Response response) {
        String responseMessage = "<html><body><h1>Hello, Restlet!</h1></body></html>";
        response.setEntity(responseMessage, MediaType.TEXT_HTML);
    }
}
