package com.mygdx.game;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Jason Ciampa
 */
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
       this.setAnimation("eatingAnimation.002", true);
    }
    
    public static Arms getInstance() {
        if (instance == null) {
            instance = new Arms();
        }
        
        return instance;
    }
    
    public void rotate() {
        this.player = Player.getInstance();
        
        Matrix4 orientationMatrix = new Matrix4().setToLookAt(Vector3.Zero, this.player.getCamera().getView().direction, this.player.getCamera().getView().up);

        Quaternion cameraAngle = new Quaternion();
        cameraAngle.setFromMatrix(orientationMatrix);
        
        this.body.modelInstance.transform.rotate(this.rotation);     
        
        this.rotation = cameraAngle;
        
        this.body.modelInstance.transform.rotate(this.rotation.cpy().conjugate());   
    }
    
    public void update(float dt) {
        this.player = Player.getInstance();
        this.setLocation(this.player.getX(), 15, this.player.getZ() + 20);
        this.rotate();
    }
}
