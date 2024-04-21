package com.mygdx.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class Mouse {
    
    // FIELDS
    private static Vector3 gameCoordinates = new Vector3();                                          // A Vector3 to hold the Mouse's converted coordinates (Mouse coordinates to Game coordinates)
    private static boolean clickCooldown;                                                            // A timer to limit click speed
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // CONSTRUCTOR
    private Mouse() {
        // This class doesn't need to be instantiated, all methods are static
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // METHODS
    
    // Updates the Mouse
    public static void update(float dt) {

    }
    
    // Returns the mouse's x-coordinate
    public static int getX() {
        gameCoordinates.x = Gdx.input.getX();                                                       // Get the mouse's x-coordinate in mouseCoords
        WorldsBestSpeedEaterSimulator.getCamera2D().unproject(gameCoordinates);                                                   // Convert from mouseCoords to gameCoords
        return (int) gameCoordinates.x;                                                             // Return the x-coordinate in gameCoords
    }
    
    // Returns the mouse's y-coordinate
    public static int getY() {
        gameCoordinates.y = Gdx.input.getY();                                                       // Get the mouse's y-coordinate in mouseCoords
        WorldsBestSpeedEaterSimulator.getCamera2D().unproject(gameCoordinates);                                                   // Convert from mouseCoords to gameCoords
        return (int) gameCoordinates.y;                                                             // Return the y-coordinate in gameCoords
    }
    
    // Determines whether or not the mouse was clicked.
    public static boolean checkClick() {
        if (clickCooldown && (!Gdx.input.isTouched())) {                                            // If the Mouse has been let go since the last click...
            clickCooldown = false;                                                                      // Disable the click cooldown
        }
        else if ((!clickCooldown) && Gdx.input.isTouched()) {                                       // Otherwise, if the Mouse has been let go since the last click and has been clicked again...
            clickCooldown = true;                                                                       // Enable the click cooldown   
            return true;                                                                                // Return true to indicate a click has occurred
        }
        
        return false;
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