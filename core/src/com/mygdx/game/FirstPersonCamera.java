package com.mygdx.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;

/**
 *
 * @author Jason Ciampa
 */

// WORKING ON MY OWN SUBCLASS OF A LIBGDX CAMERA, BUT I AM STRUGGLING!
public class FirstPersonCamera extends FirstPersonCameraController {
    private int speed;
    private Camera view;
            
    public FirstPersonCamera(int speed, float x, float y, float z, Camera camera) {
        super(camera);
        this.speed = speed;
        this.view = camera;
        
        this.view.position.x = x;
        this.view.position.y = y;
        this.view.position.z = z;
        
        this.setVelocity(this.speed);
    }
    
    @Override
    public void setVelocity(float velocity) {
        this.velocity = speed;
    }
    
    public Camera getView() {
        return this.view;
    }
}
