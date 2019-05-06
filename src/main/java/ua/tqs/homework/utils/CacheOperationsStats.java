package ua.tqs.homework.utils;


import ua.tqs.homework.entities.CacheStats;
import ua.tqs.homework.repositories.CacheStatsRepository;


public class CacheOperationsStats {
    
    private CacheOperationsStats() {}
    
    public static void createCacheStats(CacheStatsRepository cacheStatsRepo, String name) {
        cacheStatsRepo.save(new CacheStats(name));
    }
    
    public static void incPedidos(CacheStats cache, CacheStatsRepository cacheStatsRepo) {
        cache.setnPedidos(cache.getnPedidos()+1);
        cacheStatsRepo.save(cache);
    }
    
    public static void incHits(CacheStats cache, CacheStatsRepository cacheStatsRepo) {
        cache.setHits(cache.getHits()+1);
        cacheStatsRepo.save(cache);
    }
    
    public static void incMisses(CacheStats cache, CacheStatsRepository cacheStatsRepo) {
        cache.setMisses(cache.getMisses()+1);
        cacheStatsRepo.save(cache);
    }
}
