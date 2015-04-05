package api.remote.restcountries;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.CountryNameToAlpha2Converter;

import java.io.*;
import java.net.URL;

public class RestCountriesNameToAlpha2Code implements CountryNameToAlpha2Converter {

    private static final String BASE_REST_PATH = "http://restcountries.eu/rest/v1/name/";
    private static final String ALPHA_2_CODE_PROPERTY_NAME = "alpha2Code";

    @Override
    public ConversionResult convert(String countryName) {
        ConversionResult.ConversionResultStatus resultStatus;
        String answer = null;
        String alpha2Code = null;
        try (InputStream stream = new URL(BASE_REST_PATH + countryName).openStream()) {
            Reader reader = new BufferedReader(new InputStreamReader(stream));
            JsonNode node = new ObjectMapper().readTree(reader);
            alpha2Code = node.get(0).get(ALPHA_2_CODE_PROPERTY_NAME).asText();
            answer = node.get(0).get("name").asText();
            resultStatus = answer.equalsIgnoreCase(countryName) ?
                    ConversionResult.ConversionResultStatus.OK :
                    ConversionResult.ConversionResultStatus.WARN;
        } catch (IOException e) {
            resultStatus = ConversionResult.ConversionResultStatus.ERROR;
        }
        return new ConversionResult(countryName, answer, alpha2Code, resultStatus);
    }
}
