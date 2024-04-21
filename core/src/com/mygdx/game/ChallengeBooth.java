package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 *
 * @author Jason Ciampa
 */
public class ChallengeBooth extends Booth {
    
    // FIELDS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    private int reputationReward;                                                                                                                                                                                // How much eating reputation to award the Player       (just a number, the higher the better)
    private double timeLimit;                                                                                                                                                                                    // How much time the player has to eat their meal       (measured in seconds)
    private double timeLeft;                                                                                                                                                                                   // How much time the player has left to eat their meal  (measured in seconds)
    private int bitesRemaining;                                                                                                                                                                                // How many bites are left for Player to eat            (just a number, once its zero the player wins)
    private int bitesTotal;                                                                                                                                                                                    // How many bites there are for the Player initially    (just a number, the higher the bites total the harder the restaurant record run is)
    private boolean recordBroken;
    
    // CONSTRUCTOR // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public ChallengeBooth(float x, float y, float z, int speedReward, int reputationReward, int timeLimit, int bitesTotal, boolean recordBroken) {
        super(x, y, z, speedReward);
        
        this.reputationReward = reputationReward;                                                                                                                                                                // Call the Booth constructor to store the given x, y, z, and speedReward values
        
        this.timeLimit = timeLimit;                                                                                                                                                                              // Store the timeLimit for this ChallengeBooth
        this.timeLeft = this.timeLimit;                                                                                                                                                                          // Set the timeLeft to complete the challenge to timeLimit by default

        this.bitesTotal = bitesTotal;                                                                                                                                                                            // Store the number of bitesTotal it takes to finish the meal at this ChallengeBooth
        this.bitesRemaining = this.bitesTotal;                                                                                                                                                                   // Set the number of bitesRemaining to finish the challenge's meal to bitesTotal by default
        
