package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

/**
 *
 * @author Jason Ciampa
 */
public class Asset {
    
    // FIELDS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Position
    protected float x;
    protected float y;
    protected float z;
    
    // Speed
    protected float dx;
    protected float dy;
    protected float dz;
    
    // 3D Model / Skin
    protected SceneAsset gltfFile;
    protected Scene body;
    
    
    // CONSTRUCTORS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Creates an Asset without a 3D Model / Skin
    public Asset(float x, float y, float z) {
        this.setLocation(x, y, z);
    }
    
    // Creates an Asset with a 3D Model / Skin
    public Asset(String gltfFilePath, float x, float y, float z) {
        this.setLocation(x, y, z);

        this.gltfFile = new GLTFLoader().load(Gdx.files.internal(gltfFilePath));
        this.body = new Scene(gltfFile.scene);
    }

    // Creates an Asset with a 3D Model / Skin and a default Animation
    public Asset(String gltfFilePath, float x, float y, float z, String animationName, boolean loopAnimation) {
        this.setLocation(x, y, z);

        this.gltfFile = new GLTFLoader().load(Gdx.files.internal(gltfFilePath));
        this.body = new Scene(gltfFile.scene);
        
        this.setAnimation(animationName, loopAnimation);
    }
    
    
    // METHODS // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Disposes the gltfFile for garbage collection
    public void dispose() {
        this.gltfFile.dispose();
    }
    
    
    // Setters
    
    // Sets the Asset's current animation
    public void setAnimation(String animationName, boolean loopAnimation) {
        if (loopAnimation) {                                                                                     
            body.animationController.setAnimation(animationName, -1);
        }
        else {
            body.animationController.setAnimation(animationName);
        }
    }
    
    // Sets the Asset's location
    public void setLocation(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        if (this.body != null) {
            // In this transform Matrix4, the x, y, and z position coords are stored in val at indexes 12, 13, and 14 respectively     
            body.modelInstance.transform.val[12] = this.x;
            body.modelInstance.transform.val[13] = this.y;
            body.modelInstance.transform.val[14] = this.z;
        }
    }
    
    
    // Getters
    
    public Scene getBody() {
        return this.body;
    }
    
    
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
