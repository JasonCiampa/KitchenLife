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
    
    // Asset Position, Speed, and Dimensions
    float x;
    float y;
    float z;
    
    float dx;
    float dy;
    float dz;
    
    float length, width, height;
    
    private SceneAsset gltfFile;
    private Scene body;
   
    
    public Asset(String gltfFilePath, float x, float y, float z) {
        this.gltfFile = new GLTFLoader().load(Gdx.files.internal(gltfFilePath));
        this.body = new Scene(gltfFile.scene);
        
        this.setLocation(x, y, z);
    }

    public Asset(String gltfFilePath, float x, float y, float z, String animationName, boolean loopAnimation) {
        this.gltfFile = new GLTFLoader().load(Gdx.files.internal(gltfFilePath));
        this.body = new Scene(gltfFile.scene);
        
        this.setLocation(x, y, z);
        this.setAnimation(animationName, loopAnimation);
    }
    
    
    public void setAnimation(String animationName, boolean loopAnimation) {
        if (loopAnimation) {                                                                                     // Set the animation for the coneModel and set it to loop
            body.animationController.setAnimation(animationName, -1);
        }
        else {
            body.animationController.setAnimation(animationName);
        }
    }
    
    public void setLocation(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        // In this transform Matrix4, the x, y, and z position coords are stored in val at indexes 12, 13, and 14 respectively     
        body.modelInstance.transform.val[12] = this.x;
        body.modelInstance.transform.val[13] = this.y;
        body.modelInstance.transform.val[14] = this.z;
    }
    
    public Scene getBody() {
        return this.body;
    }
    
    public void dispose() {
        this.gltfFile.dispose();
    }
    
//    public Asset(String gltfFilePath, float x, float y, float z, ArrayList<Animation> animations) {
//        this.gltfFile = new GLTFLoader().load(Gdx.files.internal(gltfFilePath));
//        this.body = new Scene(gltfFile.scene);
//        
//        this.x = x;
//        this.y = y;
//        this.z = z;
//    }
}
