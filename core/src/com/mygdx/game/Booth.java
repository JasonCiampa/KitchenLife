package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 *
 * @author Jason Ciampa
 */
public abstract class Booth extends Asset implements Interactable, Updatable {

    // FIELDS // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    protected int speedReward;                                                                                                                      // How much eating speed to award the Player (measured in bites per second)
    protected boolean eatingRunOccurring;                                                                                                           // Whether or not an eating run is occurring
    protected double oneSecondTimer;                                                                                                                // A timer used to count each second
    
    // CONSTRUCTOR // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public Booth(float x, float y, float z, int speedReward) {
      super("models/booth/booth.gltf", x, y, z, 41, 27.6f, 15.6f);                                            // Calls the Asset constructor to create the Booth
      this.speedReward = speedReward;                                                                                                               // Stores the speedReward for the Booth
      this.eatingRunOccurring = false;                                                                                                              // Sets eatingRunOccurring to false
      this.oneSecondTimer = 1;                                                                                                                      // Sets the oneSecondTimer to one second
    }
    
    // METHODS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Abstract Eating Run Methods
    
    protected abstract void startEatingRun();                                                                                                       // Method used to handle the beginning of an eating run
    
    protected abstract void processEatingRun(float dt);                                                                                             // Method used to process the events occurring during the eating run
    
    protected abstract void endEatingRun(boolean playerWon);                                                                                        // Method used to handle the ending of an eating run
       
    
    // Interactable Methods
    
    @Override
    public void detectInteractions() {
        Player player = Player.getInstance();                                                                                                       // Store a local reference to the Player 
        Drawer drawer = Drawer.getInstance();                                                                                                       // Store a local reference to the Drawer 
        
        float dt = Gdx.graphics.getDeltaTime();                                                                                                     // Get the amount of time that has passed since the last call to render and store it in dt
        float distanceFromPlayer = this.getDistance(player.getX(), player.getY(), player.getZ());                                             // Calculate the distance from this booth to the Player
        
        if (this.oneSecondTimer > 0) {                                                                                                              // If the one second timer is still going...
            this.oneSecondTimer -= dt;                                                                                                                  // Decrement the timer by dt
        }
        else {
            this.oneSecondTimer = 1;                                                                                                                // Reset the one second timer to one second

            // DEBUG
            System.out.println("\n\nDistance From Booth to Player: " + distanceFromPlayer);
            System.out.println("Player Coords: X: " + player.getX() + ",   Y: " + player.getY() + ",   Z: " + player.getZ());
            System.out.println("Booth Coords: X: " + this.x + ",   Y: " + this.y + ",   Z: " + this.z);
        }

        
        if (distanceFromPlayer < 25) {                                                                                                              // If the Player is 10 units away from the booth...
            drawer.displayMessage("Press 'E' to sit down", 250, 100, 1420, 200, 0.1f);          // Display a message to the Player saying they can press 'e' to sit down
            
            if (Gdx.input.isKeyPressed(Input.Keys.E)) {                                                                                             // If the 'e' key is pressed...
                this.processInteraction(player, 1);                                                                                   // Trigger the Player's interaction on the Booth
            }
        }
    }


    @Override
    public void processInteraction(Interactable sender, int interactionType) {
        Player player = Player.getInstance();                                                                                                       // Store a local reference to the Player 
        player.setEating(true);                                                                                                               // Trigger the Player's eating state
        player.sitDown(-13, 8.5f, 15.64f, 9f);                                                                                      // Set the Player to be seated in the Booth facing forward
        this.startEatingRun();                                                                                                                      // Trigger the start of the eating run
    }
    
    
    // Updatable Methods
    
    @Override
    public void update(float dt) {
        if (this.eatingRunOccurring) {                                                                                                              // If an eating run is occurring...
            this.processEatingRun(dt);                                                                                                                  // Process the eating run
        }
        else {                                                                                                                                      // Otherwise, no eating run is occurring, so...
            this.detectInteractions();                                                                                                                 // Process interactions that may start a new eating run
        }
    }
   
    @Override
    public void render() {
        
    }
    
    
    // Getters
    public int getSpeedReward() {
        return this.speedReward;
    }
    
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
