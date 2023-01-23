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
public class Reservation {
    
    private int id;
    private int eve_id;
    private int nbplace;

    public Reservation() {
    }

    public Reservation(int eve_id, int nbplace) {
        this.eve_id = eve_id;
        this.nbplace = nbplace;
    }

    public Reservation(int id, int eve_id, int nbplace) {
        this.id = id;
        this.eve_id = eve_id;
        this.nbplace = nbplace;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEve_id() {
        return eve_id;
    }

    public void setEve_id(int eve_id) {
        this.eve_id = eve_id;
    }

    public int getNbplace() {
        return nbplace;
    }

    public void setNbplace(int nbplace) {
        this.nbplace = nbplace;
    }
    
}
