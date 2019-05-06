/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.tqs.homework.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CacheStats {
    @Id
    private String name;
    private int nPedidos;
    private int hits;
    private int misses;

    public CacheStats(String name) {
        this.name = name;
        this.nPedidos = 0;
        this.hits = 0;
        this.misses = 0;
    }
    
    public CacheStats(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getnPedidos() {
        return nPedidos;
    }

    public void setnPedidos(int nPedidos) {
        this.nPedidos = nPedidos;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hit) {
        this.hits = hit;
    }

    public int getMisses() {
        return misses;
    }

    public void setMisses(int misses) {
        this.misses = misses;
    }  
}
