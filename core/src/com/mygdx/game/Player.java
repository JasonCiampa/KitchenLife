package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;
import net.mgsx.gltf.scene3d.scene.SceneManager;

/**
 *
 * @author Jason Ciampa
 */
public class Player implements Interactable, Updatable {
    
    // FIELDS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Game Variables
    private float eatingSpeed;                                                                                                                                                                 // How many bites per second the Player can eat
    private int eatingReputation;                                                                                                                                                              // How skilled of a Professional Food Eater the Player is
    private int bonusBites;                                                                                                                                                                    // How many bonus bites the Player has currently
    private boolean eating;                                                                                                                                                                    // Whether or not the Player is currently eating
    private boolean collisionDetection;                                                                                                                                                        // Whether or not the Player is currently checking for collisions
    private Sound biteSFX;
    
    // Position
    private float x;                                                                                                                                                                           // The x-coordinate of the Player
    private float y;                                                                                                                                                                           // The y-coordinate of the Player
    private float z;                                                                                                                                                                           // The z-coordinate of the Player
        
    // Speed
    private float moveSpeed;                                                                                                                                                                   // The move speed of the Player
    
    // Camera
    private FirstPersonCamera camera;                                                                                                                                                          // The camera of the Player
    public Arms arms;
    
    // Instance
    private static Player instance;                                                                                                                                                            // The one Singleton instance of the Player

    private float timer = 0;
    
    // CONSTRUCTOR // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private Player() {
        this.camera = new FirstPersonCamera(50, 10, 25, 0, new PerspectiveCamera(60f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));    // Create and store the Player's camera
        
        Gdx.input.setInputProcessor(this.camera);                                                                                                                                           // Set the input processor for our game to be based on the Player's camera

        this.x = this.camera.getView().position.x;                                                                                                                                             // Store the x-coordinate of the Player
        this.y = this.camera.getView().position.y;                                                                                                                                             // Store the y-coordinate of the Player     
        this.z = this.camera.getView().position.z;                                                                                                                                             // Store the z-coordinate of the Player
        
        this.collisionDetection = true;                                                                                                                                                        // Enable the Player's collision detection
        
        this.eatingSpeed = 1;
        this.eatingReputation = 0;
        
        this.biteSFX = Gdx.audio.newSound(Gdx.files.internal("sfx/chomp.mp3"));
    }
    
    
    // METHODS // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Sits the Player 
    public void sitDown(float x, float z, float lookX, float lookZ) {
        this.setLocation(x, 20, z);                                                                                                                                                          // Set the location of the Player to x, 17, z (17 is the seated height)
        this.camera.getView().lookAt(lookX, this.y, lookZ);                                                                                                                              // Make the Player look straight ahead in a given direction
    }
    
    // Handles the Player's input while in an eating situation
    public void eat() {
        if (Mouse.checkClick()) {                                                                                                                                                           // If the mouse was clicked...
            this.bonusBites += 1;                                                                                                                                                               // Increment the number of bonus bites by 1
            this.biteSFX.play(0.2f);
        }
    }
    
    // Increases the eating speed of the Player
    public void increaseEatingSpeed(float eatingSpeedGained) {
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
    
    public boolean getEating() {
        return this.eating;
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
    
    // Returns the coordinates that the Player is looking at currently
    public float[] getLookingAt() {
        return new float[] {this.camera.getView().direction.x, this.camera.getView().direction.y, this.camera.getView().direction.z};
    }
    
    public void setEating(boolean eating) {
        this.eating = eating;
    }
    
    public void setCollisionDetection(boolean collisionDetection) {
        this.collisionDetection = collisionDetection;
    }
   
    
    public void bite() {
        this.biteSFX.play(0.2f);
    }
    
    // UPDATABLES
    
    public void load(SceneManager sceneManager) {
//        sceneManager.addScene(this.arms.body);
    }
    
    // Executes every frame
    @Override
    public void update(float dt) {
        this.camera.update(dt);                                                                                                                                                              // Updates the Player's camera
        
//        this.arms.update(dt);
        
        if (this.eating) {                                                                                                                                                                            // If the Player is currently eating...                                                                                                                                                                     
            this.eat();                                                                                                                                                                                   // Process the Player's eating
        }
        else {                                                                                                                                                                                         // Otherwise, the Player could be moving around, so...
            this.x = this.camera.getView().position.x;                                                                                                                                                    // Update the Player's x-coordinate
            this.z = this.camera.getView().position.z;                                                                                                                                                    // Update the Player's z-coordinate
            
            
            // COLLISION DETECTION
            // I manually moved the Player around and printed the coordinates so that I could determine which areas to make off-limits for the Player.
            // If the Player goes past any of these numbers, it will be set back to that number before the update completes (preventing movement past the barrier).
            // Collision Detection should be disabled when the Player is sat down because their movement is restricted
            
            if (this.collisionDetection) {
                // Wall 1 Barrier
                if (this.x < -41) {
                    this.x = -41;
                }
                // Wall 2 Barrier
                else if (this.x > 173) {
                    this.x = 173;
                }

                // Training Booth Barrier
                if (this.z < -58) {
                    this.z = -58;
                }
                // Training Booth Barrier
                else if (this.z > 13) {
                    this.z = 13;
                }
            }
            
            this.setLocation(this.x, this.y, this.z);                                                                                                                                               // Set the Player's location to x, y, z    
        }
    }
    
    // Executes every frame
    @Override
    public void render() {
        
    }
    
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
