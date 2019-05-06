package ua.tqs.Homework.Controllers;

import java.io.BufferedReader;
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
import ua.tqs.Homework.Utils.CacheOperationsStats;
import ua.tqs.Homework.Entities.City;
import ua.tqs.Homework.Entities.Weather;
import ua.tqs.Homework.Entities.WeatherDescription;
import ua.tqs.Homework.Repositories.CacheStatsRepository;
import ua.tqs.Homework.Repositories.CityRepository;
import ua.tqs.Homework.Repositories.WeatherDescriptionRepository;
import ua.tqs.Homework.Utils.Cache;

@Controller
@RequestMapping(path = "/city")
public class CityController {
    
    @Autowired
    private CityRepository cityRepo;
        
    @Autowired
    private WeatherDescriptionRepository weatherDescRepo;
    
    @Autowired
    private CacheStatsRepository statsRepo;
    
    
    @CrossOrigin(origins = "*")
    @GetMapping
    public @ResponseBody Iterable<City> findAll() {
        CacheOperationsStats.incPedidos(statsRepo.getOne("cache"), statsRepo);
        return cityRepo.findAll();
    }
    
    
    @GetMapping(path = "/{id}/weather")
    public @ResponseBody Iterable<Weather> weatherById(@PathVariable("id") int id) throws Exception {
        CacheOperationsStats.incPedidos(statsRepo.getOne("cache"), statsRepo);
        List<Weather> weatherList;        
        if (Cache.getWeather(id).isEmpty()) {
            weatherList = getWeatherCity(id, weatherDescRepo);
            System.out.println(weatherList);
            
            for (Weather weather : weatherList) {
                Cache.addWeather(id, weather);
            }            
            CacheOperationsStats.incMisses(statsRepo.getOne("cache"), statsRepo);
            System.out.println("Refresh");
        } else {
            weatherList = Cache.getWeather(id);
            CacheOperationsStats.incHits(statsRepo.getOne("cache"), statsRepo);
            System.out.println("Cache");
        }
        return weatherList;
    }
    
   
    private static List<Weather> getWeatherCity(int idCity, WeatherDescriptionRepository weatherDescRepo) throws Exception{
        List<Weather> weatherList = new ArrayList<>();
        URL urlForGetRequest = new URL("http://api.ipma.pt/open-data/forecast/meteorology/cities/daily/" + idCity + ".json");
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
                int idWeatherType = (int)temp.get("idWeatherType");
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
