package ua.tqs.homework.testUtils;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import ua.tqs.homework.entities.Weather;
import ua.tqs.homework.utils.Cache;

@RunWith(SpringRunner.class)
public class TestCache {
    
    private Cache cache;
    
    @Before
    public void init() {
        cache = Cache.initCache();
    }
    
    @Test
    public void testAddCity(){
        int idCity = 1;
        cache.addCity(idCity);
        assertTrue(Cache.hasCity(idCity));
    }
    
    @Test
    public void testAddAndGetWeather() {
        int idCity = 1;
        cache.addCity(idCity);
        Weather weather = new Weather();
        cache.addWeather(idCity, weather);
        assertTrue(cache.getWeather(idCity).contains(weather));
    }
    
    @Test
    public void testResetCache() {
        int idCity = 1;
        cache.addCity(idCity);
        Weather weather = new Weather();
        cache.addWeather(idCity, weather);
        cache.resetCache();
        assertTrue(cache.getWeather(idCity).isEmpty());
    }
}
