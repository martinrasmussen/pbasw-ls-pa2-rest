package resources.representations.countryinfo;

import api.remote.restcountries.RestCountriesNameToAlpha2Code;
import api.remote.worldbank.WorldBankCountryProvider;
import interfaces.Country;
import interfaces.CountryNameToAlpha2Converter;
import interfaces.CountryProvider;
import org.restlet.data.MediaType;
import org.restlet.ext.velocity.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.HashMap;
import java.util.Map;

public class CountryInfoResource extends ServerResource {

    @Get("html")
    public Representation represent() {
        String countryName = (String) getRequest().getAttributes().get("countryName");
        CountryNameToAlpha2Converter converter = new RestCountriesNameToAlpha2Code();
        CountryProvider provider = new WorldBankCountryProvider(converter);
        final Country country = provider.getCountryInfoFromName(countryName);
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("country", country);
        return new TemplateRepresentation("./src/resources/representations/countryinfo/countryInfo.vtl", dataModel, MediaType.TEXT_HTML);
    }

}
