package com.mygdx.game;

import java.util.ArrayList;

/**
 *
 * @author Jason Ciampa
 */
public class Player extends InteractableAsset implements Updatable {
    int eatingSpeed;
    int eatingReputation;
    
    ArrayList<Animation> animations;
    Animation currentAnimation;
    
    
    
    @Override
    protected void sendInteraction(InteractableAsset recipient, int interactionType) {
        
    }
    
    @Override
    protected void recieveInteraction(int interactionType) {
        
    }
    
    @Override
    protected boolean processInteractions() {
        return true;
    }
    
    
    // UPDATABLES
    
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
