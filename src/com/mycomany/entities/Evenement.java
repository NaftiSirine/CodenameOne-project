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
public class Evenement {
    
    private int id;
    private String titre;
    private String description;
    private String localisation;
    
    
    public Evenement(){
        
    }
    public Evenement(String titre,String description,String localisation) {
       
        this.titre = titre;
        this.description = description;
        this.localisation = localisation;
    }
    
        public Evenement(int id,String titre,String description,String image,String localisation ) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.localisation = localisation;
    }

    public int getId() {
        return this.id;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocalisation() {
        return this.localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    
    
    
    
}
