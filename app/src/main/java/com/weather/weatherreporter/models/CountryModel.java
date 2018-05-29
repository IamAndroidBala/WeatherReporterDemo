package com.weather.weatherreporter.models;

/**
 * used to populate the countries
 */

public class CountryModel {
    private String country_name;
    private int country_id;
    private String country_flag;
    private String country_cities;

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_flag() {
        return country_flag;
    }

    public void setCountry_flag(String country_flag) {
        this.country_flag = country_flag;
    }

    public String getCountry_cities() {
        return country_cities;
    }

    public void setCountry_cities(String country_cities) {
        this.country_cities = country_cities;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }
}
