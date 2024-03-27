package com.mygdx.game;

import java.util.ArrayList;

/**
 *
 * @author Jason Ciampa
 */

// PLANNING TO UTILIZE THE SINGLETON DESIGN PATTERN HERE, CONSTRUCTORS BELOW ARE TEMPORARY
public class Player extends InteractableAsset implements Updatable {
    int eatingSpeed;
    int eatingReputation;

    public Player(String gltfFilePath, float x, float y, float z) {
        super(gltfFilePath, x, y, z);
        // WORK IN PROGRESS
    }
    
    public Player(String gltfFilePath, float x, float y, float z, String animationName, boolean loopAnimation) {
        super(gltfFilePath, x, y, z, animationName, loopAnimation);
        // WORK IN PROGRESS
    }    
    
    
    @Override
    protected boolean processInteractions() {
        
        // This code will check the player's distance from other interactable objects
        // When close enough, code will check if player presses interact button 'e'
        // If 'e' pressed, interaction is sent from player to that interactable object (player.sendInteraction(recipient, keyPress))
        
        return true;
    }
    
    @Override
    protected void sendInteraction(InteractableAsset recipient, int interactionType) {
        // This code will send the interaction to the recipient by calling recipient.recieveInteraction(player, keyPress)
    }
    
    @Override
    protected void recieveInteraction(InteractableAsset sender, int interactionType) {
        // This code will only be run after an iteraction is sent
        // The code will be manually defined to handle a specific type of interaction
        // For example, when the player interacts with a booth
        // The booth's receiveInteraction code will move the player so they are sitting in the booth and will start the eating
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
