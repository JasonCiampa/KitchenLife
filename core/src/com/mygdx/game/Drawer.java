package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


/**
 *
 * @author Jason Ciampa
 */
public class Drawer  {
    
    // FIELDS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private static Drawer instance;                                                                                                                                                                         // Singleton instance of the Drawer
    private SpriteBatch batch;                                                                                                                                                                              // SpriteBatch is used to draw 2D images.
    
    private BitmapFont font;                                                                                                                                                                                // Normal font for the Drawer
    private BitmapFont fontSmall;                                                                                                                                                                           // Small font for the Drawer

    private boolean textActive;                                                                                                                                                                             // Whether or not the text is active
    private String text;                                                                                                                                                                                    // The text for the Drawer to display
    private GlyphLayout fontTextDimensions;                                                                                                                                                                 // The dimensions of the text when using normal font
    private GlyphLayout fontSmallTextDimensions;                                                                                                                                                            // The dimensions of the text when using small font

    private ShapeRenderer shapeRenderer;                                                                                                                                                                    // ShapeRenderer is used to draw shapes
    
    private int textX;                                                                                                                                                                                      // The x-coord of the text  
    private int textY;                                                                                                                                                                                      // The y-coord of the text
    private int textboxWidth;                                                                                                                                                                               // The width of the textbox 
    private int textboxHeight;                                                                                                                                                                              // The height of the textbox
    private float textTimer;                                                                                                                                                                                // A timer indicating how long the text should display for
    
        
    // CONSTRUCTOR // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private Drawer() {
        this.batch = new SpriteBatch();                                                                                                                                                                     // Initializes the SpriteBatch
        this.shapeRenderer = new ShapeRenderer();                                                                                                                                                           // Initialize the ShapeRenderer
        
