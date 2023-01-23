/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycomany.entities;

/**
 *
 * @author Moez
 */
public class Choix {
    
    private int id;
    private String contenu;
    private int etatchoix;
    private String stringetat;
    private boolean booletat; 
    
    
    

    public Choix() {
    }
    

    public Choix(int id, String contenu) {
        this.id = id;
        this.contenu = contenu;
    }
    
    
    public Choix(String contenu) {
        this.contenu = contenu;
    }

    public Choix(String contenu, int etatchoix) {
        this.id = id;
        this.contenu = contenu;
        this.etatchoix = etatchoix;
    }

    
   

    public int getEtatchoix() {
        return etatchoix;
    }

    public void setEtatchoix(int etatchoix) {
        this.etatchoix = etatchoix;
    }

    public String getContenu() {
        return contenu;
    }

    public int getId() {
        return id;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBooletat(boolean booletat) {
        this.booletat = booletat;
    }

    public boolean isBooletat() {
        return booletat;
    }

    public String getStringetat() {
        return stringetat;
    }

    public void setStringetat(String stringetat) {
        this.stringetat = stringetat;
    }
    
    
    
    
    
    
    
    
    
    
}
