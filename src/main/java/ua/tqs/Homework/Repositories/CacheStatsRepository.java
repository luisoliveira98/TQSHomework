/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.tqs.Homework.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tqs.Homework.Entities.CacheStats;


public interface CacheStatsRepository extends JpaRepository<CacheStats, String> {
    
}