package com.mygdx.game;

import net.mgsx.gltf.scene3d.scene.SceneManager;

/**
 *
 * @author Jason Ciampa
 */
public class RestaurantOne extends Restaurant {
    
    // CONSTRUCTOR // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public RestaurantOne() {
        super("models/resturantWalls/resturant.gltf", 100, "music/Happy Mambo - Kirby's Return to Dreamland Deluxe.mp3");
    }
    
    
    // LOAD METHOD // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    @Override
    public void load(SceneManager sceneManager) {
        Food bacon = new Food("models/bacon/bacon.gltf", 0, 0, 0, 1.74f, 0.274f, 2.21f);                                                             // Create the Bacon Food
        Food grilledCheese = new Food("models/grilled_cheese/grilledCheese.gltf", 0, 0, 0, 3.87f, 0.785f, 3.9f);                                     // Create the Grilled Cheese Food
        Food burger = new Food("models/burger/burger.gltf", 0, 0, 0, 2.01f, 1.32f, 2f);                                                              // Create the Burger Food
        Food cake = new Food("models/cake/cake.gltf", 0, 0, 0, 2.5f, 1.95f, 2.95f);                                                                  // Create the Cake Food
        
        this.trainingBooths[0] = new TrainingBooth(-10, 5, -70, 2, bacon);                                                                                      // Create the Breakfast Training Booth
        this.trainingBooths[1] = new TrainingBooth(40, 5, -70, 10, grilledCheese);                                                                               // Create the Lunch Training Booth
        this.trainingBooths[2] = new TrainingBooth(90, 5, -70, 20, burger);                                                                                     // Create the Dinner Training Booth
        this.trainingBooths[3] = new TrainingBooth(140, 5, -70, 30, cake);                                                                                      // Create the Dessert Training Booth
        
        this.challengeBooths[0] = new ChallengeBooth(-10, 5, 25, 2, 1, 5, 100, this.breakfastComplete, bacon);             // Create the Breakfast Challenge Booth
        this.challengeBooths[1] = new ChallengeBooth(40, 5, 25, 10, 5, 7, 1000, this.lunchComplete, grilledCheese);        // Create the Lunch Challenge Booth
        this.challengeBooths[2] = new ChallengeBooth(90, 5, 25, 30, 15, 10, 10000, this.dinnerComplete, burger);           // Create the Dinner Challenge Booth
        this.challengeBooths[3] = new ChallengeBooth(140, 5, 25, 60, 30, 15, 20000, this.dessertComplete, cake);           // Create the Dessert Challenge Booth
        
        for (int i = 0; i < 4; i++) {                                                                                                                                               // For 4 iterations...
            this.booths[i + i] = this.trainingBooths[i];                                                                                                                                // Add a Training Booth to the Booths array
            this.booths[i + i + 1] = this.challengeBooths[i];                                                                                                                           // Add a Challenge Booth to the Booths array
        }
        
        sceneManager.addScene(this.body);                                                                                                                                           // Add the model of this Restaurant into the Scene

        for (Booth booth : this.booths) {                                                                                                                                           // For every Booth in the Restaurant...
            sceneManager.addScene(booth.getBody());                                                                                                                                     // Add the Booth's model to the Scene
            
            for (Asset plate : booth.plates) {                                                                                                                                          // For every Plate in the Booth...
                sceneManager.addScene(plate.getBody());                                                                                                                                     // Add the Plate into the Scene
            }
            
            if (booth.food != null) {                                                                                                                                                   // If the Booth has a Food...
                for (Food food : booth.food) {                                                                                                                                              // For every Food in the Booth...
                    sceneManager.addScene(food.getBody());                                                                                                                                      // Add the Food's model to the Scene
                }
            }
        }
    }
}
