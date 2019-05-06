/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.tqs.Homework.APITests;


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
import ua.tqs.Homework.Entities.City;
import ua.tqs.Homework.Repositories.CityRepository;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.tqs.Homework.Entities.Weather;
import ua.tqs.Homework.Entities.WeatherDescription;
import ua.tqs.Homework.Repositories.CacheStatsRepository;
import ua.tqs.Homework.Repositories.WeatherDescriptionRepository;
import ua.tqs.Homework.Utils.Cache;
import ua.tqs.Homework.Utils.CacheOperationsStats;

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
        Cache.initCache();
        CacheOperationsStats.createCacheStats(statsRepo, "cache");
        Cache.addCity(1);
        Weather weather = new Weather("0", "0", "0", "teste", "teste", "teste");
        Cache.addWeather(1, weather);
        
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
        WeatherDescription weatherDesc = new WeatherDescription(0, "teste", "test");
        weatherDescRepo.save(weatherDesc);
        
        mvc.perform(MockMvcRequestBuilders.get("/weatherdescription")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idWeatherType", is(weatherDesc.getIdWeatherType())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].descIdWeatherTypePT", is(weatherDesc.getDescIdWeatherTypePT())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].descIdWeatherTypeEN", is(weatherDesc.getDescIdWeatherTypeEN())));
    }
}
