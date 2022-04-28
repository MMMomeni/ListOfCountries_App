package com.example.midtronics_first_technical;

import java.io.Serializable;


public class Country implements Serializable{
    private final String country;
    private final String capital;
    private final String population;
    private final String area;
    private final String region;
    private final String subregion;


    Country(String country, String capital, String population, String area, String region,
            String subregion) {
       this.country = country;
       this.capital = capital;
       this.population = population;
       this.area = area;
       this.region = region;
       this.subregion = subregion;

    }

    public String getCountry() {
        return country;
    }
    public String getCapital() {
        return capital;
    }
    public String getPopulation() {
        return population;
    }
    public String getArea() {
        return area;
    }
    public String getRegion() {
        return region;
    }
    public String getSubregion() {
        return subregion;
    }

}
