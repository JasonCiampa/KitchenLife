package com.mygdx.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS //
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class GameUI extends Scene2D {
    
    // FIELDS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public boolean active;                                                                                                                                     // Whether or not the GameUI is currently active
    private boolean howToPlayActive;                                                                                                                           // Whether or not the How To Play graphic within the GameUI is active
    private float[] haltCoords;                                                                                                                                // Coordinates to reset the Player at if they try to move while the How To Play graphic is active
    public boolean musicPlaying;                                                                                                                               // Whether or not there is music playing in this Scene
    
    private Button howToPlay;                                                                                                                                  // A Button that will trigger the How To Play graphic's active state
    private Button goToNextRestaurant;                                                                                                                         // A Button that will trigger the transition from the currently active Restaurant to the next
        

    // CONSTRUCTOR // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public GameUI() {
        super("images/transparent.png", "music/Happy Mambo - Kirby's Return to Dreamland Deluxe.mp3", 0, 0);                // Calls the Scene2D constructor to build the framework for the GameUI
            
        this.active = false;                                                                                                                                  // Set the GameUI to be inactive
        this.howToPlayActive = false;                                                                                                                         // Set the How To Play graphic to be inactive

        this.howToPlay = new Button(Button.SHORT, 1390, 50, "How To Play", this) {                                               // Creates the How To Play Button
            @Override                                                                                                                                            // Overriding the abstract clickAction method from the Button class                                                                    
            public void clickAction() {                                                                                                                          
                Player player = Player.getInstance();                                                                                                            // Store a local reference to the Player                                                                                                        
                if (GameUI.this.howToPlayActive) {                                                                                                               // If the How To Play graphic is currently active...
                    GameUI.this.howToPlayActive = false;                                                                                                             // Disable it now that the Button has been pressed
                }                                                   
                else {                                                                                                                                           // Otherwise, the How To Play graphic is currently inactive, so...
                    GameUI.this.howToPlayActive = true;                                                                                                              // Enable the How to Play graphic now that the Button has been clicked
                    GameUI.this.haltCoords = new float [] {player.getX(), player.getY(), player.getZ()};                                                             // Set the halt coords to be the position that the Player was in when they clicked the Button
                }
            }
        };
        
        this.goToNextRestaurant = new Button(Button.SHORT, 50, 930, "Next Restaurant", this) {                                  // Creates the Go To Next Restaurant Button
            @Override                                                                                                                                           // Overriding the abstract clickAction method from the Button class                                                                    
            public void clickAction() {                                                                                                                       
                WorldsBestSpeedEaterSimulator.setCurrentRestaurant(new RestaurantTwo());                                                                        // Set the current Restaurant to RestaurantTwo
        
                Player player = Player.getInstance();                                                                                                           // Store a local reference to the Player
                player.setLocation(16, 25, -20);                                                                                                         // Set the Player's location to the spawn point of the Restaurant
                player.getCamera().getView().lookAt(50, 25, -20);                                                                                               // Set the Player to look at these specific coordinates 
                this.active = false;
            }
        };
        
        this.goToNextRestaurant.active = false;                                                                                                             // Disables the Go To Next Restaurant Button     
        this.goToNextRestaurant.activeFont = this.goToNextRestaurant.fontSmall;                                                                             // Sets the Button's active font to be the smaller font
        this.goToNextRestaurant.textDimensions = new GlyphLayout(this.goToNextRestaurant.activeFont, this.goToNextRestaurant.text);                         // Set the text dimensions to be based on the smaller font
    }
    

    // METHODS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Anything that should happen immediately as TitleScreen becomes active should go in load
    @Override
    protected void load() {
        this.playMusic();                                                                                                                                   // Play the GameUI's music
    }
    
    @Override
    protected void update() {
        this.updateButtons();                                                                                                                               // Update all of the GameUI's Buttons
        
        Restaurant currentRestaurant = WorldsBestSpeedEaterSimulator.getCurrentRestaurant();                                                                // Store a local reference to the currently active Restaurant
        if (!(currentRestaurant instanceof RestaurantTwo) && Player.getInstance().getEatingReputation() >= currentRestaurant.getReputationGoal()) {         // If the current restaurant is not the last level  Player's eating reputation is greater than or equal to the restaurant's required reputation goal...
            this.goToNextRestaurant.active = true;                                                                                                              // Enable the Button that allows the Player to advance to the next Restaurant
        }
    }
    
    @Override
    protected void draw(SpriteBatch batch) {
       if (!Player.getInstance().getEating()) {                                                                                                             // If the Player is not currently eating...
            this.drawButtons(batch);                                                                                                                           // Draw all of TitleScreen's Buttons onto the screen
       }
       
        Drawer drawer = Drawer.getInstance();                                                                                                               // Store a local reference to the Drawer

        drawer.drawImage("images/eatingSpeedBanner.png", 710, 925);                                                                             // Draw the eating speed banner onto the screen
        drawer.drawImage("images/reputationBanner.png", 970, 925);                                                                              // Draw the eating reputation banner onto the screen
        
        if (this.howToPlayActive) {                                                                                                                        // If the How To Play graphic is currently active...
            drawer.drawImage("images/howToPlay.jpg", 320, 180);                                                                                    // Draw the How To Play graphic
            Player.getInstance().setLocation(this.haltCoords[0], this.haltCoords[1], this.haltCoords[2]);                                                     // Set the Player's position to the location they were in when the Button was pressed
        }
    }
}
