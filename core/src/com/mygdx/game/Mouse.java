package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;

/**
 *
 * @author Jason Ciampa
 */

// There is a bunch of Drawing logic built into this Mouse class rather than the Drawer class
// Although that logic might be better placed in the Drawer class, I still felt it was fine to put it here since the Mouse click is what causes the Sprites to appear
// And because the Drawer is still being used

public class Mouse {
    
    // FIELDS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private static Vector3 gameCoordinates = new Vector3();                                          // A Vector3 to hold the Mouse's converted coordinates (Mouse coordinates to Game coordinates)
    private static boolean clickCooldown = false;                                                    // A timer to limit click speed
    
    private static float animationTimer = 0;                                                         // A timer for the animation
    private static boolean playAnimation = false;                                                    // A boolean that will determine whether the animation should be played
    
    private static ArrayList<Sprite[]> sprites = new ArrayList<Sprite[]>();                         // An ArrayList to hold each Sprite of the animation 
    
    
    // CONSTRUCTOR  // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private Mouse() {
        // This class doesn't need to be instantiated, all methods are static
    }

    
    // SPRITE INNER CLASS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    private static class Sprite {
        // SPRITE FIELDS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        
        private int startX;                                                                         // Holds the starting x-coordinate of the Sprite
        private int startY;                                                                         // Holds the starting y-coordinate of the Sprite
        
        private int currentX;                                                                       // Holds the current x-coordinate of the Sprite
        private int currentY;                                                                       // Holds the current y-coordinate of the Sprite
        
        
        // SPRITE CONSTRUCTOR // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        
        private Sprite(int startX, int startY) {
            this.startX = startX;                                                                   // Stores the starting x-coordinate of the Sprite
            this.startY = startY;                                                                   // Stores the starting y-coordinate of the Sprite
            
            this.currentX = startX;                                                                 // Stores the current x-coordinate of the Sprite   
            this.currentY = startY;                                                                 // Stores the current y-coordinate of the Sprite   
        }
        
        
        // SPRITE METHODS // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        private void move(int moveX, int moveY) {
            this.currentX += moveX;                                                                 // Increment the current x-coordinate
            this.currentY += moveY;                                                                 // Increment the current y-coordinate
        }
        
        private void draw() {
            Drawer.getInstance().drawImage("images/mouth.png", this.currentX, this.currentY);   // Draw a mouth image
        }
    }
    
    // METHODS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Updates the Mouse
    public static void update(float dt) {
        if (Player.getInstance().getEating()) {
            for (int i = sprites.size() - 1; i >= 0; i--) {                                             // For every sprite in the list (iterating backwards)...
                if ((sprites.get(i)[0].startX - sprites.get(i)[0].currentX) > 40) {             // If the Sprite has moved 40 px...
                    sprites.remove(i);                                                                  // Remove the Sprite from the list
                }
                else {                                                                                    // Otherwise...
                    sprites.get(i)[0].currentX -= 5;                                                    // Decrement current x-coordinate of the first Sprite in the batch of four
                    sprites.get(i)[0].currentY += 5;                                                    // Increment current y-coordinate of the first Sprite in the batch of four

                    sprites.get(i)[1].currentX += 5;                                                    // Increment current x-coordinate of the second Sprite in the batch of four
                    sprites.get(i)[1].currentY += 5;                                                    // Increment current y-coordinate of the second Sprite in the batch of four

                    sprites.get(i)[2].currentX -= 5;                                                    // Decrement current x-coordinate of the third Sprite in the batch of four
                    sprites.get(i)[2].currentY -= 5;                                                    // Decrement current y-coordinate of the third Sprite in the batch of four

                    sprites.get(i)[3].currentX += 5;                                                    // Increment current x-coordinate of the fourth Sprite in the batch of four
                    sprites.get(i)[3].currentY -= 5;                                                    // Decrement current y-coordinate of the fourth Sprite in the batch of four
                }
            }
        }
        else {
            sprites.clear();
        }
    }
    
    // Draws any Sprites that the Mouse has gathered
    public static void draw() {
        if (Player.getInstance().getEating()) {
            for (Sprite[] clickSprites : sprites) {                                                     // For every Sprite array in the Mouse's ArrayList of sprites
                for (Sprite sprite : clickSprites) {                                                        // For every Sprite in the Sprite array...
                    sprite.draw();                                                                              // Draw the Sprite
                }
            }
        }
        else {
            sprites.clear();
        }

    }
    
    // Returns the mouse's x-coordinate
    public static int getX() {
        gameCoordinates.x = Gdx.input.getX();                                                       // Get the mouse's x-coordinate in mouseCoords
        WorldsBestSpeedEaterSimulator.getCamera2D().unproject(gameCoordinates);                     // Convert from mouseCoords to gameCoords
        return (int) gameCoordinates.x;                                                             // Return the x-coordinate in gameCoords
    }
    
    // Returns the mouse's y-coordinate
    public static int getY() {
        gameCoordinates.y = Gdx.input.getY();                                                       // Get the mouse's y-coordinate in mouseCoords
        WorldsBestSpeedEaterSimulator.getCamera2D().unproject(gameCoordinates);                     // Convert from mouseCoords to gameCoords
        return (int) gameCoordinates.y;                                                             // Return the y-coordinate in gameCoords
    }
    
    // Determines whether or not the mouse was clicked.
    public static boolean checkClick() {        
        if ((clickCooldown) && !Gdx.input.isTouched()) {                                            // If the Mouse has been let go since the last click...
            clickCooldown = false;                                                                      // Disable the click cooldown
        }
        else if ((!clickCooldown) && Gdx.input.isTouched()) {                                       // Otherwise, if the Mouse has been let go since the last click and has been clicked again...
            clickCooldown = true;                                                                       // Enable the click cooldown   
            
            if (Player.getInstance().getEating()) {
                Sprite[] clickSprites = new Sprite[4];                                                      // Create a local Sprite array with size 4

                for (int i = 0; i < 4; i++) {                                                               // For 4 iterations...
                    clickSprites[i] = new Sprite(Mouse.getX(), Mouse.getY());                       // Create a new Sprite and store it in the local Sprite array
                }

                sprites.add(clickSprites);                                                                // Add the newly filled Sprite array into the ArrayList of Sprite arrays
            }
            else {
                sprites.clear();
            }
            
            return true;                                                                                // Return true to indicate a click has occurred
        }
        
        return false;                                                                               // Return false to indicate a click hasn't occurred
    }
    
    // Checks if the mouse is hovering over an area defined by the arguments
    public static boolean checkHover(int x, int y, int width, int height) {
        int mouseX = getX();                                                                        // Sets mouseX equal to the Mouse's x-coordinate in game coordinates
        int mouseY = getY();                                                                        // Sets mouseY equal to the Mouse's y-coordinate in game coordinates
        
        if ((mouseX >= x && mouseX <= (x + width)) && (mouseY >= y && mouseY <= (y + height))) {    // If the mouse is inside of the rectangle...
            return true;                                                                                // Return true to indicate that the mouse is inside of the rectangle
        }
        return false;                                                                               // Return false to indicate that the mouse is not inside of the rectangle
    }
    
    
}

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//