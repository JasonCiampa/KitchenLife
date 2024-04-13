package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 *
 * @author Jason Ciampa
 */
public abstract class Booth extends Asset implements Interactable, Updatable {

    // FIELDS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    protected int speedReward;                                                                                  // How much eating speed to award the Player            (measured in bites per second)
    protected boolean eatingRunOccurring;                                                                       // Whether or not an eating run is occurring
    protected double oneSecondTimer;                                                                            // A timer used to count each second
    
    // CONSTRUCTORS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public Booth(float x, float y, float z, int speedReward) {
      super("booth/TrainingBooth.gltf", x, y, z, 41, 15.6f, 27.6f);               // Calls the Asset constructor to create the Booth
      this.speedReward = speedReward;                                                                           // Stores the speedReward for the Booth
      this.eatingRunOccurring = false;                                                                          // Sets eatingRunOccurring to false
      this.oneSecondTimer = 1;                                                                                  // Sets the oneSecondTimer to one second
    }
    
    // METHODS // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    protected abstract void startEatingRun();
    
    protected abstract void processEatingRun(float dt);
    
    protected abstract void endEatingRun(boolean playerWon);
       
    
    // Interactable Methods
    
    @Override
    public void processInteractions() {
        Player player = Player.getInstance();
        Drawer drawer = Drawer.getInstance();
        
        if (this.getDistance(player.getX(), player.getY(), player.getZ()) < 10) {
//            drawer.draw()                                                                                 // Draw text/image on screen for user that says "Press 'E' to Sit"
            if (Gdx.input.isKeyPressed(Input.Keys.E)) {
                this.receiveInteraction(player, 1);
            }
        }
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
        else {
            this.processInteractions();
        }
    }
    
    @Override
    public void render() {
        
    }
    
    
    // Getters
    public int getSpeedReward() {
        return this.speedReward;
    }
}
