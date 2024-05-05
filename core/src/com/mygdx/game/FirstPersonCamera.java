package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;

/**
 *
 * @author Jason Ciampa
 */
public class FirstPersonCamera extends FirstPersonCameraController {
    
    // FIELDS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private int speed;                                                                          // Movement speed of the FirstPersonCamera
    private Camera view;                                                                        // Viewport of the FirstPersonCamera
        
    private float lookX;                                                                        // The x-coordinate that the FirstPersonCamera is looking at
    private float lookY;                                                                        // The y-coordinate that the FirstPersonCamera is looking at
    private float lookZ;                                                                        // The z-coordinate that the FirstPersonCamera is looking at    
    
    
    // CONSTRUCTOR // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public FirstPersonCamera(int speed, float locationX, float locationY, float locationZ, Camera camera) {
        super(camera);                                                                          // Call the super constructor (FirstPersonCameraController)
        this.speed = speed;                                                                     // Store the speed of the FirstPersonCamera
        this.view = camera;                                                                     // Store the viewport of the FirstPersonCamera
        
        this.camera.far = 500;                                                                  // Store the far render distance of the FirstPersonCamera
        this.camera.near = 1;                                                                   // Store the close render distance of the FirstPersonCamera
        
        this.view.position.x = locationX;                                                       // Store the locationX-coordinate of the FirstPersonCamera
        this.view.position.y = locationY;                                                       // Store the y-coordinate of the FirstPersonCamera
        this.view.position.z = locationZ;                                                       // Store the z-coordinate of the FirstPersonCamera
                    
        this.lookX = this.view.direction.x;                                                     // Store the angle on the x-axis that the FirstPersonCamera is looking towards
        this.lookY = this.view.direction.y;                                                     // Store the angle on the y-axis that the FirstPersonCamera is looking towards
        this.lookZ = this.view.direction.z;                                                     // Store the angle on the z-axis that the FirstPersonCamera is looking towards
        
        this.setVelocity(this.speed);                                                   // Set the movement speed of the FirstPersonCamera
    }
    
    public FirstPersonCamera(int speed, float locationX, float locationY, float locationZ, float lookX, float lookY, float lookZ, Camera camera) {
        super(camera);                                                                          // Call the super constructor (FirstPersonCameraController)
        this.speed = speed;                                                                     // Store the speed of the FirstPersonCamera
        this.view = camera;                                                                     // Store the viewport of the FirstPersonCamera
        
        this.camera.far = 500;                                                                  // Store the far render distance of the FirstPersonCamera
        this.camera.near = 1;                                                                   // Store the close render distance of the FirstPersonCamera
        
        this.view.position.x = locationX;                                                       // Store the locationX-coordinate of the FirstPersonCamera
        this.view.position.y = locationY;                                                       // Store the y-coordinate of the FirstPersonCamera
        this.view.position.z = locationZ;                                                       // Store the z-coordinate of the FirstPersonCamera
        
        this.view.direction.x = lookX;                                                          // Store the x-coordinate for the FirstPersonCamera to look towards
        this.view.direction.y = lookY;                                                          // Store the y-coordinate for the FirstPersonCamera to look towards    
        this.view.direction.z = lookZ;                                                          // Store the z-coordinate for the FirstPersonCamera to look towards   
        
        this.setVelocity(this.speed);                                                   // Set the movement speed of the FirstPersonCamera
    }
    
    
    
    // METHODS // ------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    @Override
    public void setVelocity(float velocity) {
        this.velocity = speed;                                                                  // Set the velocity equal to the speed of the FirstPersonCamera
    }
    
    public Camera getView() {
        return this.view;
    }
    
    
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
