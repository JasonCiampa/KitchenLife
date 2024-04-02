package com.mygdx.game;

/**
 *
 * @author Jason Ciampa
 */
public class Booth implements Interactable {

    public Booth() {
      
    }
    
    @Override
    public void processInteractions() {
//        if (booth is close enough to player.getInstance()) {
//            if ('e' key is pressed) {
//                receiveInteraction(this, eWasPressed);
//            }
//        }
    }

    @Override
    public void sendInteraction(Interactable recipient, int interactionType) {
        // The booth does not send interactions
    }

    @Override
    public void receiveInteraction(Interactable sender, int interactionType) {
        Player player = Player.getInstance();
        player.setLocation(-13, 17, 8.5f);
        player.getCamera().getView().lookAt(15.64f, 18, 9);
    }
    
}
