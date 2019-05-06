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
    
    @Before
    public void init() {
        Cache.initCache();
    }
    
    @Test
    public void testAddCity(){
        int idCity = 1;
        Cache.addCity(idCity);
        assertTrue(Cache.hasCity(idCity));
    }
    
    @Test
    public void testAddAndGetWeather() {
        int idCity = 1;
        Cache.addCity(idCity);
        Weather weather = new Weather();
        Cache.addWeather(idCity, weather);
        assertTrue(Cache.getWeather(idCity).contains(weather));
    }
    
    @Test
    public void testResetCache() {
        int idCity = 1;
        Cache.addCity(idCity);
        Weather weather = new Weather();
        Cache.addWeather(idCity, weather);
        Cache.resetCache();
        assertTrue(Cache.getWeather(idCity).isEmpty());
    }
}
