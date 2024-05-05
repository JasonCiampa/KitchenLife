package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import java.util.ArrayList;
import net.mgsx.gltf.scene3d.scene.SceneManager;

/**
 *
 * @author Jason Ciampa
 */
public abstract class Restaurant extends Asset {
    
    // FIELDS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    protected Booth[] booths;                                                                             // An array holding all of the Restaurant's Booths
    protected TrainingBooth[] trainingBooths;                                                             // An array holding all of the Restaurant's Training Booths
    protected ChallengeBooth[] challengeBooths;                                                           // An array holding all of the Restaurant's Challenge Booths
    
    protected boolean breakfastComplete;                                                                  // Indicates whether or not the Breakfast Challenge Booth has been completed
    protected boolean lunchComplete;                                                                      // Indicates whether or not the Lunch Challenge Booth has been completed
    protected boolean dinnerComplete;                                                                     // Indicates whether or not the Dinner Challenge Booth has been completed
    protected boolean dessertComplete;                                                                    // Indicates whether or not the Dessert Challenge Booth has been completed
    
    protected int reputationGoal;                                                                         // An int that represents how much reputation is needed to successfully complete the Restaurant
    
    protected Music backgroundMusic;                                                                      // Background music to be played while the scene is active

    
    // CONSTRUCTOR // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public Restaurant(String gltfFilePath, int reputationGoal, String pathToBackgroundMusic) {
        super(gltfFilePath, 0, 0, 0, 231, 54.8f, 132);                           // Call the Asset constructor
        
        this.booths = new Booth[8];                                                                      // Initialize the empty array of Booths with size 8 (four Training Booths and four Challenge Booths)
        this.trainingBooths = new TrainingBooth[4];                                                      // Initialize the empty array of Training Booths with size 4
        this.challengeBooths = new ChallengeBooth[4];                                                    // Initialize the empty array of Challenge Booths with size 4
        
        this.breakfastComplete = false;                                                                  // Initialize the Breakfast Challenge Booth's record as unbroken
        this.lunchComplete = false;                                                                      // Initialize the Lunch Challenge Booth's record as unbroken
        this.dinnerComplete = false;                                                                     // Initialize the Dinner Challenge Booth's record as unbroken
        this.dessertComplete = false;                                                                    // Initialize the Dessert Challenge Booth's record as unbroken
        
        this.reputationGoal = reputationGoal;                                                            // Initialize the reputation goal value
        
        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(pathToBackgroundMusic));   // Sets background music
    }
    
    
    // METHODS // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public abstract void load(SceneManager sceneManager);                                               // An abstract load method for each Restaurant subclass to individually define
    
    // Handles the unloading of the Retaurant and all of it's components
    public void unload(SceneManager sceneManager) {
        sceneManager.removeScene(this.body);                                                      // Remove the Restaurant's model from the Scene

        for (Booth booth : this.booths) {                                                               // For every Booth in the Restaurant...
            sceneManager.removeScene(booth.getBody());                                                      // Remove the Booth's model from the Scene
            
            for (Asset plate : booth.plates) {                                                              // For every plate in the Restaurant...
                sceneManager.removeScene(plate.getBody());                                                      // Remove the Plate's model from the Scene
            }
            
            if (booth.food != null) {                                                                       // If the Booth does have food in it...
                for (Food food : booth.food) {                                                                  // For every Food in the Booth...
                    sceneManager.removeScene(food.getBody());                                                       // Remove the Food's model from the Scene
                }
            }
        }    
    }
    
    // Update the Restaurant and all of it's components
    public void update(float dt) {
        this.trainingBooths[0].update(dt);                                                              // Update the first Training Booth (this has to be active by default when starting a level)
        this.challengeBooths[0].update(dt);                                                             // Update the first Challenege Booth (this has to be active by default when starting a level)
        
        for (int i = 1; i < this.challengeBooths.length; i++) {                                         // For every ChallengeBooth except the first one...
            if (this.challengeBooths[i - 1].getRecordBroken()) {                                            // If the Challenge Booth record time was broken...
                this.trainingBooths[i].update(dt);                                                              // Update the Training Booth that corresponds to the Challenge Booth (allows it to be sat down in)
                this.challengeBooths[i].update(dt);                                                             // Update the Challenge Booth (allows it to be sat down in)
            }
            else {                                                                                      // Otherwise, the ChallengeBooth hasn't been beaten yet, so...
                break;                                                                                      // Break out from the loop, no other ChallengeBooths will be broken
            }
        }
    }
    
    // Plays the Restaurant's background music
    public void playMusic() {
        this.backgroundMusic.setLooping(true);                                                               // Enables looping for the Scene2D's background music
        this.backgroundMusic.play();                                                                             // Begins to play the Scene2D's background music
        this.backgroundMusic.setVolume((float) 0.25);                                                            // Lowers the volume to 25% for the Scene2D's background music
        System.out.println("I'm in...");
    }
    
    // Stops the Restaurant's background music
    public void stopMusic() {
        this.backgroundMusic.stop();                                                                            // Stop the background music of the Scene2D
    }
    
    
    // GETTER METHODS // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public Booth[] getBooths() {
        return this.booths;
    }
    
    public TrainingBooth[] getTrainingBooths() {
        return this.trainingBooths;
    }
    
    public ChallengeBooth[] getChallengeBooths() {
        return this.challengeBooths;
    }
    
    public int getReputationGoal() {
        return this.reputationGoal;
    }
    
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}
