package com.mygdx.game;

/**
 *
 * @author Jason Ciampa
 */
public class ChallengeBooth extends Booth {
    private int reputationReward;                                                                                                   // How much eating reputation to award the Player       (just a number, the higher the better)
    private double timeLimit;                                                                                                       // How much time the player has to eat their meal       (measured in seconds)
    protected double timeLeft;                                                                                                      // How much time the player currently has left to eat their meal (measured in seconds)
    protected int bitesRemaining;                                                                                                   // How many bites are left for Player to eat            (just a number, once its zero the player wins)
    protected int bitesTotal;                                                                                                       // How many bites there are for the Player initially    (just a number, the higher the bites total the harder the restaurant record run is)
    
    public ChallengeBooth(float x, float y, float z, int speedReward, int reputationReward, int timeLimit, int bitesTotal) {
        super(x, y, z, speedReward);
        
        this.reputationReward = reputationReward;                                                                                   // Call the Booth constructor to store the given x, y, z, and speedReward values
        
        this.timeLimit = timeLimit;                                                                                                 // Store the timeLimit for this ChallengeBooth
        this.timeLeft = this.timeLimit;                                                                                             // Set the timeLeft to complete the challenge to timeLimit by default

        this.bitesTotal = bitesTotal;                                                                                               // Store the number of bitesTotal it takes to finish the meal at this ChallengeBooth
        this.bitesRemaining = this.bitesTotal;                                                                                      // Set the number of bitesRemaining to finish the challenge's meal to bitesTotal by default
    }
    
    @Override
    protected void startEatingRun() {
        Player player = Player.getInstance();                                                                                       // Store a local reference to the Player
        player.setEating(true);                                                                                               // Set the Player's eating state to true

        this.eatingRunOccurring = true;                                                                                             // Start the eating run
        this.oneSecondTimer = 1;                                                                                                    // Set the one second timer to one second
        this.timeLeft = this.timeLimit;                                                                                             // Set the amount of time left to timeLimit
        this.bitesRemaining = this.bitesTotal;                                                                                      // Set the amount of bites left to bitesTotal
    }
    
    
    @Override
    protected void processEatingRun(float dt) {
        if (this.oneSecondTimer > 0) {                                                                                              // If the one second timer is still going...
            this.oneSecondTimer -= dt;                                                                                                  // Decrement the timer by dt
        }
        else {                                                                                                                      // Otherwise, the timer must be done, so...
            Player player = Player.getInstance();                                                                                       // Store a local reference to the Player

            this.oneSecondTimer = 1;                                                                                                    // Reset the timer to one second
            this.bitesRemaining -= (player.getEatingSpeed() + player.getBonusBites() * (int)(player.getEatingSpeed() * 0.1d));          // Decrement the number of bitesRemaining by the sum of the Player's eating speed + the number of bonusBites the Player earned multiplied by 1/10 of the Player's eating speed.       Not sure if this math will make it balanced in-game, but we'll test and adjust
        }

        if (this.bitesRemaining <= 0) {                                                                                             // If there are no bites remaining...
            this.endEatingRun(true);                                                                                          // End this eating run with the Player's victory  
        }
        else if (this.timeLeft <= 0) {                                                                                              // Otherwise, if there's no time left...
            this.endEatingRun(false);                                                                                         // End this eating run with the Player's defeat
        }
    }
    
    @Override
    protected void endEatingRun(boolean playerWon) {
        Player player = Player.getInstance();                                                                                       // Store a local reference to the Player
        Drawer drawer = Drawer.getInstance();
        
        player.setEating(false);                                                                                              // Set the Player's eating state to false
        player.getBonusBites();                                                                                                     // Get the Player's bonus bites (this is called so that the Player's bonus bites get reset, no leftovers)
        
        this.eatingRunOccurring = false;                                                                                            // End the eating run
        
        if (playerWon) {                                                                                                            // If the Player was victorious...
            player.increaseEatingSpeed(this.speedReward);                                                               // Increase their eatingSpeed by speedReward
            player.increaseEatingReputation(this.reputationReward);                                                 // Increase their eatingReputation by reputationReward
            drawer.displayMessage("You completed the Restaurant's Challenge and were rewarded with speed and reputation!");      // Display a message saying that the Player won
        }
        else {                                                                                                                      // Otherwise, the Player must have lost, so...
            drawer.displayMessage("You failed the Restaurant's Challenge. Increase your eating speed and try again!");           // Display a message saying that the Player lost
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
   

    
    
    
    

}
