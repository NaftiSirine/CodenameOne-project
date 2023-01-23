/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycomany.entities;

import java.util.List;

/**
 *
 * @author Moez
 */
public class Question {
      
    
        
    private int id;
    private String contenu;
    private int id_c;
    
    private List<Choix> choices;
    
    private Choix choix1;
    private Choix choix2;
    private Choix choix3;
    
    
    
    
    public Question(){
        
    }

    public Question(String contenu) {
        this.contenu = contenu;
    }
    
    public Question(int id,String contenu) {
        this.id = id;
        this.contenu = contenu;
    }

    public Question(int id, String contenu, int id_c) {
        this.id = id;
        this.contenu = contenu;
        this.id_c = id_c;
    }
    
    public Question(String contenu, int id_c) {
      
        this.contenu = contenu;
        this.id_c = id_c;
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

    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }

    public Choix getChoix1() {
        return choix1;
    }

    public Choix getChoix2() {
        return choix2;
    }

    public Choix getChoix3() {
        return choix3;
    }

    public void setChoix1(Choix choix1) {
        this.choix1 = choix1;
    }

    public void setChoix2(Choix choix2) {
        this.choix2 = choix2;
    }

    public void setChoix3(Choix choix3) {
        this.choix3 = choix3;
    }

    public List<Choix> getChoices() {
        return choices;
    }

    public void setChoices(List<Choix> choices) {
        this.choices = choices;
    }
    
    


    

    
    
}
