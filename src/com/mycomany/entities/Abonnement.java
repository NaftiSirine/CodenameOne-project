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
public class Abonnement {
    
    
    private int id;
    private String nom;
    private String description;
    private int cout;
    private int id_type;

    public Abonnement() {
    }

    
    
    public Abonnement(int id, String nom, String description, int cout) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.cout = cout;
    }
    
    
    public Abonnement(String nom, String description, int cout) {
        this.nom = nom;
        this.description = description;
        this.cout = cout;
        
    }
    
    public Abonnement(String nom, String description, int cout,int id_type) {
        this.nom = nom;
        this.description = description;
        this.cout = cout;
        this.id_type = id_type;
        
    }
    

    public Abonnement(String toString) {
        
    }

    public int getCout() {
        return cout;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

   

    public void setCout(int cout) {
        this.cout = cout;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }
    
    
}
