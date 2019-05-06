package ua.tqs.homework.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.tqs.homework.utils.CacheOperationsStats;
import ua.tqs.homework.entities.City;
import ua.tqs.homework.entities.Weather;
import ua.tqs.homework.entities.WeatherDescription;
import ua.tqs.homework.repositories.CacheStatsRepository;
import ua.tqs.homework.repositories.CityRepository;
import ua.tqs.homework.repositories.WeatherDescriptionRepository;
import ua.tqs.homework.utils.Cache;

@Controller
@RequestMapping(path = "/city")
public class CityController {
    
    @Autowired
    private CityRepository cityRepo;
        
    @Autowired
    private WeatherDescriptionRepository weatherDescRepo;
    
    @Autowired
    private CacheStatsRepository statsRepo;
    
    private static String cacheName = "cache";
    
    
    @CrossOrigin(origins = "*")
    @GetMapping
    public @ResponseBody Iterable<City> findAll() {
        CacheOperationsStats.incPedidos(statsRepo.getOne(cacheName), statsRepo);
        return cityRepo.findAll();
    }
    
    
    @GetMapping(path = "/{id}/weather")
    public @ResponseBody Iterable<Weather> weatherById(@PathVariable("id") int id) throws IOException {
        CacheOperationsStats.incPedidos(statsRepo.getOne(cacheName), statsRepo);
        List<Weather> weatherList;        
        if (Cache.getWeather(id).isEmpty()) {
            weatherList = getWeatherCity(id, weatherDescRepo);            
            for (Weather weather : weatherList) {
                Cache.addWeather(id, weather);
            }            
            CacheOperationsStats.incMisses(statsRepo.getOne(cacheName), statsRepo);
        } else {
            weatherList = Cache.getWeather(id);
            CacheOperationsStats.incHits(statsRepo.getOne(cacheName), statsRepo);
        }
        return weatherList;
    }
    
   
    private static List<Weather> getWeatherCity(int idCity, WeatherDescriptionRepository weatherDescRepo) throws IOException {
        List<Weather> weatherList = new ArrayList<>();
        URL urlForGetRequest = new URL("http://api.ipma.pt/open-data/forecast/meteorology/cities/daily/" + idCity + ".json");
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
                Weather weather = new Weather((String)temp.get("precipitaProb"), (String)temp.get("tMin"), (String)temp.get("tMax"), weatherDescriptionPT((int)temp.get("idWeatherType"), weatherDescRepo), (String)temp.get("predWindDir"), (String)temp.get("forecastDate"));
                weatherList.add(weather);
            }
        }
        
        return weatherList;
    }
    
    
    private static String weatherDescriptionPT(int id, WeatherDescriptionRepository weatherDescRepo) {
        WeatherDescription wd = weatherDescRepo.getOne(id);
        return wd.getDescIdWeatherTypePT();
    }
    
    
    
}
