package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import java.lang.Math;

/**
 *
 * @author Jason Ciampa
 */
public class Asset {
    
    // FIELDS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Position
    protected float x;                                                                                                                                          // x-coordinate of the Asset
    protected float y;                                                                                                                                          // y-coordinate of the Asset
    protected float z;                                                                                                                                          // z-coordinate of the Asset
    
    // Dimensions
    protected float length;                                                                                                                                     // x-coordinate in blender
    protected float width;                                                                                                                                      // z-coordinate in blender
    protected float height;                                                                                                                                     // y-coordinate in blender
    
    // 3D Model / Skin
    protected SceneAsset gltfFile;                                                                                                                              // Blender file for the Asset
    protected Scene body;                                                                                                                                       // Physical Model/Body representing the Asset
    
    
    // CONSTRUCTORS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Creates an Asset without a 3D Model / Skin
    public Asset(float x, float y, float z, float length, float width, float height) {
        this.setLocation(x, y, z);                                                                                                                              // Set the location of the Asset to the given x, y, z coordinates
        
        this.length = length;                                                                                                                                   // Store the length of the Asset
        this.width = width;                                                                                                                                     // Store the width of the Asset
        this.height = height;                                                                                                                                   // Store the height of the Asset
    }
    
    // Creates an Asset with a 3D Model / Skin
    public Asset(String gltfFilePath, float x, float y, float z, float length, float width, float height) {
        this.setLocation(x, y, z);                                                                                                                              // Set the location of the Asset to the given x, y, z coordinates

        this.gltfFile = new GLTFLoader().load(Gdx.files.internal(gltfFilePath));                                                                    // Load and store the gltf file of the Asset
        this.body = new Scene(gltfFile.scene);                                                                                                        // Store the Model of the Asset
    }

    // Creates an Asset with a 3D Model / Skin and a default Animation
    public Asset(String gltfFilePath, float x, float y, float z, float length, float width, float height, String animationName, boolean loopAnimation) {
        this.setLocation(x, y, z);                                                                                                                              // Set the location of the Asset to the given x, y, z coordinates

        this.gltfFile = new GLTFLoader().load(Gdx.files.internal(gltfFilePath));                                                                    // Load and store the gltf file of the Asset
        this.body = new Scene(gltfFile.scene);                                                                                                        // Store the Model of the Asset
        
        this.setAnimation(animationName, loopAnimation);                                                                                                        // Set the animation of the Asset to the given animation                                                                                
    }
    
    
    // METHODS // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Disposes the gltfFile for garbage collection
    public void dispose() {
        this.gltfFile.dispose();                                                                                                                                // Dispose of the Asset's gltf file
    }
    
    // Setters
    
    // Sets the Asset's current animation
    public void setAnimation(String animationName, boolean loopAnimation) {
        if (loopAnimation) {                                                                                                                                    // If the Animation is supposed to loop...                                                                                  
            body.animationController.setAnimation(animationName, -1);                                                                                   // Set the animation with a loop
        }   
        else {                                                                                                                                                  // Otherwise, the Animation isn't supposed to loop, so...
            body.animationController.setAnimation(animationName);                                                                                                // Set the Animation to play once
        }
    }
    
    // Sets the Asset's location
    public void setLocation(float x, float y, float z) {
        this.x = x;                                                                                                                                             // Store the given x-coordinate of the Asset
        this.y = y;                                                                                                                                             // Store the given y-coordinate of the Asset
        this.z = z;                                                                                                                                             // Store the given z-coordinate of the Asset
        
        if (this.body != null) {                                                                                                                                // If this Asset has an associated 3D Model...
            // In this transform Matrix4, the x, y, and z position coords are stored in val at indexes 12, 13, and 14 respectively     
            body.modelInstance.transform.val[12] = this.x;                                                                                                          // Set the Asset's model to the same x-coordinate
            body.modelInstance.transform.val[13] = this.y;                                                                                                          // Set the Asset's model to the same y-coordinate
            body.modelInstance.transform.val[14] = this.z;                                                                                                          // Set the Asset's model to the same z-coordinate
        }   
    }
    
    
    // Getters
    
    public Scene getBody() {
        return this.body;
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }
    
    // Calculates and returns the distance from this Asset to another given Asset
    public float getDistance(float x, float y, float z) {
        return (float) Math.sqrt(Math.pow((this.x - x), 2) + Math.pow((this.y - y), 2) + Math.pow((this.z - z), 2));                                    // 3D Space Distance Between Two Points Formula: https://www.youtube.com/watch?v=0c6cP2zLC2c
    }
    
    
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
