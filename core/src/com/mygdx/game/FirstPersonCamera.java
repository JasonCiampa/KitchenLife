package com.mygdx.game;

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
        
    // CONSTRUCTOR // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public FirstPersonCamera(int speed, float x, float y, float z, Camera camera) {
        super(camera);                                                                          // Call the super constructor (FirstPersonCameraController)
        this.speed = speed;                                                                     // Store the speed of the FirstPersonCamera
        this.view = camera;                                                                     // Store the viewport of the FirstPersonCamera
        
        this.camera.far = 500;                                                                  // Store the far render distance of the FirstPersonCamera
        this.camera.near = 1;                                                                   // Store the close render distance of the FirstPersonCamera
        
        this.view.position.x = x;                                                               // Store the x-coordinate of the FirstPersonCamera
        this.view.position.y = y;                                                               // Store the y-coordinate of the FirstPersonCamera
        this.view.position.z = z;                                                               // Store the z-coordinate of the FirstPersonCamera
            
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
