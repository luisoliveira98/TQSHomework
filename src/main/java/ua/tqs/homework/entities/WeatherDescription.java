/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.tqs.homework.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WeatherDescription {
    @Id
    private int idWeatherType;
    private String descIdWeatherTypePT;
    private String descIdWeatherTypeEN;
    
    public WeatherDescription() {}

    public WeatherDescription(int idWeatherType, String descIdWeatherTypePT, String descIdWeatherTypeEN) {
        this.idWeatherType = idWeatherType;
        this.descIdWeatherTypePT = descIdWeatherTypePT;
        this.descIdWeatherTypeEN = descIdWeatherTypeEN;
    }

    public int getIdWeatherType() {
        return idWeatherType;
    }

    public void setIdWeatherType(int idWeatherType) {
        this.idWeatherType = idWeatherType;
    }

    public String getDescIdWeatherTypePT() {
        return descIdWeatherTypePT;
    }

    public void setDescIdWeatherTypePT(String descIdWeatherTypePT) {
        this.descIdWeatherTypePT = descIdWeatherTypePT;
    }

    public String getDescIdWeatherTypeEN() {
        return descIdWeatherTypeEN;
    }

    public void setDescIdWeatherTypeEN(String descIdWeatherTypeEN) {
        this.descIdWeatherTypeEN = descIdWeatherTypeEN;
    }
    
    public String toString(){
        return descIdWeatherTypePT;
    }    
}
