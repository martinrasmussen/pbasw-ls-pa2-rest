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
    public String getCapital() {
        return capitalCity;
    }

    @Override
    public String getISO() {
        return iso2Code;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }
}
