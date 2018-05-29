package com.weather.weatherreporter.models;

/**
 * used for populate the cities of a country
 */

public class CityModel {
    private int city_id;
    private String city_name;
    private String weather;
    private boolean active;
    private String sugguested;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getSugguested() {
        return sugguested;
    }

    public void setSugguested(String sugguested) {
        this.sugguested = sugguested;
    }
}
