package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class Drawer {
    private static Drawer instance;
    private SpriteBatch batch;                                                                                              // SpriteBatch is used to draw 2D images.
    
    // USE THIS CLASS TO DRAW UI SCREEN, DISPLAY "PRESS E TO INTERACT", ETC.
    
    private Drawer() {
        batch = new SpriteBatch();
    }
    
    public static Drawer getInstance() {
        if (instance == null) {
            instance = new Drawer();
        }
        
        return instance;
    }
    
    // Draw the Player's UI (display their eating speed and reputation on the screen)
    public void drawPlayerInfo() {
//        this.batch.draw('Texture', 'X-Coord','Y-Coord', 'Width', 'Height');                                                          // Draw the Player Info UI onto the screen
    }
    
    public void displayMessage(String message) {
//        this.batch.draw('Texture', 'X-Coord','Y-Coord', 'Width', 'Height');                                                          // Draw the message onto the screen
    }
    
    public void dispose() {
        batch.dispose();
    }
    
    public SpriteBatch getBatch() {
        return this.batch;
    }
}
