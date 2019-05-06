/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.tqs.homework.apiTests;


import static org.hamcrest.Matchers.is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ua.tqs.homework.entities.City;
import ua.tqs.homework.repositories.CityRepository;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.tqs.homework.entities.Weather;
import ua.tqs.homework.entities.WeatherDescription;
import ua.tqs.homework.repositories.CacheStatsRepository;
import ua.tqs.homework.repositories.WeatherDescriptionRepository;
import ua.tqs.homework.utils.Cache;
import ua.tqs.homework.utils.CacheOperationsStats;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ControllerTests {
    
    @Autowired
    private MockMvc mvc;
 
    @Autowired
    private CityRepository cityRepo;
    
    @Autowired
    private CacheStatsRepository statsRepo;
    
    @Autowired
    private WeatherDescriptionRepository weatherDescRepo;
    
    @Test
    public void givenCity_getCities() throws Exception {
        Cache.initCache();
        CacheOperationsStats.createCacheStats(statsRepo, "cache");
        City city = new City(1, "Test");
        cityRepo.save(city);
                
        mvc.perform(MockMvcRequestBuilders.get("/city")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].globalIdLocal", is(city.getGlobalIdLocal())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].local", is(city.getLocal())));
    }
    
    @Test
    public void givenCity_getWeather() throws Exception {
        Cache cache = Cache.initCache();
        CacheOperationsStats.createCacheStats(statsRepo, "cache");
        cache.addCity(1);
        Weather weather = new Weather("0", "0", "0", "teste", "teste", "teste");
        cache.addWeather(1, weather);
        
        mvc.perform(MockMvcRequestBuilders.get("/city/1/weather")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].precipitaProb", is(weather.getPrecipitaProb())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tMin", is(weather.gettMin())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tMax", is(weather.gettMax())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].predWindDir", is(weather.getPredWindDir())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idWeatherType", is(weather.getIdWeatherType())));
    }
    
    @Test
    public void getWeatherDesc() throws Exception {
        Cache.initCache();
        CacheOperationsStats.createCacheStats(statsRepo, "cache");
        WeatherDescription weatherDesc = new WeatherDescription(100, "teste", "test");
        weatherDescRepo.save(weatherDesc);
        
        mvc.perform(MockMvcRequestBuilders.get("/weatherdescription")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[29].idWeatherType", is(weatherDesc.getIdWeatherType())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[29].descIdWeatherTypePT", is(weatherDesc.getDescIdWeatherTypePT())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[29].descIdWeatherTypeEN", is(weatherDesc.getDescIdWeatherTypeEN())));
    }
}
