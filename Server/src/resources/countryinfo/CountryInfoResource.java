package resources.countryinfo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import interfaces.Country;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class CountryInfoResource extends ServerResource {

    @Get("html")
    public Representation represent() {
        String countryName = (String) getRequest().getAttributes().get("countryName");
        return new StringRepresentation(getAlpha2Code(countryName));
    }

    private Country getCountryInfo(String country) {
        return null;
    }

    private String getAlpha2Code(String countryName) {
        String result = null;
        try (InputStream stream = new URL("http://restcountries.eu/rest/v1/name/"+countryName).openStream()) {
            Reader reader = new BufferedReader(new InputStreamReader(stream));
            JsonNode node = new ObjectMapper().readTree(reader);
            result = node.get(0).get("alpha2Code").asText();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
