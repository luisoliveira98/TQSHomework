/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.tqs.Homework.Entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class Weather implements Serializable{
    
    private String precipitaProb;
    private String tMin;
    private String tMax;
    private String idWeatherType;
    private String predWindDir;
    private String forecastDate;

    
    public Weather(){}

    public Weather(String precipitaProb, String tMin, String tMax, String idWeatherType, String predWindDir, String forecastDate) {
        this.precipitaProb = precipitaProb;
        this.tMin = tMin;
        this.tMax = tMax;
        this.idWeatherType = idWeatherType;
        this.forecastDate = forecastDate;
        this.predWindDir = predWindDir;
    }

    public String getPrecipitaProb() {
        return precipitaProb;
    }

    public void setPrecipitaProb(String precipitaProb) {
        this.precipitaProb = precipitaProb;
    }
    
    public String gettMin() {
        return tMin;
    }

    public void settMin(String tMin) {
        this.tMin = tMin;
    }

    public String gettMax() {
        return tMax;
    }

    public void settMax(String tMax) {
        this.tMax = tMax;
    }

        
    public String getIdWeatherType() {
        return idWeatherType;
    }

    public void setIdWeatherType(String idWeatherType) {
        this.idWeatherType = idWeatherType;
    }

    public String getPredWindDir() {
        return predWindDir;
    }

    public void setPredWindDir(String predWindDir) {
        this.predWindDir = predWindDir;
    }

    public String getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(String forecastDate) {
        this.forecastDate = forecastDate;
    }

}
