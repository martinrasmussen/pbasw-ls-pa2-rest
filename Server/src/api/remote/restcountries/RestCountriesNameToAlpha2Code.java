package api.remote.restcountries;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.CountryNameToAlpha2Converter;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class RestCountriesNameToAlpha2Code implements CountryNameToAlpha2Converter {

    private static final String BASE_REST_PATH = "http://restcountries.eu/rest/v1/name/";
    private static final String ALPHA_2_CODE_PROPERTY_NAME = "alpha2Code";

    @Override
    public String convert(String countryName) {
        String result;
        try (InputStream stream = new URL(BASE_REST_PATH + countryName).openStream()) {
            Reader reader = new BufferedReader(new InputStreamReader(stream));
            JsonNode node = new ObjectMapper().readTree(reader);
            result = node.get(0).get(ALPHA_2_CODE_PROPERTY_NAME).asText();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e); // TODO: Handle exception
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO: Handle exception
        }
        return result;
    }
}
