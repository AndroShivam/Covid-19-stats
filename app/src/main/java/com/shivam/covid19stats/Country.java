package com.shivam.covid19stats;

public class Country {

    private String countryFlag;
    private String countryName;
    private int countryTotalCases;
    private int countryTotalDeaths;
    private int countryTotalRecovered;

    public Country(String countryFlag, String countryName, int countryTotalCases, int countryTotalDeaths, int countryTotalRecovered) {
        this.countryFlag = countryFlag;
        this.countryName = countryName;
        this.countryTotalCases = countryTotalCases;
        this.countryTotalDeaths = countryTotalDeaths;
        this.countryTotalRecovered = countryTotalRecovered;
    }

    public Country(String countryName, int countryTotalCases, int countryTotalDeaths, int countryTotalRecovered) {
        this.countryName = countryName;
        this.countryTotalCases = countryTotalCases;
        this.countryTotalDeaths = countryTotalDeaths;
        this.countryTotalRecovered = countryTotalRecovered;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getCountryTotalCases() {
        return countryTotalCases;
    }

    public int getCountryTotalDeaths() {
        return countryTotalDeaths;
    }

    public int getCountryTotalRecovered() {
        return countryTotalRecovered;
    }
}
