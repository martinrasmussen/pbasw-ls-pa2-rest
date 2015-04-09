package interfaces;

public interface CountryProvider {

    Country getCountryInfoFromName(String countryName);

    Country getCountryInfoFromAlpha2Code(String alpha2Code);

}
