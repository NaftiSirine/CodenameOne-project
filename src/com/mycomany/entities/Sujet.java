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
public class Sujet {
    
    
    private int id;
    private String sujet;
    
    public Sujet(){
        
    }

    public Sujet(String sujet) {
        this.sujet = sujet;
    }
    
    public Sujet(int id,String sujet) {
        this.id = id;
        this.sujet = sujet;
    }

    public String getSujet() {
        return sujet;
    }

    public int getId() {
        return id;
    }

    public void setSujet(String contenu) {
        this.sujet = contenu;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
