package ua.tqs.homework.testEntities;

import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ua.tqs.homework.entities.WeatherDescription;
import ua.tqs.homework.repositories.WeatherDescriptionRepository;



@RunWith(SpringRunner.class)
@DataJpaTest
public class WeatherDescTest {
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private WeatherDescriptionRepository weatherDescRepo;
    
    @Test
    public void whenGetById_returnWeatherDesc() {
        WeatherDescription weatherDesc = new WeatherDescription(1, "teste", "test");
        entityManager.persistAndFlush(weatherDesc);
        
        WeatherDescription found = weatherDescRepo.getOne(1);
        assertTrue(found.getIdWeatherType()==weatherDesc.getIdWeatherType());
    }
    
    @Test
    public void whenFindAll_containsWeatherDesc() {
        WeatherDescription weatherDesc = new WeatherDescription(1, "teste", "test");
        entityManager.persistAndFlush(weatherDesc);
        
        List<WeatherDescription> found = weatherDescRepo.findAll();
        assertTrue(found.contains(weatherDesc));
    }
    
}
