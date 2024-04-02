package com.mygdx.game;

import java.util.ArrayList;

/**
 *
 * @author Jason Ciampa
 */
public class Restaurant {
    private ArrayList<Interactable> booths;
    
    public static Restaurant instance;
    
    private Restaurant() {
        
    }
    
    public static Restaurant getInstance() {
        if (instance == null) {
            instance = new Restaurant();
        }
        
        return instance;
    }
    
    public ArrayList<Interactable> getBooths() {
        return this.booths;
    }
}
