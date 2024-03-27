package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;

/**
 *
 * @author Jason Ciampa
 */

// WORKING ON MY OWN SUBCLASS OF A LIBGDX CAMERA, BUT I AM STRUGGLING!
public class MyFirstPersonCamera extends FirstPersonCameraController {
    
    private int speed;
    
    public MyFirstPersonCamera(Camera camera, int speed) {
        super(camera);
        
        this.speed = speed;
    }
    
    @Override
    public void setVelocity(float velocity) {
        this.velocity = 0;
    }
    
//    
//    @Override
//    public void update(float dt) {        
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            this.camera.position.z -= this.speed * dt;
//        }
//        
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            this.camera.position.z += this.speed * dt;
//        }
//        
//        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//            this.camera.position.x -= this.speed * dt;
//        }
//        
//        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//            this.camera.position.x += this.speed * dt;
//        }
//    }
}
