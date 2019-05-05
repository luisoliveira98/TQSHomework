package ua.tqs.Homework.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ua.tqs.Homework.Entities.Weather;


public class Cache {
    
    private static Cache cache;    
    private static Map<Integer, List<Weather>> map = new HashMap<>();
    
    private  Cache() {
    }
    
    public static boolean hasCity(int idCity){
        return map.containsKey(idCity);
    }
    
    public static void initCache() {
        Cache.cache = new Cache();
    }
    
    public static void addCity(int cityId) {
        List<Weather> temp = new ArrayList<>();
        map.put(cityId, temp);
    }
    
    public static void addWeather(int cityId, Weather weather) {
        List<Weather> temp = map.get(cityId);
        temp.add(weather);
        map.put(cityId, temp);
    }
    
    public static List getWeather(int cityId) {
        return map.get(cityId);
    }
    
    public static void resetCache() {
        for (int key : map.keySet()) {
            List<Weather> temp = new ArrayList<>();
            map.put(key, temp);
        }
    }
    
}
