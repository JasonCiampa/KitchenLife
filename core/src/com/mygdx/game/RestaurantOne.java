package com.mygdx.game;

import net.mgsx.gltf.scene3d.scene.SceneManager;

/**
 *
 * @author Jason Ciampa
 */
public class RestaurantOne extends Restaurant{
    
    public RestaurantOne() {
        super("models/resturantWalls/resturant.gltf", 150);
    }
    
    @Override
    public void load(SceneManager sceneManager) {
        Food bacon = new Food("models/bacon/bacon.gltf", 0, 0, 0, 1.74f, 0.274f, 2.21f);                                                                         // Create a 
        Food grilledCheese = new Food("models/grilled_cheese/grilledCheese.gltf", 0, 0, 0, 3.87f, 0.785f, 3.9f);
        Food burger = new Food("models/burger/burger.gltf", 0, 0, 0, 2.01f, 1.32f, 2f);
        Food cake = new Food("models/cake/cake.gltf", 0, 0, 0, 2.5f, 1.95f, 2.95f);
        
        this.trainingBooths[0] = new TrainingBooth(-10, 5, -70, 1, bacon);                                                                                                                        // Create the Breakfast Training Booth
        this.trainingBooths[1] = new TrainingBooth(40, 5, -70, 5, grilledCheese);                                                                                                                         // Create the Lunch Training Booth
        this.trainingBooths[2] = new TrainingBooth(90, 5, -70, 15, burger);                                                                                                                       // Create the Dinner Training Booth
        this.trainingBooths[3] = new TrainingBooth(140, 5, -70, 30, cake);                                                                                                                       // Create the Dessert Training Booth
        
        this.challengeBooths[0] = new ChallengeBooth(-10, 5, 25, 2, 1, 5, 100, this.breakfastComplete, bacon);                                       // Create the Breakfast Challenge Booth
        this.challengeBooths[1] = new ChallengeBooth(40, 5, 25, 10, 5, 7, 1000, this.lunchComplete, grilledCheese);                                  // Create the Lunch Challenge Booth
        this.challengeBooths[2] = new ChallengeBooth(90, 5, 25, 30, 15, 10, 10000, this.dinnerComplete, burger);                                     // Create the Dinner Challenge Booth
        this.challengeBooths[3] = new ChallengeBooth(140, 5, 25, 60, 30, 15, 50000, this.dessertComplete, cake);                                    // Create the Dessert Challenge Booth
        
        for (int i = 0; i < 4; i++) {
            this.booths[i + i] = this.trainingBooths[i];
            this.booths[i + i + 1] = this.challengeBooths[i];
        }
        
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
}