        this.font = new BitmapFont(Gdx.files.internal("fonts/showcard_gothic_64px.fnt"));                                                                                                                  // Initialize the normal font for writing text
        this.fontSmall = new BitmapFont(Gdx.files.internal("fonts/showcard_gothic_48px.fnt"));                                                                                                             // Initialize the small font for writing text
    }
    
    
    // METHODS // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Displays a text message onto the screen at the given coordinates for the given amount of time
    public void displayMessage(String message, int textX, int textY, int textboxWidth, int textboxHeight, float displayTime) {                
        this.text = message;                                                                                                                                                                                // Stores the text
        this.fontTextDimensions = new GlyphLayout(this.font, message);                                                                                                                                      // Stores the dimensions of the text
        this.textX = textX;                                                                                                                                                                                 // Stores the x-coordinate of the text
        this.textY = textY;                                                                                                                                                                                 // Stores the y-coordinate of the text
        this.textboxWidth = textboxWidth;                                                                                                                                                                   // Stores the width of the textbox
        this.textboxHeight = textboxHeight;                                                                                                                                                                 // Stores the height of the textbox
        this.textTimer = displayTime;                                                                                                                                                                       // Stores how long to display the text for
        this.textActive = true;                                                                                                                                                                             // Enables the text
    }

    public void drawText(String text, float textX, float textY, float textboxWidth, float textboxHeight) {
        this.fontSmall.setColor(Color.BLACK);                                                                                                                                                               // Set the font color to black

        this.fontSmallTextDimensions = new GlyphLayout(this.fontSmall, text);                                                                                                                               // Stores the dimensions of the text

        this.fontSmall.draw(batch, text, (textX + (textboxWidth / 2) - (this.fontSmallTextDimensions.width / 2)) - 2f, (textY + textboxHeight / 2) + (this.fontSmallTextDimensions.height / 2));           // Draw the background white offset to the left 
        this.fontSmall.draw(batch, text, (textX + (textboxWidth / 2) - (this.fontSmallTextDimensions.width / 2)) + 2f, (textY + textboxHeight / 2) + (this.fontSmallTextDimensions.height / 2));           // Draw the background white offset to the right
        this.fontSmall.draw(batch, text, (textX + (textboxWidth / 2) - (this.fontSmallTextDimensions.width / 2)), (textY + textboxHeight / 2) + (this.fontSmallTextDimensions.height / 2) + 2f);           // Draw the background white offset upward
        this.fontSmall.draw(batch, text, (textX + (textboxWidth / 2) - (this.fontSmallTextDimensions.width / 2)), (textY + textboxHeight / 2) + (this.fontSmallTextDimensions.height / 2) - 2f);           // Draw the background white offset downward

        this.fontSmall.setColor(Color.WHITE);
        this.fontSmall.draw(batch, text, (textX + (textboxWidth / 2) - (this.fontSmallTextDimensions.width / 2)), (textY + textboxHeight / 2) + (this.fontSmallTextDimensions.height / 2));                // Draw the black text as the main message 
    }
    
    // Draw the Player's eating stats (speed and reputation values)
    public void drawEatingStats() {
        Player player = Player.getInstance();                                                                                                                                                               // Fetch and store a local reference to the Player
        
        int eatingSpeed = (int) player.getEatingSpeed();                                                                                                                                                    // Store the integer form of the Player's eating speed
        int eatingReputation = (int) player.getEatingReputation();                                                                                                                                          // Store the integer form of the Player's eating reputation
        
        String stringEatingSpeed = Integer.toString(eatingSpeed);                                                                                                                                         // Convert the integer form of the Player's eating speed into a String
        String stringEatingReputation = Integer.toString(eatingReputation);                                                                                                                               // Convert the integer form of the Player's eating reputation into a String
        
        this.drawText(stringEatingSpeed, 810, 960, 115, 76);                                                                                                       // Draw the eating speed value onto the screen
        this.drawText(stringEatingReputation, 1070, 960, 115, 76);                                                                                                 // Draw the eating reputation value onto the screen
    }
    
    // Draws a given image onto the screen at the given coordinates
    public void drawImage(String imagePath, int x, int y) {
        Texture image = new Texture(imagePath);                                                                                                                                                             // Create a texture using the given image path
        this.batch.draw(image, x, y);                                                                                                                                                                       // Draw the image onto the screen at the given x and y coordinates
    }
    
    private void determineProgressBarColor(float percentRemaining) {
        if (percentRemaining > 0.5) {                                                                                                                                                                       // If the progress bar has more than half way to go until completion...
            this.shapeRenderer.setColor(Color.GREEN);                                                                                                                                                          // Set the ShapeRender's drawing color to green
        }
        else if (percentRemaining > 0.25) {                                                                                                                                                                 // Otherwise, if the progress bar has more than a quarter of the way to go until completion...
            this.shapeRenderer.setColor(Color.YELLOW);                                                                                                                                                         // Set the ShapeRenderer's drawing color to yellow
        }
        else {                                                                                                                                                                                             // Otherwise, the progress bar must have less than a quarter of the way to go until completion, so...
            this.shapeRenderer.setColor(Color.RED);                                                                                                                                                            // Set the ShapeRenderer's drawing color to red
        }
    }
    
    // Draws the progress bars of the Challenge Booth onto the screen
    public void drawProgressBars() {
        
        ChallengeBooth challengeBooth = WorldsBestSpeedEaterSimulator.getCurrentRestaurant().getChallengeBooths()[0];                                                                                       // Store a local reference to the first ChallengeBooth
        
        for (ChallengeBooth booth : WorldsBestSpeedEaterSimulator.getCurrentRestaurant().getChallengeBooths()) {                                                                                            // For every ChallengeBooth in the currently active Restaurant...
            if (booth.eatingRunOccurring) {                                                                                                                                                                     // If the ChallengeBooth has an eating run occurring...
                challengeBooth = booth;                                                                                                                                                                              // Store this ChallengeBooth as our local reference because the Player is currently eating there
                break;                                                                                                                                                                                               // Break out of the loop because the Player can only be eating in one booth at once
            }
        }
        
        float percentageOfBitesRemaining = (float) challengeBooth.getBitesRemaining() / (float) challengeBooth.getBitesTotal();                                                                            // Calculate the percentage of bites that have been taken from this meal
        float percentageOfTimePassed = (float) challengeBooth.getTimeLeft() / (float) challengeBooth.getTimeLimit();                                                                                       // Calculate the amount of time that has passed since the challenge began
        
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);                                                                                                                                          // Start the ShapeRenderer so that it is prepared to draw a filled shape
        
        this.determineProgressBarColor(percentageOfBitesRemaining);                                                                                                                            // Set the color of the progress bar based on how many bites are left in the meal
        this.shapeRenderer.rect(100 + 12, 60, 76, (percentageOfBitesRemaining * 868f) + 4);                                                                                                                // Draw a rectangle to represent the Bites Progress Bar

        
        this.determineProgressBarColor(percentageOfBitesRemaining);                                                                                                                            // Set the color of the progress bar based on how much time is left to finish the meal
        this.shapeRenderer.rect(1720 + 12, 60, 76, (percentageOfTimePassed * 868f));                                                                                                                       // Draw a rectangle to represent the Time Progress Bar
         
        this.drawImage("images/bitesProgressBar.png", 100, 50);                                                                                                                                 // Draw the border of the bites progress bar
        this.drawImage("images/timerProgressBar.png", 1720, 50);                                                                                                                                // Draw the border of the time progress bar
        this.drawImage("images/mouth.png", -50, -50);                                                                                                                                           // Draw a mouth off-screen because the ShapeRenderer is weird and makes the last image drawn all white
        
        this.shapeRenderer.end();                                                                                                                                                                          // Stop the ShapeRenderer now that the progress bars are all drawn
    }
    
    
    // STATE HANDLING METHODS // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   
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
    
    public void render() {
        if (this.textActive) {                                                                                                                                                                                  // If the text is currently active...
            this.font.setColor(Color.WHITE);                                                                                                                                                                       // Set the font color to white
            this.font.draw(batch, this.text, (this.textX + (this.textboxWidth / 2) - (this.fontTextDimensions.width / 2)) - 2f, (this.textY + this.textboxHeight / 2) + (this.fontTextDimensions.height / 2));     // Draw the background white offset to the left 
            this.font.draw(batch, this.text, (this.textX + (this.textboxWidth / 2) - (this.fontTextDimensions.width / 2)) + 2f, (this.textY + this.textboxHeight / 2) + (this.fontTextDimensions.height / 2));     // Draw the background white offset to the right
            this.font.draw(batch, this.text, (this.textX + (this.textboxWidth / 2) - (this.fontTextDimensions.width / 2)), (this.textY + this.textboxHeight / 2) + (this.fontTextDimensions.height / 2) + 2f);     // Draw the background white offset upward
            this.font.draw(batch, this.text, (this.textX + (this.textboxWidth / 2) - (this.fontTextDimensions.width / 2)), (this.textY + this.textboxHeight / 2) + (this.fontTextDimensions.height / 2) - 2f);     // Draw the background white offset downward

            this.font.setColor(Color.BLACK);                                                                                                                                                                       // Set the font color to white
            this.font.draw(batch, this.text, (this.textX + (this.textboxWidth / 2) - (this.fontTextDimensions.width / 2)), (this.textY + this.textboxHeight / 2) + (this.fontTextDimensions.height / 2));          // Draw the black text as the main message
        }
    }
    
    
    
    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
        this.fontSmall.dispose();
    }
    
    
    // GETTER METHODS // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public SpriteBatch getBatch() {
        return this.batch;
    }
    
    public ShapeRenderer getShapeRenderer() {
        return this.shapeRenderer;
    }
    
    // Gets a reference to the Drawer instance
    public static Drawer getInstance() {
        if (instance == null) {                                                                                                                                                                             // If the Drawer instance hasn't been initiated yet...
            instance = new Drawer();                                                                                                                                                                            // Initialize a new Drawer object and store it as the one instance
        }
        
        return instance;                                                                                                                                                                                    // Return the single instance of the Drawer class
    }
    
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
}
