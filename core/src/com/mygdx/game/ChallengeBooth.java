package com.mygdx.game;

/**
 *
 * @author Jason Ciampa
 */
public class ChallengeBooth extends InteractableAsset {
    
    public ChallengeBooth(String gltfFilePath, float x, float y, float z) {
        super(gltfFilePath, x, y, z);
        // WORK IN PROGRESS
    }
    
    public ChallengeBooth(String gltfFilePath, float x, float y, float z, String animationName, boolean loopAnimation) {
        super(gltfFilePath, x, y, z, animationName, loopAnimation);
        // WORK IN PROGRESS
    }
    
   
    
    @Override
    protected boolean processInteractions() {
        return true;
    }
    
    @Override
    protected void sendInteraction(InteractableAsset recipient, int interactionType) {
        
    }
    
    @Override
    protected void recieveInteraction(InteractableAsset sender, int interactionType) {
        
    }
}
