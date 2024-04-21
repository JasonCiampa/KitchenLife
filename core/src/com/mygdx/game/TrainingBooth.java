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
    
    public TrainingBooth(float x, float y, float z, int speedReward, Food food) {
        super(x, y, z, speedReward, food);                                                                                                                                        // Call the Booth constructor
        this.speedRewardPerClick = this.speedReward / 6;                                                                                                                    // Set each mouse click to give 1/6 of the speedReward to the Player
    }
    
    // METHODS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Eating Run Methods
    
    @Override
    protected void startEatingRun() {
        Player player = Player.getInstance();
        player.setCollisionDetection(false);                                                                                                               // Disable the Player's collision detection so that it doesn't accidentally interfere with the Player being sat down in the Booth
        
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
                
                float bonusBites = player.applyBonusBites() * (0.16f * speedReward);                                                                                                // Get the Player's bonus bites and multiply them by the speed multiplier (1/6 of speed reward)
                player.increaseEatingSpeed(speedReward + bonusBites);                                                                                                               // Increase the Player's eating speed by the speedReward per second and their bonus bites multiplier
            }

            Drawer drawer = Drawer.getInstance();                                                                                                                               // Store a local reference to the Drawer
            drawer.displayMessage("Press 'ESC' to exit the Training Booth", 250, 100, 1420, 200, 0.1f);                   // Display a message showing how to exit the Booth and stop training
        }        
    }
    
    @Override
    protected void endEatingRun(boolean playerWon) {
        Player player = Player.getInstance();                                                                                                                               // Store a local reference to the Player
        
        player.setEating(false);                                                                                                                                      // Set the Player's eating state to false
        player.setLocation(this.x - (this.length / 3), 25, this.z + (this.width / 2) + 10);                                                                               // Stand the Player up just to the side of the Booth
        player.setCollisionDetection(true);                                                                                                                // Enable the Player's collision detection now that they're no longer seated                                         
        player.applyBonusBites();                                                                                                                                             // Get the Player's bonus bites (this is called so that the Player's bonus bites get reset, no leftovers)             

        this.eatingRunOccurring = false;                                                                                                                                    // End the training run
    }
    
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
