package com.mygdx.game;

/**
 *
 * @author Jason Ciampa
 */
public class Food extends Asset implements Updatable {
    
    // UPDATABLES
    
    public Food(String gltfFilePath, float x, float y, float z) {
        super(gltfFilePath, x, y, z);
    }
    
    public Food(String gltfFilePath, float x, float y, float z, String animationName, boolean loopAnimation) {
        super(gltfFilePath, x, y, z, animationName, loopAnimation);
    }
    
    @Override
    public void load() {
        
    }
    
    @Override
    public void update() {
        
    }
    
    @Override
    public void render() {
        
    }
}
