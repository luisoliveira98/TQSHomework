/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.tqs.Homework;

import ua.tqs.Homework.Utils.CacheOperationsStats;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.tqs.Homework.Entities.City;
import ua.tqs.Homework.Entities.WeatherDescription;
import ua.tqs.Homework.Repositories.*;
import ua.tqs.Homework.Utils.Cache;

@Component
public class ScheduledTasks {
    
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    
    private static Boolean CACHEINIT = false;
    
    @Autowired
    private CityRepository cityRepo;
    
    @Autowired
    private WeatherDescriptionRepository weatherDescRepo;
        
    @Autowired
    private CacheStatsRepository statsRepo;
    
    @Scheduled(fixedRate=600000)
    public void run() throws Exception {
        if (CACHEINIT==false) {
            Cache.initCache();
            getCities();
            getWeatherDescription();
            CacheOperationsStats.createCacheStats(statsRepo, "cache");
            CACHEINIT = true;
        } else {
            Cache.resetCache();
        }
    }
    
    public void getCities() throws Exception {
        URL urlForGetRequest = new URL("http://api.ipma.pt/open-data/distrits-islands.json");
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        
        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            
            JSONObject jsonObj = new JSONObject(response.toString());
            JSONArray cities = jsonObj.getJSONArray("data");
            
            for (int i = 0; i < cities.length(); i++) {
                JSONObject temp = cities.getJSONObject(i);
                City city = new City((int)temp.get("globalIdLocal"), (String)temp.get("local") );
                cityRepo.save(city);
                Cache.addCity(city.getGlobalIdLocal());
            }
            
        } else {
            System.out.println("GET NOT WORKED");
        }
        
        //log.info(cityRepo.findAll().toString());
    }
    
    public void getWeatherDescription() throws Exception {
        URL urlForGetRequest = new URL("http://api.ipma.pt/open-data/weather-type-classe.json");
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        
        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            
            JSONObject jsonObj = new JSONObject(response.toString());
            JSONArray weatherTypes = jsonObj.getJSONArray("data");
            
            for (int i = 0; i < weatherTypes.length(); i++) {
                JSONObject temp = weatherTypes.getJSONObject(i);
                WeatherDescription weather = new WeatherDescription((int)temp.get("idWeatherType"), (String)temp.get("descIdWeatherTypePT"), (String)temp.get("descIdWeatherTypeEN") );
                weatherDescRepo.save(weather);
            }
        }else {
            System.out.println("GET NOT WORKED");
        }
        
        //log.info(weatherDescRepo.findAll().toString());
    }
}
    
    

