package ua.tqs.homework.testEntities;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ua.tqs.homework.entities.CacheStats;
import ua.tqs.homework.repositories.CacheStatsRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CacheStatsRepoTest {
    
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private CacheStatsRepository cacheStatsRepo;
    
    @Test
    public void whenGetById_returnCacheStats() {
        CacheStats cacheStats = new CacheStats("test");
        entityManager.persistAndFlush(cacheStats);
        
        CacheStats found = cacheStatsRepo.getOne("test");
        assertTrue(found.getName().equals(cacheStats.getName()));
    }
    
}
