package com.weather.weatherreporter.models;

/**
 * used to display the weather details
 */

public class WeatherModel {
    private String when;
    private String tMax;
    private String tMin;
    private String tNow;
    private String tDate;
    private String tSummary;
    private String iconUrl;
    private String humidty;
    private String latitude;
    private String longitude;

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String gettMax() {
        return tMax;
    }

    public void settMax(String tMax) {
        this.tMax = tMax;
    }

    public String gettMin() {
        return tMin;
    }

    public void settMin(String tMin) {
        this.tMin = tMin;
    }

    public String gettNow() {
        return tNow;
    }

    public void settNow(String tNow) {
        this.tNow = tNow;
    }

    public String gettDate() {
        return tDate;
    }

    public void settDate(String tDate) {
        this.tDate = tDate;
    }

    public String gettSummary() {
        return tSummary;
    }

    public void settSummary(String tSummary) {
        this.tSummary = tSummary;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getHumidty() {
        return humidty;
    }

    public void setHumidty(String humidty) {
        this.humidty = humidty;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
