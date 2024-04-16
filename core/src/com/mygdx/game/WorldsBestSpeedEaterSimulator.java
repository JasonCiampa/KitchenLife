package com.mygdx.game;
                                                                                                                                                                                                        
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
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
    private OrthographicCamera camera2D;
    
    // Drawer
    private Drawer drawer;
    
    // Scenes
//    private Restaurant restaurant;
    
    // Assets
    private Asset cube;
    private TrainingBooth booth;                                                                                                                // Stores all necessary information about the booth Asset
    private Asset plate;                                                                                                                        // Stores all necessary information about the plate Asset
    private Asset bacon;                                                                                                                        // Stores all necessary information about the bacon Asset
    private Asset restaurant;
    
    // Scene
    private SceneManager sceneManager;                                                                                                          // Manages the Scene and all of its components
    
    // Visuals
    private Cubemap environmentCubemap;                                                                                                         // Cubemap for the environment of the Scene
    private Cubemap diffuseCubemap;                                                                                                             // Cubemap for the irradiance of the Scene
    private Cubemap specularCubemap;                                                                                                            // Cubemap for the radiance of the Scene
    private Texture brdfLUT;                                                                                                                    // Texturing for the Scene
    private DirectionalLightEx light;                                                                                                           // Lighting for the Scene
    
    // METHODS // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    @Override
    public void create() {

        // Player Setup
        player = Player.getInstance();                                                                                                          // Store a local reference to the Player
        player.setLocation(0, 25, 0);
        
        // 2D Camera Setup
        camera2D = new OrthographicCamera();                                                                                                    // Creates a new 2D Camera
        camera2D.setToOrtho(false, 1920, 1080);                                                                  // Camera sets the viewport to 1080p
        
        // Drawer Setup
        drawer = Drawer.getInstance();                                                                                                          // Store a local reference to the Drawer
                
        // Asset Setup
        cube = new Asset("models/tests/originTest.gltf", 10, 0, 0, 2, 2, 2);
        booth = new TrainingBooth(-7.8393f, 5.083f, -70.973f, 20);                                                                 // Create the booth asset with the given gltf file and xyz coords
        plate = new Asset("models/plate/TrainingBoothPlate.gltf", 0, 0, 0, 3.96f, 0.15f, 3.84f);            // Create the plate asset with the given gltf file and xyz coords
        bacon = new Asset("models/bacon/bacon.gltf", 0, 0.1f, 0, 1.74f, 0.274f, 2.21f);                     // Create the bacon asset with the given gltf file and xyz coords
        restaurant = new Asset("models/resturantWalls/resturant.gltf", 0, 0, 0, 231, 54.8f, 132);           // Create the restaurant asset with the given gltf file and xyz corods
        
        // Scene Setup
        sceneManager = new SceneManager();    
        sceneManager.addScene(cube.getBody());                                                                                           // Add booth to the SceneManager to make it manage the booth
        sceneManager.addScene(booth.getBody());                                                                                           // Add booth to the SceneManager to make it manage the booth
        sceneManager.addScene(plate.getBody());                                                                                           // Add plate to the SceneManager to make it manage the plate
        sceneManager.addScene(bacon.getBody());                                                                                           // Add bacon to the SceneManager to make it manage the bacon
        sceneManager.addScene(restaurant.getBody());                                                                                      // Add restaurant to the SceneManager to make it manage the restaurant
        sceneManager.setCamera(player.getCamera().getView());                                                                             // Set the Player's camera as the SceneManager's camera
                                 
        // Light Setup
        light = new DirectionalLightEx();                                                                                                       // Create the directional light and store it in light
        light.direction.set(1, -3, 1).nor();                                                                                              // Set the direction of the light to be slightly off-centered
        light.color.set(Color.WHITE);                                                                                                     // Set the light's color to white
        sceneManager.environment.add(light);                                                                                                    // Set light as sceneManager's light

        // IBL Setup (Image Based Lighting)
        IBLBuilder iblBuilder = IBLBuilder.createOutdoor(light);                                                                            // Create the IBLBuilder with light and store it in iblBuilder
        environmentCubemap = iblBuilder.buildEnvMap(1024);                                                                                 // Build the environment bitmap with a size of 1024 and store it in environmentCubemap
        diffuseCubemap = iblBuilder.buildIrradianceMap(256);                                                                               // Build the irradiance bitmap with a size of 256 and store it in diffuseCubemap
        specularCubemap = iblBuilder.buildRadianceMap(10);                                                                          // Build the radiance bitmap with mip map levels of 10 and store it in specularCubemap
        iblBuilder.dispose();                                                                                                                   // Dispose of iblBuilder

        // Texture Setup
        brdfLUT = new Texture(Gdx.files.classpath("net/mgsx/gltf/shaders/brdfLUT.png"));                                             // Creates the texture for the environment

        // Scene Setup
        sceneManager.setAmbientLight(1f);                                                                                                   // Set the Scene's ambient light with a value of 1
        sceneManager.environment.set(new PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, brdfLUT));                         // Create and set the Scene's texture to brdfLUT
        sceneManager.environment.set(PBRCubemapAttribute.createSpecularEnv(specularCubemap));                                          // Create and set the Scene's specular envrionment
        sceneManager.environment.set(PBRCubemapAttribute.createDiffuseEnv(diffuseCubemap));                                            // Create and set the Scene's diffuse environment
    }

    @Override
    public void resize(int width, int height) {
        sceneManager.updateViewport(width, height);                                                                                             // Update the Scene's viewport
    }

    @Override
    public void render() {
        // DeltaTime
        float dt = Gdx.graphics.getDeltaTime();                                                                                                 // Get the amount of time that has passed since the last call to render and store it in dt

        // Scene Rendering & Updating
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);                                                                    // Clear and redraw the bits on the screen?
        sceneManager.update(dt);                                                                                                          // Update the Scene with dt                                                                                  
        sceneManager.render();                                                                                                                  // Render the Scene
        
        player.update(dt);                                                                                                                      // Update the moveCam so that movement input from user can be processed

        // Camera Rendering & Updating
        camera2D.update();                                                                                                                      // Updates the 2D camera once every frame
        
        // Drawer
        drawer.update(dt);                                                                                                                      // Update the Drawer
        drawer.getBatch().setProjectionMatrix(camera2D.combined);                                                                     // Set the SpriteBatch to use the 2D camera's coordinate system
        
        drawer.getBatch().begin();                                                                                                              // Begin the SpriteBatch
        
        drawer.render();                                                                                                                        // Draw any text that the Drawer has ready
        
        drawer.getBatch().end();                                                                                                                // End the SpriteBatch
        
        booth.update(dt);                                                                                                                       // Update the Booth        
        // Debug
//        System.out.println("Player X: " + player.getX());
//        System.out.println("Player Y: " + player.getY());
//        System.out.println("Player Z: " + player.getZ() + "\n\n");
    }

    @Override
    public void dispose() {
        sceneManager.dispose();
        environmentCubemap.dispose();
        diffuseCubemap.dispose();
        specularCubemap.dispose();
        brdfLUT.dispose();
    }
}