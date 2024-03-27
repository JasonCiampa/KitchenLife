package com.mygdx.game;

/**
 *
 * @author Jason Ciampa
 */

// CONSIDERING MAKING THIS AN INTERFACE...
public abstract class InteractableAsset extends Asset {
    
    public InteractableAsset(String gltfFilePath, float x, float y, float z) {
        super(gltfFilePath, x, y, z);
        // WORK IN PROGRESS
    }
    
    public InteractableAsset(String gltfFilePath, float x, float y, float z, String animationName, boolean loopAnimation) {
        super(gltfFilePath, x, y, z, animationName, loopAnimation);
        // WORK IN PROGRESS
    }
    
    // Protected so that only the InteractableAsset inheritor can use these methods
    abstract protected boolean processInteractions();
    abstract protected void sendInteraction(InteractableAsset recipient, int interactionType);
    abstract protected void recieveInteraction(InteractableAsset sender, int interactionType);
    
}
