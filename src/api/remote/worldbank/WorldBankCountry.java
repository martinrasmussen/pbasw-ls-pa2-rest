package api.remote.worldbank;

import interfaces.Country;

public class WorldBankCountry implements Country {

    private String name;
    private String capitalCity;
    private String iso2Code;
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
        return capitalCity;
    }

    @Override
    public void setCapital(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    @Override
    public String getISO() {
        return iso2Code;
    }

    @Override
    public void setISO(String iso2Code) {
        this.iso2Code = iso2Code;
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
