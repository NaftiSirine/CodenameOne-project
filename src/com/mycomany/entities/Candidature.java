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
public class Candidature {
    
   private int id;
    private String Message;

    public Candidature(int id, String Message) {
        this.id = id;
        this.Message = Message;
    }
    
    public Candidature(String Message) {
        
        this.Message = Message;
    }


    public Candidature() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    @Override
    public String toString() {
        return "Candidature{" + "id=" + id + ", Message=" + Message + '}';
    }
    
    
}
