package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import java.util.ArrayList;

/**
 *
 * @author Jason Ciampa
 */

// PLANNING TO UTILIZE THE SINGLETON DESIGN PATTERN HERE, CONSTRUCTORS BELOW ARE TEMPORARY
public class Player implements Interactable, Updatable {
    
    // FIELDS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Game Variables
    private int eatingSpeed;
    private int eatingReputation;
    private int bonusBites;
    private boolean eating;
    
    // Position
    private float x;
    private float y;
    private float z;
    
    // Speed
    private float moveSpeed;
    
    // Camera
    private FirstPersonCamera camera;
    
    // Instance
    private static Player instance;

    
    // CONSTRUCTOR // --------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private Player() {
        this.camera = new FirstPersonCamera(20, 10, 25, 0, new PerspectiveCamera(60f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        
        this.x = this.camera.getView().position.x;
        this.y = this.camera.getView().position.y;
        this.z = this.camera.getView().position.z;
    }
    
    
    // METHODS // --------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void sitDown(float x, float y, float z, float lookX, float lookZ) {
        this.setLocation(x, y, z);
        this.camera.getView().lookAt(lookX, this.y, lookZ);
    }
    
    public void eat() {
        // Check if a key was pressed, THEN check if that key that was pressed was 'e' (or any other active letter). If so, increment bonus bites. Otherwise, despawn the letter (despawn just one letter if there are multiple, the first one that spawned will die first) user biffed it!
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            this.bonusBites += 1;
        }
    }
    
    public void increaseEatingSpeed(int eatingSpeedGained) {
        this.eatingSpeed += eatingSpeedGained;
    }
    
    public void increaseEatingReputation(int eatingReputationGained) {
        this.eatingReputation += eatingReputationGained;
    }
    
    // Interactable Methods
    
    // Checks proximity to other Interactable objects and detects key presses to send interactions

    @Override
    public void processInteractions() {
        // This code will check the player's distance from other interactable objects
        // When close enough, code will check if player presses interact button 'e'
        // If 'e' pressed, interaction is sent from player to that interactable object (player.sendInteraction(recipient, keyPress))
        
        ArrayList<Interactable> booths = Restaurant.getInstance().getBooths();
        
//        for (Interactable booth : booths) {
//            if (booth is close enough to player) {
//                if ('e' key is being pressed) {
//                    sendInteraction(booth, eWasPressed);
//                }
//            }
//        }
    }
    
    @Override
    // Sends an interaction to another Interactable object
    public void sendInteraction(Interactable recipient, int interactionType) {
        // This code will send the interaction to the recipient by calling recipient.recieveInteraction(player, keyPress)
        recipient.receiveInteraction(this, interactionType);
    }
    
    @Override
    // Handles an interaction request from another Interactable object
    public void receiveInteraction(Interactable sender, int interactionType) {
        // This code will only be run after an iteraction is sent
        // The code will be manually defined to handle a specific type of interaction
        // For example, when the player interacts with a booth
        // The booth's receiveInteraction code will move the player so they are sitting in the booth and will start the eating
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
    
    public int getEatingReputation() {
        return this.eatingReputation;
    }
    
    public int getBonusBites() {
        return this.bonusBites * (int)(this.eatingSpeed * 0.1d);    // Not sure if this math will make it balanced in-game, but we'll test and adjust
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
    
    // Executes when the Player is loaded into the game for the first time
    @Override
    public void load() {
        
    }
    
    // Executes every frame
    @Override
    public void update(float dt) {
        this.camera.update(dt);
        
        this.x = this.camera.getView().position.x;
        this.z = this.camera.getView().position.z;
        
        this.setLocation(this.x, this.y, this.z);
        
        this.processInteractions();

        if (this.eating) {
            this.eat();
        }
    }
    
    // Executes every frame
    @Override
    public void render() {
        
    }
    
    
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
