package com.mygdx.game;

/**
 *
 * @author Jason Ciampa
 */
public abstract class InteractableAsset extends Asset {
    
    // Protected so that only the InteractableAsset inheritor can use these methods
    abstract protected void sendInteraction(InteractableAsset recipient, int interactionType);
    abstract protected void recieveInteraction(int interactionType);
    abstract protected boolean processInteractions();
    
}
