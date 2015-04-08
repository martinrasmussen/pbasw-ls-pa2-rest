package api.remote.restcountries;

import interfaces.Country;

public class RestCountriesCountry implements Country {

    private String name;
    private String capital;
    private String iso;
    private double longitude;
    private double latitude;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCapital() {
        return capital;
    }

    @Override
    public void setCapital(String capitalCity) {
        this.capital = capitalCity;
    }

    @Override
    public String getISO() {
        return iso;
    }

    @Override
    public void setISO(String iso2Code) {
        this.iso = iso2Code;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
