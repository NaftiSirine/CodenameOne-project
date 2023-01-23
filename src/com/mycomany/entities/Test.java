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
public class Test {
    
    private int id;
    private String titre;
    private List<Question> questions;
    int note = 0;
    

    public Test() {
    }

    public Test(int id, String titre) {
        this.id = id;
        this.titre = titre;
    }
    
    public Test(String titre) {
        this.titre = titre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }


    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
    
    public void increment_note()
    {
        note++;
    }

    public int getNote() {
        return note;
    }
    
    
    
    
    
    
    
    
    
}
