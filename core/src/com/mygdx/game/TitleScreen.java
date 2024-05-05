package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Jason Ciampa
 */
public class TitleScreen extends Scene2D {
    
    // FIELDS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public boolean active;                                                                                                                              // Indicates whether or not the TitleScreen Scene2D is active (being updated and drawn)
        
    private Button playButton;                                                                                                                          // Stores the play Button which will start the game
    private Button quitButton;                                                                                                                          // Stores the quit Button which will exit the game
        
    
    // CONSTRUCTOR // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public TitleScreen() {
        super("images/titlescreen.png", "music/Nintendo 3DS Music - Mii Maker.mp3", 0, 0);                           // Calls the Scene2D constructor to build the framework for TitleScreen
            
        this.active = true;                                                                                                                             // Sets the TitleScreen to be active

        playButton = new Button(Button.LONG, (1920 / 2) - (1920 / 4), (1920 / 2) - (1920 / 4) - 50, "Play Game", this) {        // Creates a playButton that changes the Scene to casinoTable when clicked on
            @Override                                                                                                                                       // Overriding the abstract clickAction method from the Button class                                                                    
            public void clickAction() {
                TitleScreen.this.active = false;                                                                                                            // Disable the TitleScreen
                TitleScreen.this.stopMusic();                                                                                                               // Stop the TitleScreen music    
                WorldsBestSpeedEaterSimulator.getCurrentRestaurant().playMusic();
            }
        };                 
            
        quitButton = new Button(Button.LONG, (1920 / 2) - (1920 / 4), (1920 / 2) - (1920 / 4) - 250, "Quit Game", this) {       // Creates a quitButton
            @Override                                                                                                                                       // Overriding the abstract clickAction method from the Button class                                                                    
            public void clickAction() {                                                                                                                     
                Gdx.app.exit();                                                                                                                             // Quits the program, ends the application
            }
        };
    }
    

    // METHODS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Anything that should happen immediately as TitleScreen becomes active should go in load
    @Override
    protected void load() {
        this.playMusic();                                                                                                                             // Play the TitleScreen's music
    }
    
    @Override
    protected void update() {
        this.updateButtons();                                                                                                                         // Updates all of TitleScreen's Buttons
    }
    
    @Override
    protected void draw(SpriteBatch batch) {
        batch.draw(this.backgroundImage, this.x, this.y, this.width, this.height);                                                                   // Draws TitleScreen's background image onto the screen
        this.drawButtons(batch);                                                                                                                     // Draw all of TitleScreen's Buttons onto the screen
    }
    
}
