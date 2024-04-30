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
public class Drawer implements Updatable {
    
    // FIELDS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private static Drawer instance;                                                                                                                                                                         // Singleton instance of the Drawer
    private SpriteBatch batch;                                                                                                                                                                              // SpriteBatch is used to draw 2D images.
    
    private BitmapFont font;                                                                                                                                                                                // Large for the Drawer
    private BitmapFont fontSmall;                                                                                                                                                                                // Small for the Drawer

    private boolean textActive;                                                                                                                                                                             // Whether or not the text is active
    private String text;                                                                                                                                                                                    // The text for the Drawer to display
    private GlyphLayout fontTextDimensions;                                                                                                                                                                     // The dimensions of the text
    private GlyphLayout fontSmallTextDimensions;                                                                                                                                                                     // The dimensions of the text

    private ShapeRenderer shapeRenderer;
    
    private int textX;                                                                                                                                                                                      // The x-coord of the text  
    private int textY;                                                                                                                                                                                      // The y-coord of the text
    private int textboxWidth;                                                                                                                                                                               // The width of the textbox 
    private int textboxHeight;                                                                                                                                                                              // The height of the textbox
    private float textTimer;                                                                                                                                                                                // A timer indicating how long the text should display for
    
        
    // CONSTRUCTOR // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private Drawer() {
        this.batch = new SpriteBatch();                                                                                                                                                                     // Initializes the SpriteBatch
        this.shapeRenderer = new ShapeRenderer();
        
        this.font = new BitmapFont(Gdx.files.internal("fonts/showcard_gothic_64px.fnt"));                                                                                                          // Initializes the font for writing text
        this.fontSmall = new BitmapFont(Gdx.files.internal("fonts/showcard_gothic_48px.fnt"));                                                                                                          // Initializes the font for writing text
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
        this.fontTextDimensions = new GlyphLayout(this.font, message);                                                                                                                                 // Stores the dimensions of the text
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
    
    public void drawText(String text, float textX, float textY, float textboxWidth, float textboxHeight) {
        this.fontSmall.setColor(Color.BLACK);

        this.fontSmallTextDimensions = new GlyphLayout(this.fontSmall, text);                                                                                                                                 // Stores the dimensions of the text

        this.fontSmall.draw(batch, text, (textX + (textboxWidth / 2) - (this.fontSmallTextDimensions.width / 2)) - 2f, (textY + textboxHeight / 2) + (this.fontSmallTextDimensions.height / 2));          // Draw the background white offset to the left 
        this.fontSmall.draw(batch, text, (textX + (textboxWidth / 2) - (this.fontSmallTextDimensions.width / 2)) + 2f, (textY + textboxHeight / 2) + (this.fontSmallTextDimensions.height / 2));          // Draw the background white offset to the right
        this.fontSmall.draw(batch, text, (textX + (textboxWidth / 2) - (this.fontSmallTextDimensions.width / 2)), (textY + textboxHeight / 2) + (this.fontSmallTextDimensions.height / 2) + 2f);          // Draw the background white offset upward
        this.fontSmall.draw(batch, text, (textX + (textboxWidth / 2) - (this.fontSmallTextDimensions.width / 2)), (textY + textboxHeight / 2) + (this.fontSmallTextDimensions.height / 2) - 2f);          // Draw the background white offset downward

        this.fontSmall.setColor(Color.WHITE);
        this.fontSmall.draw(batch, text, (textX + (textboxWidth / 2) - (this.fontSmallTextDimensions.width / 2)), (textY + textboxHeight / 2) + (this.fontSmallTextDimensions.height / 2));               // Draw the black text as the main message 
    }
    
    // Draw the numbers 
    public void drawEatingStats() {
        Player player = Player.getInstance();
       
        this.drawText(Integer.toString((int) player.getEatingSpeed()), 810, 960, 115, 76); 
        this.drawText(Integer.toString((int) player.getEatingReputation()), 1070, 960, 115, 76); 
    }
    
    public void drawImage(String imagePath, int x, int y) {
        Texture image = new Texture(imagePath);
        this.batch.draw(image, x, y);
    }
    
    
    // Draws the progress bars of the Challenge Booth
    public void drawProgressBars() {
        
        ChallengeBooth challengeBooth = WorldsBestSpeedEaterSimulator.getCurrentRestaurant().getChallengeBooths()[0];
        
        for (ChallengeBooth booth : WorldsBestSpeedEaterSimulator.getCurrentRestaurant().getChallengeBooths()) {
            if (booth.eatingRunOccurring) {
                challengeBooth = booth;
                break;
            }
        }
        
        float percentageOfBitesCompleted = (float) challengeBooth.getBitesRemaining() / (float) challengeBooth.getBitesTotal();
        float percentageOfTimePassed = (float) challengeBooth.getTimeLeft() / (float) challengeBooth.getTimeLimit();
        
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        
        if (percentageOfBitesCompleted > 0.5) {
            this.shapeRenderer.setColor(Color.GREEN);
        }
        else if (percentageOfBitesCompleted > 0.25) {
            this.shapeRenderer.setColor(Color.YELLOW);
        }
        else {
            this.shapeRenderer.setColor(Color.RED);
        }
        
        
        this.shapeRenderer.rect(100 + 12, 60, 76, (percentageOfBitesCompleted * 868f) + 4);

        
        if (percentageOfTimePassed > 0.5) {
            this.shapeRenderer.setColor(Color.GREEN);
        }
        else if (percentageOfTimePassed > 0.25) {
            this.shapeRenderer.setColor(Color.YELLOW);
        }
        else {
            this.shapeRenderer.setColor(Color.RED);
        }
        
        this.shapeRenderer.rect(1720 + 12, 60, 76, (percentageOfTimePassed * 868f));

        
        this.drawImage("images/bitesProgressBar.png", 100, 50);
        this.drawImage("images/timerProgressBar.png", 1720, 50);
        this.drawImage("images/mouth.png", -50, -50);
        
        this.shapeRenderer.end();
        

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
            this.font.draw(batch, this.text, (this.textX + (this.textboxWidth / 2) - (this.fontTextDimensions.width / 2)) - 2f, (this.textY + this.textboxHeight / 2) + (this.fontTextDimensions.height / 2));          // Draw the background white offset to the left 
            this.font.draw(batch, this.text, (this.textX + (this.textboxWidth / 2) - (this.fontTextDimensions.width / 2)) + 2f, (this.textY + this.textboxHeight / 2) + (this.fontTextDimensions.height / 2));          // Draw the background white offset to the right
            this.font.draw(batch, this.text, (this.textX + (this.textboxWidth / 2) - (this.fontTextDimensions.width / 2)), (this.textY + this.textboxHeight / 2) + (this.fontTextDimensions.height / 2) + 2f);          // Draw the background white offset upward
            this.font.draw(batch, this.text, (this.textX + (this.textboxWidth / 2) - (this.fontTextDimensions.width / 2)), (this.textY + this.textboxHeight / 2) + (this.fontTextDimensions.height / 2) - 2f);          // Draw the background white offset downward

            this.font.setColor(Color.BLACK);
            this.font.draw(batch, this.text, (this.textX + (this.textboxWidth / 2) - (this.fontTextDimensions.width / 2)), (this.textY + this.textboxHeight / 2) + (this.fontTextDimensions.height / 2));               // Draw the black text as the main message
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
    
    public ShapeRenderer getShapeRenderer() {
        return this.shapeRenderer;
    }
    
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
}
