package com.mygdx.game;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Jason Ciampa
 */


// The Arms were unfortunately not able to be implemented. I struggled and fought, but Euler Angles and Quaternions fought harder.
// Here is code that was pretty close to getting me there, but not quite
public class Arms extends Asset {
    private float x;
    private float y;
    private float z;
    
    private Player player;
    
    private Quaternion rotation = new Quaternion();
    
    // Instance
    private static Arms instance;                                                                                                                                                            // The one Singleton instance of the Player
    
    private Arms() {
       super("models/person/arms.gltf", 20, 15, 0, 7.39f, 4.23f, 1.5f);
       this.setAnimation("eating", true);
       
       Quaternion quaternion = new Quaternion().setFromAxis(Vector3.Y, 0);
       this.body.modelInstance.transform.rotate(quaternion);
    }
    
    public static Arms getInstance() {
        if (instance == null) {
            instance = new Arms();
        }
        
        return instance;
    }
    
    public void rotate() {
        // Get the current orientation of the Player Camera
        Matrix4 orientationMatrix = new Matrix4().setToLookAt(Vector3.Zero, this.player.getCamera().getView().direction, this.player.getCamera().getView().up);

        // Store the orientation of the Player Camera as a Quaternion
        Quaternion cameraAngle = new Quaternion();
        cameraAngle.setFromMatrix(orientationMatrix);
        
        // Undo the current rotation of the model
        this.body.modelInstance.transform.rotate(this.rotation);     
        
        // Set the new rotation of the model to be based on the euler angles of the Player's Camera
        this.rotation = cameraAngle.setEulerAngles(cameraAngle.getYaw(), cameraAngle.getPitch(), cameraAngle.getRoll());
        
        // Rotate the model with the new rotation
        this.body.modelInstance.transform.rotate(this.rotation.cpy().conjugate());   
    }
    
    public void update(float dt) {
        if (this.player == null) {
            this.player = Player.getInstance();
        }

        this.setLocation(this.player.getX(), this.player.getY() - 10, this.player.getZ());
        this.rotate();
    }
}
