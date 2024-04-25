package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 *
 * @author Jason Ciampa
 */
public class Drawer implements Updatable {
    
    // FIELDS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private static Drawer instance;                                                                                                                                                                         // Singleton instance of the Drawer
    private SpriteBatch batch;                                                                                                                                                                              // SpriteBatch is used to draw 2D images.
    
    private BitmapFont font;                                                                                                                                                                                // Large for the Drawer
    private BitmapFont fontSmall;                                                                                                                                                                                // Small for the Drawer

    private boolean textActive;                                                                                                                                                                             // Whether or not the text is active
    private String text;                                                                                                                                                                                    // The text for the Drawer to display
    private GlyphLayout textDimensions;                                                                                                                                                                     // The dimensions of the text
    private int textX;                                                                                                                                                                                      // The x-coord of the text  
    private int textY;                                                                                                                                                                                      // The y-coord of the text
    private int textboxWidth;                                                                                                                                                                               // The width of the textbox 
    private int textboxHeight;                                                                                                                                                                              // The height of the textbox
    private float textTimer;                                                                                                                                                                                // A timer indicating how long the text should display for
    
    private Texture testImage;                                                                                                                                                                              // An image for the Drawer to display
        
    // CONSTRUCTOR // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private Drawer() {
        this.batch = new SpriteBatch();                                                                                                                                                                     // Initializes the SpriteBatch
        
        this.font = new BitmapFont(Gdx.files.internal("fonts/showcard_gothic_64px.fnt"));                                                                                                          // Initializes the font for writing text

        this.testImage = new Texture("images/background.jpg");                                                                                                                                  // Initializes the image
    }
    
    // METHODS // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Gets a reference to the Drawer instance
    public static Drawer getInstance() {
        if (instance == null) {                                                                                                                                                                             // If the Drawer instance hasn't been initiated yet...
            instance = new Drawer();                                                                                                                                                                            // Initialize a new Drawer object and store it as the one instance
        }
        
        return instance;                                                                                                                                                                                    // Return the single instance of the Drawer class
    }
    
    // Displays a text message onto the screen at the given coordinates for the given amount of time
    public void displayMessage(String message, int textX, int textY, int textboxWidth, int textboxHeight, float displayTime) {                
        this.text = message;                                                                                                                                                                                // Stores the text
        this.textDimensions = new GlyphLayout(this.font, message);                                                                                                                                 // Stores the dimensions of the text
        this.textX = textX;                     // 250                                                                                                                                                      // Stores the x-coordinate of the text
        this.textY = textY;                     // 100                                                                                                                                                      // Stores the y-coordinate of the text
        this.textboxWidth = textboxWidth;       // 1420                                                                                                                                                     // Stores the width of the textbox
        this.textboxHeight = textboxHeight;     // 200                                                                                                                                                      // Stores the height of the textbox
        this.textTimer = displayTime;                                                                                                                                                                       // Stores how long to display the text for
        this.textActive = true;                                                                                                                                                                             // Enables the text
    }
    
    
    // Draws the Player's UI (displays their eating speed and reputation on the screen)
    public void drawPlayerInfo() {

    }
    
    // Draw a little animation for when the Player has clicked on the screen
    public void drawClickAnimation() {
        
    }
    
    public void drawImage(String imagePath, int x, int y) {
        Texture image = new Texture(imagePath);
        this.batch.draw(image, x, y);
    }
    
    
    // Updatable Methods
   
    @Override
    public void update(float dt) {
        if (this.textActive) {                                                                                                                                                                              // If the text is currently active...
            if (this.textTimer > 0) {                                                                                                                                                                           // If the text timer is still running...
                this.textTimer -= dt;                                                                                                                                                                               // Decrement the text timer by dt
            }
            else {                                                                                                                                                                                              // Otherwise, the text timer must be done, so...
                this.textActive = false;                                                                                                                                                                            // Disable the text
            }
        }
    }
    
    @Override
    public void render() {
        if (this.textActive) {                                                                                                                                                                                  // If the text is currently active...
            this.font.setColor(Color.WHITE);
            this.font.draw(batch, this.text, (this.textX + (this.textboxWidth / 2) - (this.textDimensions.width / 2)) - 2f, (this.textY + this.textboxHeight / 2) + (this.textDimensions.height / 2));          // Draw the background white offset to the left 
            this.font.draw(batch, this.text, (this.textX + (this.textboxWidth / 2) - (this.textDimensions.width / 2)) + 2f, (this.textY + this.textboxHeight / 2) + (this.textDimensions.height / 2));          // Draw the background white offset to the right
            this.font.draw(batch, this.text, (this.textX + (this.textboxWidth / 2) - (this.textDimensions.width / 2)), (this.textY + this.textboxHeight / 2) + (this.textDimensions.height / 2) + 2f);          // Draw the background white offset upward
            this.font.draw(batch, this.text, (this.textX + (this.textboxWidth / 2) - (this.textDimensions.width / 2)), (this.textY + this.textboxHeight / 2) + (this.textDimensions.height / 2) - 2f);          // Draw the background white offset downward

            this.font.setColor(Color.BLACK);
            this.font.draw(batch, this.text, (this.textX + (this.textboxWidth / 2) - (this.textDimensions.width / 2)), (this.textY + this.textboxHeight / 2) + (this.textDimensions.height / 2));               // Draw the black text as the main message
        }
    }
    
    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
    }
    
    
    // Getters 
    public SpriteBatch getBatch() {
        return this.batch;
    }
    
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
}
