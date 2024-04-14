package com.mygdx.game;

/**
 *
 * @author Jason Ciampa
 */
public interface Interactable {
    public void detectInteractions();                                               // Used to detect interactions    (determine when to send or receive an interaction)
    public void processInteraction(Interactable sender, int interactionType);       // Used to process an interaction (processes an interaction from another Interactable)
}