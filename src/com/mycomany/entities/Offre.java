/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycomany.entities;

/**
 *
 * @author nidha
 */
public class Offre {
    private int id;
    private String titre,secteur,description,localisation;
    private String salaire;

    public Offre(int id, String titre, String secteur, String description, String localisation, String salaire) {
        this.id = id;
        this.titre = titre;
        this.secteur = secteur;
        this.description = description;
        this.localisation = localisation;
        this.salaire = salaire;
    }
    
    public Offre(String titre) {
        this.titre = titre;
    }
    
      public Offre(String titre, String secteur, String description, String localisation, String salaire) {
       
        this.titre = titre;
        this.secteur = secteur;
        this.description = description;
        this.localisation = localisation;
        this.salaire = salaire;
    }

    public Offre() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getSalaire() {
        return salaire;
    }

    public void setSalaire(String salaire) {
        this.salaire = salaire;
    }

    

    
    
    
}
