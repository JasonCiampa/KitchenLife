package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 *
 * @author Jason Ciampa
 */
public abstract class Booth extends Asset implements Interactable, Updatable {

    // FIELDS // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    protected int speedReward;                                                                                                                      // How much eating speed to award the Player (measured in bites per second)
    protected boolean eatingRunOccurring;                                                                                                           // Whether or not an eating run is occurring
    protected double oneSecondTimer;                                                                                                                // A timer used to count each second
    
    protected float seatX;
    protected float seatY;
    protected float seatZ;
        
    protected Asset[] plates;
    protected Food[] food;
    
    // CONSTRUCTOR // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Creates a Booth with no food on any of its four plates
    public Booth(float x, float y, float z, int speedReward) {
      super("models/booth/booth.gltf", x, y, z, 41, 27.6f, 15.6f);                                                                                              // Calls the Asset constructor to create the Booth
      this.speedReward = speedReward;                                                                                                                                                         // Stores the speedReward for the Booth
      this.eatingRunOccurring = false;                                                                                                                                                        // Sets eatingRunOccurring to false
      this.oneSecondTimer = 1;                                                                                                                                                                // Sets the oneSecondTimer to one second
      
      this.seatX = (this.x - (this.length / 3));                                                                                                                                              // Set the x-coordinate of the seat in the Booth 
      this.seatY = 20;                                                                                                                                                                        // Set the y-coordinate of the seat in the Booth
              
      if (this.z < 0) {                                                                                                                                                                       // If the Booth's z-coordinate is less than 0...
        this.seatZ = (this.z + (this.width / 4));                                                                                                                                                 // The Booth is a Training Booth, so add to the Booth's z-coordinate to set the seat's z-coordinate towards the outer seat of the Booth
      }                                         
      else {                                                                                                                                                                                  // Otherwise...
          this.seatZ = (this.z - (this.width / 4));                                                                                                                                               // the Booth is a Challenge Booth, so subtract from the Booth's z-coordinate to set the seat's z-coordinate towards the outer seat of the Booth
      }
        
      this.plates = new Asset[4];                                                                                                                                                             // Initialize the Plates array with a size of 4
      this.plates[0] = new Asset("models/plate/TrainingBoothPlate.gltf", this.x - 1, -2.5f, this.z + 1.5f, 3.96f, 0.15f, 3.84f);                                // Create the plate asset with the given gltf file and xyz coords
      this.plates[1] = new Asset("models/plate/TrainingBoothPlate.gltf", this.x - 1, -2.5f, this.z + 15, 3.96f, 0.15f, 3.84f);                                  // Create the plate asset with the given gltf file and xyz coords
      this.plates[2] = new Asset("models/plate/TrainingBoothPlate.gltf", this.x + 6, -2.5f, this.z + 1.5f, 3.96f, 0.15f, 3.84f);                                // Create the plate asset with the given gltf file and xyz coords
      this.plates[3] = new Asset("models/plate/TrainingBoothPlate.gltf", this.x + 6, -2.5f, this.z + 15, 3.96f, 0.15f, 3.84f);                                  // Create the plate asset with the given gltf file and xyz coords
    }
    
    
    // Creates a Booth with a given food on each of its four plates
    public Booth(float x, float y, float z, int speedReward, Food food) {
      super("models/booth/booth.gltf", x, y, z, 41, 27.6f, 15.6f);                                                                                              // Calls the Asset constructor to create the Booth
      this.speedReward = speedReward;                                                                                                                                                         // Stores the speedReward for the Booth
      this.eatingRunOccurring = false;                                                                                                                                                        // Sets eatingRunOccurring to false
      this.oneSecondTimer = 1;                                                                                                                                                                // Sets the oneSecondTimer to one second
      
      this.seatX = (this.x - (this.length / 3));                                                                                                                                              // Set the x-coordinate of the seat in the Booth 
      this.seatY = 20;                                                                                                                                                                        // Set the y-coordinate of the seat in the Booth
              
      if (this.z < 0) {                                                                                                                                                                       // If the Booth's z-coordinate is less than 0...
        this.seatZ = (this.z + (this.width / 4));                                                                                                                                               // The Booth is a Training Booth, so add to the Booth's z-coordinate to set the seat's z-coordinate towards the outer seat of the Booth
      }                                         
      else {                                                                                                                                                                                  // Otherwise...
          this.seatZ = (this.z - (this.width / 4));                                                                                                                                             // the Booth is a Challenge Booth, so subtract from the Booth's z-coordinate to set the seat's z-coordinate towards the outer seat of the Booth
      }
      
      this.plates = new Asset[4];                                                                                                                                                             // Initialize the Plates array with a size of 4
      this.plates[0] = new Asset("models/plate/TrainingBoothPlate.gltf", this.x - 1, -2.5f, this.z + 1.5f, 3.96f, 0.15f, 3.84f);                                // Create the plate asset with the given gltf file and xyz coords
      this.plates[1] = new Asset("models/plate/TrainingBoothPlate.gltf", this.x - 1, -2.5f, this.z + 15, 3.96f, 0.15f, 3.84f);                                  // Create the plate asset with the given gltf file and xyz coords
      this.plates[2] = new Asset("models/plate/TrainingBoothPlate.gltf", this.x + 6, -2.5f, this.z + 1.5f, 3.96f, 0.15f, 3.84f);                                // Create the plate asset with the given gltf file and xyz coords
      this.plates[3] = new Asset("models/plate/TrainingBoothPlate.gltf", this.x + 6, -2.5f, this.z + 15, 3.96f, 0.15f, 3.84f);                                  // Create the plate asset with the given gltf file and xyz coords
    
      this.food = new Food[4];
      
      for (int i = 0; i < this.plates.length; i++) {
          this.food[i] = new Food(food.gltfFilePath, this.plates[i].x - 2.75f, this.plates[i].y + 14.25f, this.plates[i].z - 8, food.length, food.width, food.height);
      } 
    }
    
