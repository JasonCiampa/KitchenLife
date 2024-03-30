package com.mygdx.game;

/**
 *
 * @author Jason Ciampa
 */
public interface Interactable {
    public void processInteractions();       // This might not even be a necessary method because we can do all the proximity checks nad key press checks in the PLayer update function, but keep for now until we know for sure it won't be useful.
    public void sendInteraction(Interactable recipient, int interactionType);
    public void receiveInteraction(Interactable sender, int interactionType);
}