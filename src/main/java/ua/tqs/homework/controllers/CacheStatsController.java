/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.tqs.homework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.tqs.homework.entities.CacheStats;
import ua.tqs.homework.repositories.CacheStatsRepository;

@Controller
@RequestMapping(path = "/stats")
public class CacheStatsController {
    
    @Autowired
    private CacheStatsRepository statsRepo;
    
    
    @CrossOrigin(origins = "*")
    @GetMapping
    public @ResponseBody Iterable<CacheStats> findAll() {
        return statsRepo.findAll();
    }
    
}
