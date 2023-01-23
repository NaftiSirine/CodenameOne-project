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
public class TypeAbonnement {
    private int id;
    private String type;
    private int id_ab;

    public TypeAbonnement() {
    }

    public TypeAbonnement(int id, String type) {
        this.id = id;
        this.type = type;
    }
    
    public TypeAbonnement(String type) {
        this.type = type;
    }
    
    public TypeAbonnement(String type,int id_ab) {
        this.type = type;
        this.id_ab = id_ab;
    }

    

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId_ab() {
        return id_ab;
    }

    public void setId_ab(int id_ab) {
        this.id_ab = id_ab;
    }
    
    
    
}
