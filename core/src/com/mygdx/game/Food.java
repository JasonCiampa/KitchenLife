package com.mygdx.game;

/**
 *
 * @author Jason Ciampa
 */
public class Food extends Asset implements Updatable {
    
    // FIELDS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------    
    
    // CONSTRUCTORS // -------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public Food(String gltfFilePath, float x, float y, float z, float length, float width, float height) {
        super(gltfFilePath, x, y, z, length, width, height);
    }
    
    public Food(String gltfFilePath, float x, float y, float length, float width, float height, float z, String animationName, boolean loopAnimation) {
        super(gltfFilePath, x, y, z, length, width, height, animationName, loopAnimation);
    }
    
    // METHODS // ------------------------------------------------------------------------------------------------------------------------------------------------------------------ 
    
    @Override
    public void update(float dt) {
        
    }
    
    @Override
    public void render() {
        
    }
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
