package org.worldbank.models;

/**
 * Created by Taylan on 1.06.2016.
 */
public class CountryData {

    private String gdp;
    private String population;
    private String co2;

    public String getGdp() {
        return gdp;
    }

    public void setGdp(String gdp) {
        this.gdp = gdp;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getCo2() {
        return co2;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }

    public CountryData(String gdp, String population, String co2) {
        this.gdp = gdp;
        this.population = population;
        this.co2 = co2;
    }
}
