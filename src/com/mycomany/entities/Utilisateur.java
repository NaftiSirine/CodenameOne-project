/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycomany.entities;

import com.codename1.ui.ComboBox;

/**
 *
 * @author Lenovo
 */
//taw n7oto fi description
public class Utilisateur {
   
   
    private int id;
    private String username;
    private String email;
    private String photo;
    private String nom;
    private String prenom;
    private String motdepasse;
    private String etat;
     private String[] roles;
         

    public Utilisateur(String username,String etat , String email,String [] roles , String photo, String nom, String prenom, String motdepasse) {
        this.username = username;
        this.email = email;
        this.photo = photo;
        this.nom = nom;
           this.etat = etat;
        this.prenom = prenom;
        this.motdepasse = motdepasse;
           this.roles = roles;
    }

    public Utilisateur() {
   
    }

    public Utilisateur(String toString, String toString0, int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
   
   

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }



    public Utilisateur(int id) {
        this.id = id;
    }



   
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "id=" + id + ", emaill=" + email  + '}';
    }

    public Utilisateur(int id, String email,String motdepasse) {
        this.id = id;
        this.motdepasse = motdepasse;
        this.email = email;

    }

    public Utilisateur(String email, String motdepasse) {
        this.motdepasse = motdepasse;
        this.email = email;

   
    }
   
   
   
   
}