        this.recordBroken = recordBroken;                                                                                                                                                                        // Stores whether or not this Challenge Booth's record has been broken
    }
    
    
    public ChallengeBooth(float x, float y, float z, int speedReward, int reputationReward, int timeLimit, int bitesTotal, boolean recordBroken, Food food) {
        super(x, y, z, speedReward, food);
        
        this.reputationReward = reputationReward;                                                                                                                                                                // Call the Booth constructor to store the given x, y, z, and speedReward values
        
        this.timeLimit = timeLimit;                                                                                                                                                                              // Store the timeLimit for this ChallengeBooth
        this.timeLeft = this.timeLimit;                                                                                                                                                                          // Set the timeLeft to complete the challenge to timeLimit by default

        this.bitesTotal = bitesTotal;                                                                                                                                                                            // Store the number of bitesTotal it takes to finish the meal at this ChallengeBooth
        this.bitesRemaining = this.bitesTotal;                                                                                                                                                                   // Set the number of bitesRemaining to finish the challenge's meal to bitesTotal by default
        
        this.recordBroken = recordBroken;                                                                                                                                                                        // Stores whether or not this Challenge Booth's record has been broken
    }
    
    // METHODS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Eating Run Methods
    
    @Override
    protected void startEatingRun() {
        Player player = Player.getInstance();                                                                                                                                                                    // Store a local reference to the Player
        player.setCollisionDetection(false);                                                                                                                                                    // Disable the Player's collision detection so that it doesn't accidentally interfere with the Player being sat down in the Booth
        
        this.eatingRunOccurring = true;                                                                                                                                                                          // Start the eating run
        this.oneSecondTimer = 1;                                                                                                                                                                                 // Set the one second timer to one second
        
        this.timeLeft = this.timeLimit;                                                                                                                                                                          // Set the amount of time left to timeLimit
        this.bitesRemaining = this.bitesTotal;                                                                                                                                                                   // Set the amount of bites left to bitesTotal
    }
    
    
    @Override
    protected void processEatingRun(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {                                                                                                                                                       // If the 'ESC' key was pressed...
            this.endEatingRun(false);                                                                                                                                                                        // End the eating run with player victory (training, player can't lose)
        }
        else {                                                                                                                                                                                                   // Otherwise...
            if (this.oneSecondTimer > 0) {                                                                                                                                                                           // If the one second timer is still going...
                this.oneSecondTimer -= dt;                                                                                                                                                                               // Decrement the timer by dt
            }
            else {                                                                                                                                                                                                   // Otherwise, the timer must be done, so...
                Player player = Player.getInstance();                                                                                                                                                                    // Store a local reference to the Player

                this.oneSecondTimer = 1;                                                                                                                                                                                 // Reset the timer to one second
                                
                float bonusBites = player.applyBonusBites() * (0.1f * player.getEatingSpeed());                                                                                                                         // Get the Player's bonus bites and multiply them by the speed multiplier (1/10 of player speed)                
                this.bitesRemaining -= (player.getEatingSpeed() + bonusBites);                                                                                                                                         // Decrement the # of bitesRemaining by the calculated number of bites taken this past second  (Not sure if this math will make it balanced in-game, but we'll test and adjust)
            }

            if (this.bitesRemaining <= 0) {                                                                                                                                                                          // If there are no bites remaining...
                this.endEatingRun(true);                                                                                                                                                                        // End this eating run with the Player's victory  
                return;                                                                                                                                                                                                  // Return now that the eating run is over
            }
            
            if (this.timeLeft <= 0) {                                                                                                                                                                           // Otherwise, if there's no time left...
                this.endEatingRun(false);                                                                                                                                                                       // End this eating run with the Player's defeat
                return;                                                                                                                                                                                                  // Return now that the eating run is over
            }
            else {
                this.timeLeft -= dt;
            }
            
            Drawer drawer = Drawer.getInstance();                                                                                                                                                                   // Store a local reference to the Drawer
            drawer.displayMessage("Press 'ESC' to exit the Challenge Booth", 250, 100, 1420, 200, 0.1f);                                                       // Display a message showing how to exit the Booth and stop training
        }
        
        System.out.println("Time Limit: " + this.timeLimit);
        System.out.println("Time Left: " + this.timeLeft);
        System.out.println("Bites Remaining: " + this.bitesRemaining);

        
    }
    
    @Override
    protected void endEatingRun(boolean playerWon) {
        Player player = Player.getInstance();                                                                                                                                                                    // Store a local reference to the Player
        Drawer drawer = Drawer.getInstance();
        
        player.setEating(false);                                                                                                                                                                           // Set the Player's eating state to false
        player.setLocation(this.x - (this.length / 3), 25, this.z - (this.width / 2) - 10);                                                                                                                    // Stand the Player up just to the side of the Booth
        player.setCollisionDetection(true);                                                                                                                                                     // Enable the Player's collision detection now that they're no longer seated                                         
        player.applyBonusBites();                                                                                                                                                                                // Get the Player's bonus bites (this is called so that the Player's bonus bites get reset, no leftovers)
        
        this.eatingRunOccurring = false;                                                                                                                                                                         // End the eating run
        
        if (playerWon) {                                                                                                                                                                                         // If the Player was victorious...
            player.increaseEatingSpeed(this.speedReward);                                                                                                                                            // Increase their eatingSpeed by speedReward
            player.increaseEatingReputation(this.reputationReward);                                                                                                                              // Increase their eatingReputation by reputationReward
            drawer.displayMessage("You completed the challenge!", 250, 100, 1420, 200, 2.5f);     // Display a message saying that the Player won
        }
        else {                                                                                                                                                                                                   // Otherwise, the Player must have lost, so...
            drawer.displayMessage("You failed the challenge!", 250, 100, 1420, 200, 2.5f);              // Display a message saying that the Player lost
        }
    }
    
    
    // Getters
    
    public int getReputationReward() {
        return this.reputationReward;
    }
    
    public double getTimeLimit() {
        return this.timeLimit;
    }
    
    public int getBitesRemaining() {
        return this.bitesRemaining;
    }
    
    public int getBitesTotal() {
        return this.bitesTotal;
    }  
    
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
