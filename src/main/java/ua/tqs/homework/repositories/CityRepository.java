/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.tqs.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tqs.homework.entities.City;


public interface CityRepository extends JpaRepository<City, Integer>{
    
}