package ua.tqs.Homework.TestEntities;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ua.tqs.Homework.Entities.City;
import ua.tqs.Homework.Entities.WeatherDescription;
import ua.tqs.Homework.Repositories.WeatherDescriptionRepository;



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
        assertThat(found.getIdWeatherType()==weatherDesc.getIdWeatherType());
    }
    
    @Test
    public void whenFindAll_containsWeatherDesc() {
        WeatherDescription weatherDesc = new WeatherDescription(1, "teste", "test");
        entityManager.persistAndFlush(weatherDesc);
        
        List<WeatherDescription> found = weatherDescRepo.findAll();
        assertThat(found.contains(weatherDesc));
    }
    
}
