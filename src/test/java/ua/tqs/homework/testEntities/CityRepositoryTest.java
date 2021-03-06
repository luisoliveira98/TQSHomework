package ua.tqs.homework.testEntities;

import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ua.tqs.homework.entities.City;
import ua.tqs.homework.repositories.CityRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CityRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private CityRepository cityRepo;
    
    @Test
    public void whenGetById_returnCity() {
        City city = new City(1, "test");
        entityManager.persistAndFlush(city);
        
        City found = cityRepo.getOne(1);
        assertTrue(found.getGlobalIdLocal()==city.getGlobalIdLocal());
    }
    
    @Test
    public void whenFindAll_containsCity() {
        City city = new City(1, "test");
        entityManager.persistAndFlush(city);
        List<City> found = cityRepo.findAll();
        assertTrue(found.contains(city));
    }
    

}
