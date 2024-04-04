package com.mygdx.game;

/**
 *
 * @author Jason Ciampa
 */
public class Booth extends Asset implements Interactable, Updatable {

    // FIELDS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private int speedReward;                // How much eating speed to award the Player            (measured in bites per second)
    private int reputationReward;           // How much eating reputation to award the Player       (just a number, the higher the better)
    private double timeLimit;               // How much time the player has to eat their meal       (measured in seconds)
    
    private double timeLeft;                // How much time the player currently has left to eat their meal (measured in seconds)
    private int bitesRemaining;             // How many bites are left for Player to eat            (just a number, once its zero the player wins)
    private int bitesTotal;                 // How many bites there are for the Player initially    (just a number, the higher the bites total the harder the restaurant record run is)
    private boolean eatingRunOccurring;     // Whether or not an eating run is occurring
    
    private double oneSecondTimer;
    
    // CONSTRUCTORS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public Booth(float x, float y, float z) {
      super("booth/TrainingBooth.gltf", x, y, z);
        
      this.x = 41;
      this.y = 27.6f;
      this.z = 15.6f;      
    }
    
    public Booth(float x, float y, float z, int speedReward, int reputationReward, int timeLimit, int bitesTotal) {
        super("booth/TrainingBooth.gltf", x, y, z);
        
        this.speedReward = speedReward;
        this.reputationReward = reputationReward;
        this.timeLimit = timeLimit;
        this.bitesTotal = bitesTotal;
    }
    
    // METHODS // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private void startEatingRun() {
        this.bitesRemaining = this.bitesTotal;
        this.timeLeft = this.timeLimit;
        this.eatingRunOccurring = true;
        this.oneSecondTimer = 1;
    }
    
    private void processEatingRun(float dt) {
        this.timeLeft -= dt;
        
        if (this.oneSecondTimer > 0) {
            this.oneSecondTimer -= dt;
        }
        else {
            Player player = Player.getInstance();

            this.oneSecondTimer = 1;
            this.bitesRemaining -= (player.getEatingSpeed() + player.getBonusBites());
        }
        
        if (this.bitesRemaining <= 0) {
            this.endEatingRun(true);
        }
        else if (this.timeLeft <= 0) {
            this.endEatingRun(false);
        }
    }
    
    private void endEatingRun(boolean playerWon) {
        Player player = Player.getInstance();
        player.setEating(false);
        this.eatingRunOccurring = false;
        
        if (playerWon) {
            player.increaseEatingSpeed(this.speedReward);
            player.increaseEatingReputation(this.reputationReward);
            // Display on screen somehow that speed and rep were increased
        }
        else {
            // Display on screen somehow that player lost, no rewards
        }
    }
    
    
    // Interactable Methods
    
    @Override
    public void processInteractions() {
//        if (booth is close enough to player.getInstance()) {
//            if ('e' key is pressed) {
//                receiveInteraction(this, eWasPressed);
//            }
//        }
    }

    @Override
    public void sendInteraction(Interactable recipient, int interactionType) {
        // The booth does not send interactions
    }

    @Override
    public void receiveInteraction(Interactable sender, int interactionType) {
        Player player = Player.getInstance();
        player.setLocation(-13, 17, 8.5f);
        player.getCamera().getView().lookAt(15.64f, 17, 9);
        player.setEating(true);
        this.startEatingRun();
    }
    
    // Updatable Methods
    
    @Override
    public void load() {
        
    }
    
    @Override
    public void update(float dt) {
        if (this.eatingRunOccurring) {
            this.processEatingRun(dt);
        }
    }
    
    @Override
    public void render() {
        
    }
    
    
    // Getters
    
    public int getSpeedReward() {
        return this.speedReward;
    }
    
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
    
    
    // Setters
    
    public void setSpeedReward(int speed) {
        this.speedReward = speed;
    }
    
    public void setReputationReward(int reputation) {
        this.reputationReward = reputation;
    }
    
    public void setTimeLimit(double timeLimit) {
        this.timeLimit = timeLimit;
    }
    
    public void setBitesRemaining(int bitesRemaining) {
        this.bitesRemaining = bitesRemaining;
    }
    
    public void setBitesTotal(int bitesTotal) {
        this.bitesTotal = bitesTotal;
    }
    
}
