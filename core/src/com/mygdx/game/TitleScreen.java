package com.mygdx.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS //
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class TitleScreen extends Scene2D {
    
    // FIELDS // 
    
    // STATE
    public boolean active;
    
    // IMAGES
    private Texture gameLogo;                                                                                                                                                      // Stores the Killer Logo to be drawn on the screen
    
    // BUTTONS
    private Button playButton;                                                                                                                                            // Stores the play Button which will start the game
    private Button quitButton;                                                                                                                                                       // Stores the quit Button which will exit the game
        
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // CONSTRUCTOR //
    public TitleScreen() {
        super("images/background.jpg", "music/Nintendo 3DS Music - Mii Maker.mp3", 0, 0);                                              // Calls the Scene's constructor to build the framework for TitleScreen
        
//        gameLogo = new Texture("images/main_menu/killer_logo.png");                                                                                                                // Stores the gameLogo texture
    
        this.active = true;

        playButton = new Button(Button.LONG, (1920 / 2) - (1920 / 4), (1920 / 2) - (1920 / 4) - 50, "Play Game", this) {                   // Creates a playButton that changes the Scene to casinoTable when clicked on
            @Override
            public void clickAction() {
                TitleScreen.this.active = false;
            }
        };                 
            
        quitButton = new Button(Button.LONG, (1920 / 2) - (1920 / 4), (1920 / 2) - (1920 / 4) - 250, "Quit Game", this) {                                // Creates a quitButton
            @Override
            public void clickAction() {                                                                                                                                              // Overriding the abstract clickAction method from the Button class                                                                    
                Gdx.app.exit();                                                                                                                                                      // Quits the program, ends the application
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
    }
    
    @Override
    protected void draw(SpriteBatch batch) {
        batch.draw(this.backgroundImage, this.x, this.y, this.width, this.height);                                                                                                    // Draws TitleScreen's background image onto the screen
//        batch.draw(gameLogo, this.x, this.y);                                                                                                                                       // Draws the gameLogo onto the screen
        this.drawButtons(batch);                                                                                                                                                      // Draw all of TitleScreen's Buttons onto the screen
    }
    
}
