package api.remote.restcountries;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.CountryNameToAlpha2Converter;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

public class RestCountriesNameToAlpha2Code implements CountryNameToAlpha2Converter {

    private static final String BASE_REST_PATH_TEMPLATE = "http://restcountries.eu/rest/v1/name/%s?fullText=true";
    private static final String ALPHA_2_CODE_PROPERTY = "alpha2Code";
    private static final String NAME_PROPERTY = "name";
    private static final String CAPITAL_PROPERTY = "capital";
    private static final String LATLNG_PROPERTY = "latlng";

    @Override
    public ConversionResult convert(String countryName) {
        ConversionResult.ConversionResultStatus resultStatus;
        RestCountriesCountry backupCountry = null;
        try (InputStream stream = getUrl(countryName).openStream()) {
            Reader reader = new BufferedReader(new InputStreamReader(stream));
            JsonNode node = new ObjectMapper().readTree(reader);
            backupCountry = getBackupCountry(node);
            resultStatus = isMismatch(countryName, backupCountry.getName()) ?
                    ConversionResult.ConversionResultStatus.WARN : ConversionResult.ConversionResultStatus.OK;
        } catch (IOException e) {
            resultStatus = ConversionResult.ConversionResultStatus.ERROR;
        }

        return new RestCountriesConversionResult(countryName, backupCountry.getName(), backupCountry.getISO(), resultStatus, backupCountry);
    }

    private boolean isMismatch(String requestName, String resultName) throws UnsupportedEncodingException {
        String decodedCountryName = URLDecoder.decode(requestName, "utf-8");
        return !decodedCountryName.equalsIgnoreCase(resultName);
    }

    private RestCountriesCountry getBackupCountry(JsonNode json) {
        JsonNode countryNode = json.get(0);
        String name = countryNode.get(NAME_PROPERTY).asText();
        String capital = countryNode.get(CAPITAL_PROPERTY).asText();
        String alpha2Code = countryNode.get(ALPHA_2_CODE_PROPERTY).asText();
        JsonNode latlongNode = countryNode.get(LATLNG_PROPERTY);
        double latitude = latlongNode.get(0).asDouble();
        double longitude = latlongNode.get(1).asDouble();
        RestCountriesCountry country = new RestCountriesCountry();
        country.setName(name);
        country.setCapital(capital);
        country.setISO(alpha2Code);
        country.setLatitude(latitude);
        country.setLongitude(longitude);
        return country;
    }

    private URL getUrl(String countryName) throws MalformedURLException {
        String path = String.format(BASE_REST_PATH_TEMPLATE, countryName);
        return new URL(path);
    }

    public class RestCountriesConversionResult extends ConversionResult {

        private RestCountriesCountry backupCountry;

        public RestCountriesConversionResult(String countryNameQuery, String countryNameAnswer, String alpha2Code, ConversionResultStatus status) {
            super(countryNameQuery, countryNameAnswer, alpha2Code, status);
        }

        public RestCountriesConversionResult(String countryNameQuery, String countryNameAnswer, String alpha2Code, ConversionResultStatus status, RestCountriesCountry backupCountry) {
            super(countryNameQuery, countryNameAnswer, alpha2Code, status);
            this.backupCountry = backupCountry;
        }

        public RestCountriesCountry getBackupCountry() {
            return backupCountry;
        }
    }
}
