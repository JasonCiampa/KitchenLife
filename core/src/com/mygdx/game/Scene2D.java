package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

// 

public abstract class Scene2D {
    
    // FIELDS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    protected static int totalScenes;                                                                           // Keeps track of how many scenes have been created.
    protected Texture backgroundImage;                                                                          // Background image to be drawn for the scene
    protected Music backgroundMusic;                                                                            // Background music to be played while the scene is active
    protected int x;                                                                                            // x-coordinate of the scene (px)
    protected int y;                                                                                            // y-coordinate of the scene (px)
    protected int width;                                                                                        // width of the scene (px)
    protected int height;                                                                                       // height of the scene (px)
    protected ArrayList<Button> buttons;                                                                        // an ArrayList of all buttons created in a scene    

    
    // CONSTRUCTOR // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    protected Scene2D(String pathToBackgroundImage, String pathToBackgroundMusic, int x, int y) {
        totalScenes++;                                                                                          // Increase total scene count by 1 since we're creating a new one
        this.backgroundImage = new Texture(Gdx.files.internal(pathToBackgroundImage));                          // Sets background image
        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(pathToBackgroundMusic));                   // Sets background music
        this.x = x;                                                                                             // Sets x-coordinate
        this.y = y;                                                                                             // Sets y-coordinate
        this.width = backgroundImage.getWidth();                                                                // Sets width to the width of the image
        this.height = backgroundImage.getHeight();                                                              // Sets height to the height of the image
        this.buttons = new ArrayList<Button>();                                                                 // Creates a new ArrayList to store any buttons that are created inside.
    }
  

    // ABSTRACT METHODS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Initializes the state of the Scene2D
    protected abstract void load();                                                                             // This code will vary for each Scene2D, so a Scene2D-specific function should be written and take the place of this one
    
    // Updates the state of the Scene2D
    protected abstract void update();                                                                           // This code will vary for each Scene2D, so a Scene2D-specific function should be written and take the place of this one
    
    // Draws the state of the Scene2D
    protected abstract void draw(SpriteBatch batch);                                                            // This code will vary for each Scene2D, so a Scene2D-specific function should be written and take the place of this one
    
    
    // METHODS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Plays the Scene2D's background music
    protected void playMusic() {
        this.backgroundMusic.setLooping(true);                                                               // Enables looping for the Scene2D's background music
        this.backgroundMusic.play();                                                                             // Begins to play the Scene2D's background music
        this.backgroundMusic.setVolume((float) 0.25);                                                            // Lowers the volume to 25% for the Scene2D's background music
    }
    
    // Stops the Scene2D's background music
    protected void stopMusic() {
        this.backgroundMusic.stop();                                                                            // Stop the background music of the Scene2D
    }
    
    // Creates a new Button object and adds it to the Scene2D's ArrayList of Buttons.
    protected void updateButtons() {
        for (Button button: this.buttons) {                                                                     // For each Button in titleScreen...
            button.update();                                                                                        // Update the Button
        }
    }
    
    // Draws all of the Buttons stored in the Scene2D on the screen
    protected void drawButtons(SpriteBatch batch) { 
        for (int i = 0; i < this.buttons.size(); i++) {                                                         // For every Button in the Scene2D...
            this.buttons.get(i).draw(batch);                                                                    // Draw the Button
        }
    }    

    
}

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//