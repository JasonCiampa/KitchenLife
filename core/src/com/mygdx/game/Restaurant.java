package com.mygdx.game;

import java.util.ArrayList;
import net.mgsx.gltf.scene3d.scene.SceneManager;

/**
 *
 * @author Jason Ciampa
 */
public abstract class Restaurant extends Asset implements Updatable {
    
    // FIELDS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    protected Booth[] booths;                                                                                                                                                                         // An array holding all of the Restaurant's Booths
    protected TrainingBooth[] trainingBooths;                                                                                                                                                         // An array holding all of the Restaurant's Training Booths
    protected ChallengeBooth[] challengeBooths;                                                                                                                                                       // An array holding all of the Restaurant's Challenge Booths
    
    protected boolean breakfastComplete;                                                                                                                                                              // Indicates whether or not the Breakfast Challenge Booth has been completed
    protected boolean lunchComplete;                                                                                                                                                                  // Indicates whether or not the Lunch Challenge Booth has been completed
    protected boolean dinnerComplete;                                                                                                                                                                 // Indicates whether or not the Dinner Challenge Booth has been completed
    protected boolean dessertComplete;                                                                                                                                                                // Indicates whether or not the Dessert Challenge Booth has been completed
    
    protected int reputationGoal;
    
    // CONSTRUCTOR // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public Restaurant(String gltfFilePath, int reputationGoal) {
        super(gltfFilePath, 0, 0, 0, 231, 54.8f, 132);                                                                                                                                        // Call the Asset constructor
        
        this.booths = new Booth[8];                                                                                                                                                                 // Initialize the empty array of Booths with size 8 (four Training Booths and four Challenge Booths)
        this.trainingBooths = new TrainingBooth[4];
        this.challengeBooths = new ChallengeBooth[4];
        
        this.breakfastComplete = false;                                                                                                                                                             // Initialize the Breakfast Challenge Booth's record as unbroken
        this.lunchComplete = false;                                                                                                                                                                 // Initialize the Lunch Challenge Booth's record as unbroken
        this.dinnerComplete = false;                                                                                                                                                                // Initialize the Dinner Challenge Booth's record as unbroken
        this.dessertComplete = false;                                                                                                                                                               // Initialize the Dessert Challenge Booth's record as unbroken
        
        this.reputationGoal = reputationGoal;
    }
    
    
    // METHODS // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public abstract void load(SceneManager sceneManager);
    
    public void unload(SceneManager sceneManager) {
        sceneManager.removeScene(this.body);

        for (Booth booth : this.booths) {
            sceneManager.removeScene(booth.getBody());                                                                                                                                           // Add booth to the SceneManager to make it manage the booth
            
            for (Asset plate : booth.plates) {
                sceneManager.removeScene(plate.getBody());
            }
            
            if (booth.food != null) {
                for (Food food : booth.food) {
                    sceneManager.removeScene(food.getBody());
                }
            }
        }    
    }
    
    @Override
    public void update(float dt) {
        this.trainingBooths[0].update(dt);
        this.challengeBooths[0].update(dt);
        
        
        for (int i = 1; i < this.challengeBooths.length; i++) {
            if (this.challengeBooths[i - 1].getRecordBroken()) {                                                                                                                                                          // If the Challenge Booth record time was broken...
                this.trainingBooths[i].update(dt);                                                                                                                                                                        // Update the Booth
                this.challengeBooths[i].update(dt);     
            }
            else {
                break;
            }
        }
    }
    
    @Override
    public void render() {
        
    }
    
    
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
}
