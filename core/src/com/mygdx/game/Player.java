package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import java.util.ArrayList;

/**
 *
 * @author Jason Ciampa
 */
public class Player implements Interactable, Updatable {
    
    // FIELDS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Game Variables
    private int eatingSpeed;                                                                                                                                                                   // How many bites per second the Player can eat
    private int eatingReputation;                                                                                                                                                              // How skilled of a Professional Food Eater the Player is
    private int bonusBites;                                                                                                                                                                    // How many bonus bites the Player has currently
    private boolean eating;                                                                                                                                                                    // Whether or not the Player is currently eating
    
    // Position
    private float x;                                                                                                                                                                           // The x-coordinate of the Player
    private float y;                                                                                                                                                                           // The y-coordinate of the Player
    private float z;                                                                                                                                                                           // The z-coordinate of the Player
    
    // Speed
    private float moveSpeed;                                                                                                                                                                   // The move speed of the Player
    
    // Camera
    private FirstPersonCamera camera;                                                                                                                                                          // The camera of the Player
    
    // Instance
    private static Player instance;                                                                                                                                                            // The one Singleton instance of the Player

    
    // CONSTRUCTOR // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private Player() {
        this.camera = new FirstPersonCamera(20, 10, 25, 0, new PerspectiveCamera(60f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));    // Create and store the Player's camera
        
        Gdx.input.setInputProcessor(this.camera);                                                                                                                                           // Set the input processor for our game to be based on the Player's camera

        this.x = this.camera.getView().position.x;                                                                                                                                             // Store the x-coordinate of the Player
        this.y = this.camera.getView().position.y;                                                                                                                                             // Store the y-coordinate of the Player     
        this.z = this.camera.getView().position.z;                                                                                                                                             // Store the z-coordinate of the Player
    }
    
    
    // METHODS // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Sits the Player 
    public void sitDown(float x, float z, float lookX, float lookZ) {
        this.setLocation(x, 17, z);                                                                                                                                                          // Set the location of the Player to x, 17, z (17 is the seated height)
        this.camera.getView().lookAt(lookX, this.y, lookZ);                                                                                                                              // Make the Player look straight ahead in a given direction
    }
    
    // Handles the Player's input while in an eating situation
    public void eat() {
        // Check if the KeySpawner is actively spawning Keys
//        if (KeySpawner.isActive()) {
            // Check if a key was pressed, THEN check if that key that was pressed was 'e' (or any other active letter).
            // If so, increment bonus bites. 
            // Otherwise, despawn the letter (despawn just one letter if there are multiple, the first one that spawned will die first) user biffed it!
            
//            if (Gdx.input.isKeyPressed(Input.Keys.E)) {
//                this.bonusBites += 1;
//            }
//        }
//        else {
//            if (mouse is clicked) {
//                this.bonusBites += 1;
//            } 
//        }
        
        // Handle eating animation updates here possibly?
    }
    
    // Increases the eating speed of the Player
    public void increaseEatingSpeed(int eatingSpeedGained) {
        this.eatingSpeed += eatingSpeedGained;                                                                                                                                               // Increases the eating speed of the Player
    }

    // Increases the eating reputation of the Player
    public void increaseEatingReputation(int eatingReputationGained) {
        this.eatingReputation += eatingReputationGained;                                                                                                                                     // Increases the eating reputation of the Player
    }
    
    public int applyBonusBites() {
        int bonusBites = this.bonusBites;                                                                                                                                                    // Store the number of bonus bites in a variable
        this.bonusBites = 0;                                                                                                                                                                 // Reset the Player's count of bonusBites since these current bonus bites are being registered
        return bonusBites;                                                                                                                                                                   // Return the number of bonus bites that the Player had before the reset
    }
    
    // Interactable Methods
    
    @Override
    public void detectInteractions() {

    }
    
    @Override
    public void processInteraction(Interactable sender, int interactionType) {

    }
    
    // Getters
    
    // Returns the Player's x-coordinate
    public float getX() {
        return this.x;
    }
    
    // Returns the Player's y-coordinate
    public float getY() {
        return this.y;
    }
    
    // Returns the Player's z-coordinate
    public float getZ() {
        return this.z;
    }
    
    // Returns the Player's moveSpeed
    public float getMoveSpeed() {
        return this.moveSpeed;
    }
    
    // Returns the Player's moveSpeed
    public float getEatingSpeed() {
        return this.eatingSpeed;
    }
    
    // Returns the Player's eating reputation
    public int getEatingReputation() {
        return this.eatingReputation;
    }
    
    
    // Returns the Player's camera
    public FirstPersonCamera getCamera() {
        return this.camera;
    }
    
    // Returns the single instance of the Player class
    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        
        return instance;
    }
    
    
    // Setters
    
    // Sets the location of the Player
    public void setLocation(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.camera.getView().position.set(this.x, this.y, this.z);
    }
    
    public void setEating(boolean eating) {
        this.eating = eating;
    }
   
    
    // UPDATABLES
    
    // Executes every frame
    @Override
    public void update(float dt) {
        this.camera.update(dt);                                                                                                                                                              // Updates the Player's camera
        
        if (this.eating) {                                                                                                                                                                            // If the Player is currently eating...                                                                                                                                                                     
            this.eat();                                                                                                                                                                                   // Process the Player's eating
        }
        else {                                                                                                                                                                                         // Otherwise, the Player could be moving around, so...
            this.x = this.camera.getView().position.x;                                                                                                                                                    // Update the Player's x-coordinate
            this.z = this.camera.getView().position.z;                                                                                                                                                    // Update the Player's z-coordinate
            this.setLocation(this.x, this.y, this.z);                                                                                                                                               // Set the Player's location to x, y, z    
        }
    }
    
    // Executes every frame
    @Override
    public void render() {
        
    }
    
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
