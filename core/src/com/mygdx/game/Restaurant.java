package com.mygdx.game;

import java.util.ArrayList;
import net.mgsx.gltf.scene3d.scene.SceneManager;

/**
 *
 * @author Jason Ciampa
 */
public class Restaurant extends Asset implements Updatable {
    
    // FIELDS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private Booth[] booths;                                                                                                                                                                         // An array holding all of the Restaurant's Booths
    private TrainingBooth[] trainingBooths;                                                                                                                                                         // An array holding all of the Restaurant's Training Booths
    private ChallengeBooth[] challengeBooths;                                                                                                                                                       // An array holding all of the Restaurant's Challenge Booths
    
    private boolean breakfastComplete;                                                                                                                                                              // Indicates whether or not the Breakfast Challenge Booth has been completed
    private boolean lunchComplete;                                                                                                                                                                  // Indicates whether or not the Lunch Challenge Booth has been completed
    private boolean dinnerComplete;                                                                                                                                                                 // Indicates whether or not the Dinner Challenge Booth has been completed
    private boolean dessertComplete;                                                                                                                                                                // Indicates whether or not the Dessert Challenge Booth has been completed
    
    // CONSTRUCTOR // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public Restaurant(String gltfFilePath, float x, float y, float z, float length, float width, float height) {
        super(gltfFilePath, x, y, z, length, width, height);                                                                                                                                        // Call the Asset constructor
        
        this.booths = new Booth[8];                                                                                                                                                                 // Initialize the empty array of Booths with size 8 (four Training Booths and four Challenge Booths)
        this.trainingBooths = new TrainingBooth[8];
        this.challengeBooths = new ChallengeBooth[8];
        
        this.breakfastComplete = false;                                                                                                                                                             // Initialize the Breakfast Challenge Booth's record as unbroken
        this.lunchComplete = false;                                                                                                                                                                 // Initialize the Lunch Challenge Booth's record as unbroken
        this.dinnerComplete = false;                                                                                                                                                                // Initialize the Dinner Challenge Booth's record as unbroken
        this.dessertComplete = false;                                                                                                                                                               // Initialize the Dessert Challenge Booth's record as unbroken
        
        Food bacon = new Food("models/bacon/bacon.gltf", 0, 0, 0, 1.74f, 0.274f, 2.21f);                                                                         // Create a 
//        Food bltSandwich = new Food("models/blt/blt.gltf", 0, 0, 0, ?, ?, ?);
        Food burger = new Food("models/burger/burger.gltf", 0, 0, 0, 2.01f, 1.32f, 2f);
        Food cake = new Food("models/cake/cake.gltf", 0, 0, 0, 2.5f, 1.95f, 2.95f);
        
        this.trainingBooths[0] = new TrainingBooth(-10, 5, -70, 1, bacon);                                                                                                                        // Create the Breakfast Training Booth
        this.trainingBooths[1] = new TrainingBooth(40, 5, -70, 5);                                                                                                                         // Create the Lunch Training Booth
        this.trainingBooths[2] = new TrainingBooth(90, 5, -70, 15, burger);                                                                                                                       // Create the Dinner Training Booth
        this.trainingBooths[3] = new TrainingBooth(140, 5, -70, 30, cake);                                                                                                                       // Create the Dessert Training Booth
        
        this.challengeBooths[0] = new ChallengeBooth(-10, 5, 25, 2, 1, 5, 100, this.breakfastComplete, bacon);                                       // Create the Breakfast Challenge Booth
        this.challengeBooths[1] = new ChallengeBooth(40, 5, 25, 10, 5, 7, 1000, this.lunchComplete);                                          // Create the Lunch Challenge Booth
        this.challengeBooths[2] = new ChallengeBooth(90, 5, 25, 30, 15, 10, 10000, this.dinnerComplete, burger);                                     // Create the Dinner Challenge Booth
        this.challengeBooths[3] = new ChallengeBooth(140, 5, 25, 60, 30, 15, 100000, this.dessertComplete, cake);                                    // Create the Dessert Challenge Booth
        
        for (int i = 0; i < 4; i++) {
            this.booths[i + i] = this.trainingBooths[i];
            this.booths[i + i + 1] = this.challengeBooths[i];
        }
        
    }
    
    
    
    // METHODS // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void load(SceneManager sceneManager) {
        sceneManager.addScene(this.body);

        for (Booth booth : this.booths) {
            sceneManager.addScene(booth.getBody());                                                                                                                                           // Add booth to the SceneManager to make it manage the booth
            
            for (Asset plate : booth.plates) {
                sceneManager.addScene(plate.getBody());
            }
            
            if (booth.food != null) {
                for (Food food : booth.food) {
                    sceneManager.addScene(food.getBody());
                }
            }
        }
    }
    
    public Booth[] getBooths() {
        return this.booths;
    }
    
    
    // Updatable Methods
    
    @Override
    public void update(float dt) {
        this.trainingBooths[0].update(dt);
        this.challengeBooths[0].update(dt);
        
        for (int i = 1; i < this.challengeBooths.length; i++) {
            if (this.challengeBooths[i - 1].getRecordBroken()) {                                                                                                                                                          // If the Challenge Booth record time was broken...
                this.trainingBooths[i].update(dt);                                                                                                                                                                        // Update the Booth
                this.challengeBooths[i].update(dt);                                                                                                                                                                       // Update the Booth
            }
            else {
                break;
            }
        }
    }
    
    @Override
    public void render() {
        
    }
}
