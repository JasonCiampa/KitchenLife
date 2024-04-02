package com.mygdx.game;

import com.badlogic.gdx.Gdx;
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
        this.camera = new FirstPersonCamera(20, 0, 25, 0, new PerspectiveCamera(60f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        
        this.x = this.camera.getView().position.x;
        this.y = this.camera.getView().position.y;
        this.z = this.camera.getView().position.z;
    }
    
    
    // METHODS // --------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
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
    public float getSpeed() {
        return this.moveSpeed;
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

    }
    
    // Executes every frame
    @Override
    public void render() {
        
    }
    
    
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
