package resources.representations.countries;

import application.ServerApplication;
import application.VelocityManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.velocity.Template;
import org.restlet.data.MediaType;
import org.restlet.ext.velocity.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryListResource extends ServerResource {

    private static final String COUNTRY_LIST_SERVICE_URL = "http://restcountries.eu/rest/v1/all";
    private static final String COUNTRY_NAME_FIELD = "name";

    @Get("json")
    public Representation represent() {
        try (InputStream input = new URL(COUNTRY_LIST_SERVICE_URL).openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("utf-8")));
            JsonNode allCountryNodes = new ObjectMapper().readTree(reader);
            List<String> countryNames = new ArrayList<>(allCountryNodes.size());
            for (int i = 0; i < allCountryNodes.size(); i++) {
                JsonNode countryNode = allCountryNodes.get(i);
                String countryName = countryNode.get(COUNTRY_NAME_FIELD).asText();
                countryNames.add(countryName);
            }
            Map<String, Object> dataModel = new HashMap<>(1);
            dataModel.put("countryNames", countryNames);
            VelocityManager velocityManager = ((ServerApplication) getApplication()).getVelocityManager();
            Template template = velocityManager.getTemplate("templates/countrylist.vtl");
            return new TemplateRepresentation(template, dataModel, MediaType.TEXT_HTML);
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO: Handle exception
        }
    }
}
