package api.remote.worldbank;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.Country;
import interfaces.CountryNameToAlpha2Converter;
import interfaces.CountryProvider;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class WorldBankCountryProvider implements CountryProvider {

    private static final String NAME_FIELD = "name";
    private static final String CAPITAL_FIELD = "capitalCity";
    private static final String ISO_2_FIELD = "iso2Code";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String LATITUDE_FIELD = "latitude";
    private static final String API_TEMPLATE = "http://api.worldbank.org/country/%s?format=json";

    private CountryNameToAlpha2Converter nameToAlpha2Converter;
    private String url;

    public WorldBankCountryProvider(CountryNameToAlpha2Converter nameToAlpha2Converter) {
        this.nameToAlpha2Converter = nameToAlpha2Converter;
    }

    @Override
    public Country getCountryInfoFromName(String countryName) {
        if (nameToAlpha2Converter == null) throw new RuntimeException("No CountryNameToAlpha2Converter set!");
        CountryNameToAlpha2Converter.ConversionResult conversion = nameToAlpha2Converter.convert(countryName);
        return getCountryInfoFromAlpha2Code(conversion.getAlpha2Code());
    }

    @Override
    public Country getCountryInfoFromAlpha2Code(String alpha2Code) {
        WorldBankCountry countryInfo = null;
        url = String.format(API_TEMPLATE, alpha2Code);
        try (InputStream stream = new URL(url).openStream()) {
            Reader reader = new BufferedReader(new InputStreamReader(stream));
            JsonNode node = new ObjectMapper().readTree(reader);
            try {
                node = node.get(1).get(0); // Attempt to unwrap from list. Risk of nullpointer if node structure is not as expected
            } catch (NullPointerException e) {
                return null;
            }
            countryInfo = new WorldBankCountry();
            countryInfo.setName(node.get(NAME_FIELD).asText());
            countryInfo.setCapital(node.get(CAPITAL_FIELD).asText());
            countryInfo.setISO(node.get(ISO_2_FIELD).asText());
            countryInfo.setLatitude(node.get(LATITUDE_FIELD).asDouble());
            countryInfo.setLongitude(node.get(LONGITUDE_FIELD).asDouble());
        } catch (IOException e) {
            return null;
        }
        return countryInfo;
    }
}
