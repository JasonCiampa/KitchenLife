package com.mygdx.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS //
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class GameUI extends Scene2D {
    
    // FIELDS // 
    
    // STATE
    public boolean active;
    private boolean howToPlayActive;
    private float[] haltCoords;
    
    // IMAGES
    private Texture gameLogo;                                                                                                                                                      // Stores the Killer Logo to be drawn on the screen
    
    // BUTTONS
    private Button howToPlay;                                                                                                                                            // Stores the play Button which will start the game
        
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // CONSTRUCTOR //
    public GameUI() {
        super("images/transparent.png", "music/Nintendo 3DS Music - Mii Maker.mp3", 0, 0);                                              // Calls the Scene's constructor to build the framework for TitleScreen
        
//        gameLogo = new Texture("images/main_menu/killer_logo.png");                                                                                                                // Stores the gameLogo texture
    
        this.active = false;             
        this.howToPlayActive = false;

        this.howToPlay = new Button(Button.SHORT, 1390, 50, "How To Play", this) {                                // Creates a quitButton
            @Override
            public void clickAction() {                                                                                                                                               // Overriding the abstract clickAction method from the Button class                                                                    
                Player player = Player.getInstance();
                if (GameUI.this.howToPlayActive) {
                    GameUI.this.howToPlayActive = false;
                }
                else {
                    GameUI.this.howToPlayActive = true;
                    GameUI.this.haltCoords = new float [] {player.getX(), player.getY(), player.getZ()};   
                }
            }
        };
    }
    
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // METHODS //
    
    // Anything that should happen immediately as TitleScreen becomes active should go in load
    @Override
    protected void load() {
        
    }
    
    @Override
    protected void update() {
        this.updateButtons();                                                                                                                                                         // Updates all of TitleScreen's Buttons
        Drawer drawer = Drawer.getInstance();
        
        if (this.howToPlayActive) {
            drawer.drawImage("images/howToPlay.jpg", 320, 180);                                                                                                                                                  // Quits the program, ends the application
            Player.getInstance().setLocation(this.haltCoords[0], this.haltCoords[1], this.haltCoords[2]);
        }
        
        drawer.drawImage("images/eatingSpeedBanner.png", 710, 925);
        drawer.drawImage("images/reputationBanner.png", 970, 925);
    }
    
    @Override
    protected void draw(SpriteBatch batch) {
//        batch.draw(gameLogo, this.x, this.y);                                                                                                                                       // Draws the gameLogo onto the screen
       if (!Player.getInstance().getEating()) {
            this.drawButtons(batch);                                                                                                                                                      // Draw all of TitleScreen's Buttons onto the screen
       }
    }
}
