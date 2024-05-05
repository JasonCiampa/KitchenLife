package com.mygdx.game;

import net.mgsx.gltf.scene3d.scene.SceneManager;

/**
 *
 * @author Jason Ciampa
 */
public class RestaurantTwo extends Restaurant{
    
    // CONSTRUCTOR // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public RestaurantTwo() {
        super("models/resturantWalls/resturant2.gltf", 1000, "music/Comet Observatory - Super Mario Galaxy.mp3");
    }
    
    // LOAD METHOD // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    @Override
    public void load(SceneManager sceneManager) {
        Food bacon = new Food("models/bacon/bacon.gltf", 0, 0, 0, 1.74f, 0.274f, 2.21f);                                                             // Create the Bacon Food
        Food grilledCheese = new Food("models/grilled_cheese/grilledCheese.gltf", 0, 0, 0, 3.87f, 0.785f, 3.9f);                                     // Create the Grilled Cheese Food
        Food burger = new Food("models/burger/burger.gltf", 0, 0, 0, 2.01f, 1.32f, 2f);                                                              // Create the Burger Food
        Food cake = new Food("models/cake/cake.gltf", 0, 0, 0, 2.5f, 1.95f, 2.95f);                                                                  // Create the Cake Food
        
        this.trainingBooths[0] = new TrainingBooth(-10, 5, -70, 50, bacon);                                                                                         // Create the Breakfast Training Booth
        this.trainingBooths[1] = new TrainingBooth(40, 5, -70, 65, grilledCheese);                                                                                  // Create the Lunch Training Booth
        this.trainingBooths[2] = new TrainingBooth(90, 5, -70, 80, burger);                                                                                        // Create the Dinner Training Booth
        this.trainingBooths[3] = new TrainingBooth(140, 5, -70, 100, cake);                                                                                         // Create the Dessert Training Booth
        
        this.challengeBooths[0] = new ChallengeBooth(-10, 5, 25, 25, 45, 5, 10000, this.breakfastComplete, bacon);             // Create the Breakfast Challenge Booth
        this.challengeBooths[1] = new ChallengeBooth(40, 5, 25, 30, 60, 7, 20000, this.lunchComplete, grilledCheese);        // Create the Lunch Challenge Booth
        this.challengeBooths[2] = new ChallengeBooth(90, 5, 25, 40, 75, 10, 40000, this.dinnerComplete, burger);           // Create the Dinner Challenge Booth
        this.challengeBooths[3] = new ChallengeBooth(140, 5, 25, 50, 90, 15, 80000, this.dessertComplete, cake);           // Create the Dessert Challenge Booth
        
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
