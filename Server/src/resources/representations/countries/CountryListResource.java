package resources.representations.countries;

import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;

public class CountryListResource extends ServerResource {

    private List<String> testedCountries = new ArrayList<String>() {{
        add("Denmark");
        add("Mexico");
        add("United Kingdom");
        add("United States of America");
        add("Russia");
        add("China");
        add("Netherlands");
        add("Germany");
        add("France");
        add("Spain");
        add("Norway");
        add("Finland");
        add("Sweden");

        sort(String.CASE_INSENSITIVE_ORDER);
    }};

    @Get("json")
    public Representation represent() {
        return new JacksonRepresentation<>(MediaType.APPLICATION_JSON, testedCountries);
    }
}
