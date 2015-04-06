package resources.representations.proxy;

import org.restlet.representation.AppendableRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class XMLProxyResource extends ServerResource {

    @Get("xml")
    public Representation represent() {
        String url = (String) getRequest().getAttributes().get("url");
        try (InputStream input = new URL(url).openStream()) {
            AppendableRepresentation representation = new AppendableRepresentation();
            Scanner sc = new Scanner(input);
            while (sc.hasNextLine()) {
                representation.append(sc.nextLine());
            }
            return representation;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e); // TODO: Handle exception
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO: Handle exception
        }
    }

}
