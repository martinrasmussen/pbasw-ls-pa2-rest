package resources.representations.countryinfo;

import api.remote.restcountries.RestCountriesNameToAlpha2Code;
import api.remote.worldbank.WorldBankCountryProvider;
import interfaces.Country;
import interfaces.CountryNameToAlpha2Converter;
import interfaces.CountryNameToAlpha2Converter.ConversionResult;
import interfaces.CountryProvider;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.velocity.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class CountryInfoResource extends ServerResource {

    private VelocityEngine velocityEngine;

    @Get("html")
    public Representation represent() {
        String countryName = (String) getRequest().getAttributes().get("countryName");

        CountryNameToAlpha2Converter converter = new RestCountriesNameToAlpha2Code();
        ConversionResult conversion = converter.convert(countryName);

        if(conversion.getStatus() == ConversionResult.ConversionResultStatus.ERROR) return get404(conversion);

        CountryProvider provider = new WorldBankCountryProvider(converter);
        String message = conversion.getStatus() == ConversionResult.ConversionResultStatus.WARN ?
                getWarnMessage(conversion) : "";

        final Country country = provider.getCountryInfoFromAlpha2Code(conversion.getAlpha2Code());
        if(country == null) return get404(conversion); // TODO: Make fault tolerant by using another CountryProvider.

        if(velocityEngine == null) initializeVelocityEngine();

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("country", country);
        dataModel.put("message", message);

        Template template = velocityEngine.getTemplate("templates/countryinfo.vtl");
        return new TemplateRepresentation(template, dataModel, MediaType.TEXT_HTML);
    }

    private String getWarnMessage(ConversionResult conversion) {
        System.out.println("Mismatch: Query = " + conversion.getCountryNameQuery() + "; Answer = " + conversion.getCountryNameAnswer());
        return String.format("Found country does not match input completely. Assuming you meant '%s'.",
                conversion.getCountryNameAnswer());
    }

    private Representation get404(ConversionResult conversion) {
        String description = String.format("No country with the name '%s' found.", conversion.getCountryNameQuery());
        getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND, description);
        return new StringRepresentation(String.format(
                "<html>" +
                    "<head></head>" +
                    "<body>" +
                        "<h1>404</h1>" +
                        "<p>No data could be found for '%s'.</p>" +
                    "</body>" +
                "</html>",
                decodeUrlEncoding(conversion.getCountryNameQuery()))
        , MediaType.TEXT_HTML);
    }

    private String decodeUrlEncoding(String str) {
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e); // TODO: Handle exception
        }
    }

    private void initializeVelocityEngine(){
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
    }
}
