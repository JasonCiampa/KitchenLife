package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 *
 * @author Jason Ciampa
 */
public class TrainingBooth extends Booth {
    
    // FIELDS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // According to google, 6.51 is the average number of clicks per second. Based on that number, I decided to award 1/6 of speedReward to the Player per click.
    // That means that if the player clicks at the average clicking speed of about 6 CPS, then they will earn two seconds worth of speed reward in only one second.
    
    private int speedRewardPerClick;                                                                                                                                        // The amount of speed to reward the Player when they click the mouse   
    
    // CONSTRUCTOR // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public TrainingBooth(float x, float y, float z, int speedReward) {
        super(x, y, z, speedReward);                                                                                                                                        // Call the Booth constructor
        this.speedRewardPerClick = this.speedReward / 6;                                                                                                                    // Set each mouse click to give 1/6 of the speedReward to the Player
    }
    
    // METHODS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Eating Run Methods
    
    @Override
    protected void startEatingRun() {
        this.eatingRunOccurring = true;                                                                                                                                     // Start the Training Run
        this.oneSecondTimer = 1;                                                                                                                                            // Initialize the one second timer
    }
    
    @Override
    protected void processEatingRun(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {                                                                                                                  // If the 'ESC' key was pressed...
            this.endEatingRun(true);                                                                                                                                   // End the eating run with player victory (training, player can't lose)
        }
        else {                                                                                                                                                              // Otherwise, the Player hasn't stopped training, so...
            if (this.oneSecondTimer > 0) {                                                                                                                                      // If the one second timer is still going...
                this.oneSecondTimer -= dt;                                                                                                                                          // Decrement the timer by dt
            }
            else {                                                                                                                                                              // Otherwise, the timer must be done, so...
                Player player = Player.getInstance();                                                                                                                               // Store a local reference to the Player

                this.oneSecondTimer = 1;                                                                                                                                            // Reset the timer to one second

                player.increaseEatingSpeed((int) speedReward + (int) player.applyBonusBites() * (int) (0.16f * speedReward));                                                         // Increase the Player's eating speed by the sum of the Booth's speedReward + the number of bonusBites (clicks) multiplied by 1/6 of the Booth's speedReward
            }

            Drawer drawer = Drawer.getInstance();                                                                                                                               // Store a local reference to the Drawer
            drawer.displayMessage("Press 'ESC' to exit the Training Booth", 250, 100, 1420, 200, 0.1f);                   // Display a message showing how to exit the Booth and stop training
            
            Player.getInstance().setLocation(-13, 17, 8.5f);                                                                                                              // Keep the Player sat at the Booth
        }
    }
    
    @Override
    protected void endEatingRun(boolean playerWon) {
        Player player = Player.getInstance();                                                                                                                               // Store a local reference to the Player
        
        player.setEating(false);                                                                                                                                      // Set the Player's eating state to false
        player.applyBonusBites();                                                                                                                                             // Get the Player's bonus bites (this is called so that the Player's bonus bites get reset, no leftovers)             
        player.setEating(false);                                                                                                                                      // Set the Player's eating state to false

        player.setLocation(-15, 25, 27);
        
        this.eatingRunOccurring = false;                                                                                                                                    // End the training run
    }
    
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
