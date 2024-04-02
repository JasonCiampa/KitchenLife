package com.mygdx.game;

/**
 *
 * @author Jason Ciampa
 */
public class Booth extends Asset implements Interactable {

    public Booth(float x, float y, float z) {
      super("booth/TrainingBooth.gltf", x, y, z);
        
      this.x = 41;
      this.y = 27.6f;
      this.z = 15.6f;
      
      // RYANS NUMBER: 48.758
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
