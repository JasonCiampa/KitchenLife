package com.mygdx.game;

/**
 *
 * @author Jason Ciampa
 */

// This class was going to be used to handle the movement of Food to and from the Player's mouth, but I didn't get around to it.
// Now it is essentially just an Asset, but for readability it helps to know that this Asset is particularly a Food

public class Food extends Asset {
        
    // CONSTRUCTORS // -------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public Food(String gltfFilePath, float x, float y, float z, float length, float width, float height) {
        super(gltfFilePath, x, y, z, length, width, height);
    }
    
    public Food(String gltfFilePath, float x, float y, float length, float width, float height, float z, String animationName, boolean loopAnimation) {
        super(gltfFilePath, x, y, z, length, width, height, animationName, loopAnimation);
    }
}
