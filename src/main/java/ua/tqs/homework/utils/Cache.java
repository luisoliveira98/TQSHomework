package ua.tqs.homework.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ua.tqs.homework.entities.Weather;


public class Cache {
    
    private static Cache cache = new Cache();    
    private static Map<Integer, List<Weather>> map = new HashMap<>();
    
    private  Cache() {
    }
    
    public static boolean hasCity(int idCity){
        return map.containsKey(idCity);
    }
    
    public static Cache initCache() {
        return cache;
    }
    
    public void addCity(int cityId) {
        List<Weather> temp = new ArrayList<>();
        map.put(cityId, temp);
    }
    
    public void addWeather(int cityId, Weather weather) {
        List<Weather> temp = map.get(cityId);
        temp.add(weather);
        map.put(cityId, temp);
    }
    
    public List getWeather(int cityId) {
        return map.get(cityId);
    }
    
    public void resetCache() {
        for (int key : map.keySet()) {
            List<Weather> temp = new ArrayList<>();
            map.put(key, temp);
        }
    }
    
}