    // METHODS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Abstract Eating Run Methods
    
    protected abstract void startEatingRun();                                                                                                                                                 // Method used to handle the beginning of an eating run
    
    protected abstract void processEatingRun(float dt);                                                                                                                                       // Method used to process the events occurring during the eating run
    
    protected abstract void endEatingRun(boolean playerWon);                                                                                                                                  // Method used to handle the ending of an eating run
       
    
    // Interactable Methods
    
    @Override
    public void detectInteractions() {
        Player player = Player.getInstance();                                                                                                                                                 // Store a local reference to the Player 
        Drawer drawer = Drawer.getInstance();                                                                                                                                                 // Store a local reference to the Drawer 
        
        float dt = Gdx.graphics.getDeltaTime();                                                                                                                                               // Get the amount of time that has passed since the last call to render and store it in dt
        float distanceFromPlayer = this.getDistance(player.getX(), player.getY(), player.getZ());                                                                                       // Calculate the distance from this booth to the Player
        
        if (this.oneSecondTimer > 0) {                                                                                                                                                        // If the one second timer is still going...
            this.oneSecondTimer -= dt;                                                                                                                                                          // Decrement the timer by dt
        }
        else {                                                                                                                                                                                // Otherwise...
            this.oneSecondTimer = 1;                                                                                                                                                            // Reset the one second timer to one second
            
            
            // DEBUG
//            System.out.println("\n\nDistance From Booth to Player: " + distanceFromPlayer);
            System.out.println("\n\nPlayer Coords: X: " + player.getX() + ",   Y: " + player.getY() + ",   Z: " + player.getZ());
            System.out.println("Player Arm Coords: X: " + player.arms.getX() + ",   Y: " + player.arms.getY() + ",   Z: " + player.arms.getZ());

//            System.out.println("Booth Coords: X: " + this.x + ",   Y: " + this.y + ",   Z: " + this.z);
//            System.out.println("Booth Width: " + this.width);
//            System.out.println("Player Eating Speed: " + player.getEatingSpeed());
//            System.out.println("Player Eating Reputation: " + player.getEatingReputation());
        }

        
        if (distanceFromPlayer < (this.width + 2)) {                                                                                                                                          // If the Player is close enough to the edge of the Booth...
            drawer.displayMessage("Press 'E' to sit down", 250, 100, 1420, 200, 0.1f);                                                      // Display a message to the Player saying they can press 'e' to sit down
            
            if (Gdx.input.isKeyPressed(Input.Keys.E)) {                                                                                                                                         // If the 'e' key is pressed...
                this.processInteraction(player, 1);                                                                                                                              // Trigger the Player's interaction on the Booth
            }
        }
    }


    @Override
    public void processInteraction(Interactable sender, int interactionType) {
        Player player = Player.getInstance();                                                                                                                                                   // Store a local reference to the Player 
        player.setEating(true);                                                                                                                                                           // Trigger the Player's eating state
        
        // If the z-coordinate of the Booth is less than 0, then it must be a Training Booth
        if (this.z < 0) {                                                                                                                                                                       // If the Booth's z-coordinate is less than 0 (Training Booth)...
            player.sitDown(this.seatX, this.seatZ, 173, -63);                                                                                                                       // Set the Player to be seated in the outer right seat of the Booth facing forward
        }
        else {                                                                                                                                                                                  // Otherwise, this is a Challenge Booth...
            player.sitDown(this.seatX, this.seatZ, 173f, 18);                                                                                                                       // Set the Player to be seated in the outer right seat of the Booth facing forward
        }
        
        this.startEatingRun();                                                                                                                                                                  // Trigger the start of the eating run
    }
    
    
    // Updatable Methods
    
    @Override
    public void update(float dt) {
        Player player = Player.getInstance();                                                                                                                                                   // Store a reference to the Player

        if (this.eatingRunOccurring) {                                                                                                                                                          // If an eating run is occurring...
            player.setLocation(this.seatX, this.seatY, this.seatZ);                                                                                                                           // Ensure the Player hasn't moved from their seat
            this.processEatingRun(dt);                                                                                                                                                              // Process the eating run
        }
        else {                                                                                                                                                                                  // Otherwise, no eating run is occurring, so...
            this.detectInteractions();                                                                                                                                                          // Process interactions that may start a new eating run
        }
    }
   
    @Override
    public void render() {
        
    }
    
    
    // Getters
    public int getSpeedReward() {
        return this.speedReward;
    }
    
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
