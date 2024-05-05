package com.mygdx.game;
                                                                                                                                                                                                        
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.utils.IBLBuilder;
import net.mgsx.gltf.scene3d.scene.SceneManager;

/**
 *
 * @author Jason Ciampa
 */
public class WorldsBestSpeedEaterSimulator extends ApplicationAdapter {
    
    // FIELDS // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Player
    private Player player;
    
    // 2D Camera
    private static OrthographicCamera camera2D;                                                                             // A Camera for 2D graphics
    
    // Drawer
    private Drawer drawer;                                                                                                  // A Drawer for drawing graphics, text, etc.
    
    // Restaurant
    private static Restaurant currentRestaurant;                                                                            // A reference to the currently active Restaurant
    
    // Scene
    private static SceneManager sceneManager;                                                                              // Manages the Scene2D and all of its components
    
    // Scene 2D
    TitleScreen titleScreen;                                                                                               // A reference to the TitleScreen Scene2D
    GameUI gameUI;                                                                                                         // A reference to the GameUI Scene2D
    
    // Visuals
    private Cubemap environmentCubemap;                                                                                    // Cubemap for the environment of the Scene2D
    private Cubemap diffuseCubemap;                                                                                        // Cubemap for the irradiance of the Scene2D
    private Cubemap specularCubemap;                                                                                       // Cubemap for the radiance of the Scene2D
    private Texture brdfLUT;                                                                                               // Texturing for the Scene2D
    private DirectionalLightEx light;                                                                                      // Lighting for the Scene2D
    
    
    // METHODS // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    @Override
    public void create() {
        player = Player.getInstance();                                                                                     // Store a local reference to the Player

        // SceneManager Setup
        sceneManager = new SceneManager();                                                                                 // Initialize the SceneManager
        sceneManager.setCamera(player.getCamera().getView());                                                              // Set the Player's camera as the SceneManager's camera
        
        // Player Setup
        player.setLocation(16, 25, -20);                                                                            // Set the location of the Player to the given coordinates
        player.getCamera().getView().lookAt(50, 25, -20);                                                           // Set the Player to look at the given coordinates
        
        // 2D Camera Setup
        camera2D = new OrthographicCamera();                                                                              // Creates a new 2D Camera
        camera2D.setToOrtho(false, 1920, 1080);                                           // Camera sets the viewport to 1080p
        
        // Title Screen
        titleScreen = new TitleScreen();                                                                                  // Initializes the TitleScreen Scene2D
        titleScreen.load();                                                                                               // Loads the TitleScreen
        gameUI = new GameUI();                                                                                            // Initializes the GameUI
        
        // Drawer Setup
        drawer = Drawer.getInstance();                                                                                    // Store a local reference to the Drawer
        
        // Restaurant Setup
        currentRestaurant = new RestaurantOne();                                                                          // Initialize RestaurantOne and set it as the game's current Restaurant
        currentRestaurant.load(sceneManager);                                                                             // Load the current Restaurant and all of it's components
        
        // Light Setup
        light = new DirectionalLightEx();                                                                                  // Create the directional light and store it in light
        light.direction.set(1, -3, 1).nor();                                                                              // Set the direction of the light to be slightly off-centered
        light.color.set(Color.WHITE);                                                                                     // Set the light's color to white
        sceneManager.environment.add(light);                                                                              // Set light as sceneManager's light

        // IBL Setup (Image Based Lighting)
        IBLBuilder iblBuilder = IBLBuilder.createOutdoor(light);                                                         // Create the IBLBuilder with light and store it in iblBuilder
        environmentCubemap = iblBuilder.buildEnvMap(1024);                                                               // Build the environment bitmap with a size of 1024 and store it in environmentCubemap
        diffuseCubemap = iblBuilder.buildIrradianceMap(256);                                                          // Build the irradiance bitmap with a size of 256 and store it in diffuseCubemap
        specularCubemap = iblBuilder.buildRadianceMap(10);                                                     // Build the radiance bitmap with mip map levels of 10 and store it in specularCubemap
        iblBuilder.dispose();                                                                                             // Dispose of iblBuilder

        // Texture Setup
        brdfLUT = new Texture(Gdx.files.classpath("net/mgsx/gltf/shaders/brdfLUT.png"));                                 // Creates the texture for the environment

        // Scene2D Setup
        sceneManager.setAmbientLight(1f);                                                                                // Set the Scene2D's ambient light with a value of 1
        sceneManager.environment.set(new PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, brdfLUT));             // Create and set the Scene2D's texture to brdfLUT
        sceneManager.environment.set(PBRCubemapAttribute.createSpecularEnv(specularCubemap));                           // Create and set the Scene2D's specular envrionment
        sceneManager.environment.set(PBRCubemapAttribute.createDiffuseEnv(diffuseCubemap));                             // Create and set the Scene2D's diffuse environment
    }

    @Override
    public void resize(int width, int height) {
        sceneManager.updateViewport(width, height);                                                                     // Update the viewport
    }

    @Override
    public void render() {
        // DeltaTime
        float dt = Gdx.graphics.getDeltaTime();                                                                         // Get the amount of time that has passed since the last call to render and store it in dt

        // Scene2D Rendering & Updating
        Gdx.gl.glClearColor(0, 0, 0, 1);                                                                     // Initialize the screen as black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);                                            // Clear and redraw the bits on the screen?
        
        // Scene2D
        sceneManager.update(dt);                                                                                        // Update the Scene2D with dt                                                                                  
        sceneManager.render();                                                                                          // Render the Scene2D
        
        // Camera Rendering & Updating
        camera2D.update();                                                                                              // Updates the 2D camera once every frame
                                                                                                                        
        // Drawer
        drawer.getBatch().setProjectionMatrix(camera2D.combined);                                                       // Set the SpriteBatch to use the 2D camera's coordinate system
        drawer.getShapeRenderer().setProjectionMatrix(camera2D.combined);
        
        drawer.getBatch().begin();                                                                                       // Begin the SpriteBatch
        
        drawer.update(dt);                                                                                               // Update the Drawer
        drawer.render();                                                                                                 // Draw any text that the Drawer has ready
        
        if (titleScreen.active) {                                                                                        // If the TitleScreen is currently active...
            titleScreen.update();                                                                                            // Update the TitleScreen
            titleScreen.draw(drawer.getBatch());                                                                        // Draw the TitleScreen
        }
        
        drawer.getBatch().end();                                                                                         // End the SpriteBatch
        
        
        drawer.getBatch().begin();                                                                                       // Begin the SpriteBatch

        if (!titleScreen.active) {                                                                                       // If the TitleScreen is NOT active...
            player.update(dt);                                                                                              // Update the Player
            currentRestaurant.update(dt);                                                                                   // Update the current Restaurant
            
            gameUI.update();                                                                                                // Update the GameUI
            gameUI.draw(drawer.getBatch());                                                                            // Draw the GameUI
            drawer.drawEatingStats();                                                                                       // Draw the numbers representing eating speed and reputation

            if (player.getEating()) {                                                                                       // If the Player is currently eating...
                Mouse.update(Gdx.graphics.getDeltaTime());                                                                  // Update the Mouse
                Mouse.draw();                                                                                                  // Draw the Mouse's animation
                
                if (player.getZ() > 0) {                                                                                       // If the Player is in a Challenge Booth...
                    drawer.drawProgressBars();                                                                                    // Draw the progress bars
                }                                                                                                                                                                        
            }
        }
        
        drawer.getBatch().end();                                                                                       // End the SpriteBatch
    }

    @Override
    public void dispose() {
        sceneManager.dispose();
        currentRestaurant.dispose();
        environmentCubemap.dispose();
        diffuseCubemap.dispose();
        specularCubemap.dispose();
        brdfLUT.dispose();
    }
    
    public static OrthographicCamera getCamera2D() {
        return camera2D;
    }
    
    public static void setCurrentRestaurant(Restaurant newRestaurant) {
        Restaurant oldRestaurant = currentRestaurant;                                                                 // Store a local reference to the old Restaurant
        oldRestaurant.stopMusic();                                                                                    // Stop playing the music in the old Restaurant
                
        currentRestaurant = newRestaurant;                                                                            // Set the new Restaurant as the current Restaurant
        
        currentRestaurant.load(sceneManager);                                                                         // Load the current restaurant
        oldRestaurant.unload(sceneManager);                                                                           // Unload the old restaurant
        
        currentRestaurant.playMusic();
    }
    
    public static Restaurant getCurrentRestaurant() {
        return currentRestaurant;
    }
}