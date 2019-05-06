/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.tqs.homework;

import ua.tqs.homework.repositories.CacheStatsRepository;
import ua.tqs.homework.repositories.CityRepository;
import ua.tqs.homework.repositories.WeatherDescriptionRepository;
import ua.tqs.homework.utils.CacheOperationsStats;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.tqs.homework.entities.City;
import ua.tqs.homework.entities.WeatherDescription;
import ua.tqs.homework.utils.Cache;

@Component
public class ScheduledTasks {
     
    private static Boolean cacheInit = false;
    
    @Autowired
    private CityRepository cityRepo;
    
    @Autowired
    private WeatherDescriptionRepository weatherDescRepo;
        
    @Autowired
    private CacheStatsRepository statsRepo;
    
    @Scheduled(fixedRate=600000)
    public void run() throws Exception {
        
        if (!cacheInit) {
            Cache.initCache();
            getCities();
            getWeatherDescription();
            CacheOperationsStats.createCacheStats(statsRepo, "cache");
            cacheInit = true;
        } else {
            Cache.resetCache();
        }
    }
    
    public void getCities() throws IOException {
        URL urlForGetRequest = new URL("http://api.ipma.pt/open-data/distrits-islands.json");
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        
        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            StringBuilder response;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()))) {
                response = new StringBuilder();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
            }
            
            JSONObject jsonObj = new JSONObject(response.toString());
            JSONArray cities = jsonObj.getJSONArray("data");
            
            for (int i = 0; i < cities.length(); i++) {
                JSONObject temp = cities.getJSONObject(i);
                City city = new City((int)temp.get("globalIdLocal"), (String)temp.get("local") );
                cityRepo.save(city);
                Cache.addCity(city.getGlobalIdLocal());
            }
            
        }
    }
    
    public void getWeatherDescription() throws IOException {
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
        }
    }
}
    
    

