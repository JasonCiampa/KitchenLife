package com.mygdx.game;
                                                                                                                                                                                                        
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;

import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.utils.IBLBuilder;

import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import net.mgsx.gltf.scene3d.scene.SceneManager;

public class WorldsBestSpeedEaterSimulator extends ApplicationAdapter
{
    // Player
    private Player player;
    
    // 2D Camera
    private OrthographicCamera camera2D;
    
    // Drawer
    private Drawer drawer;
    
    // Scenes
//    private Restaurant restaurant;
    private Booth boothTest;
    
    // Assets
    private Asset cone;                                                                                                                         // Stores all necessary information about the cone Asset
    private Asset booth;                                                                                                                        // Stores all necessary information about the booth Asset
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
    
    @Override
    public void create() {

        // Player Setup
        player = Player.getInstance();
        Gdx.input.setInputProcessor(player.getCamera());                                                                                                // Set the input processor for our game to be based on the moveCam's input processing methods
        
        // 2D Camera Setup
        camera2D = new OrthographicCamera();                                                                                                // Creates a new 2D Camera
        camera2D.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());                       // Camera sets the viewport to 1080p
        
        // Drawer Setup
        drawer = Drawer.getInstance();
        
        boothTest = new TrainingBooth(41, 27.6f, 15.6f, 20);
        
        // Asset Setup
//        cone = new Asset("cone/conemove.gltf", 0, 0, -5, "ConeDog", true);                           // Create the cone asset with the given gltf file, xyz coords, and animation details
        booth = new Asset("booth/TrainingBooth.gltf", 20, 0, 10, 41, 15.6f, 27.6f);                                                            // Create the booth asset with the given gltf file and xyz coords
        plate = new Asset("plate/TrainingBoothPlate.gltf", 0, 0, 0, 3.96f, 0.15f, 3.84f);                                                          // Create the plate asset with the given gltf file and xyz coords
        bacon = new Asset("bacon/bacon.gltf", 0, 0.1f, 0, 1.74f, 0.274f, 2.21f);                                                                       // Create the bacon asset with the given gltf file and xyz coords
        restaurant = new Asset("resturantWalls/resturant.gltf", 0, 0, 0, 231, 54.8f, 132);
        
        // Scene Setup
        sceneManager = new SceneManager();                                                                                                      // Create the SceneManager
//        sceneManager.addScene(cone.getBody());                                                                                            // Add cone to the SceneManager to make it manage the cone
        sceneManager.addScene(boothTest.getBody());                                                                                           // Add booth to the SceneManager to make it manage the booth
        sceneManager.addScene(plate.getBody());                                                                                           // Add plate to the SceneManager to make it manage the plate
        sceneManager.addScene(bacon.getBody());                                                                                           // Add bacon to the SceneManager to make it manage the bacon
        sceneManager.addScene(restaurant.getBody());
        sceneManager.setCamera(player.getCamera().getView());                                                                                                // Set visualCam as sceneManager's testCam
                                 
        
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
        
        player.update(dt);                                                                                                      // Update the moveCam so that movement input from user can be processed

        // Camera Rendering & Updating
        camera2D.update();                                                                                                      // Updates the 2D camera once every frame
        
        // Drawer
        drawer.getBatch().setProjectionMatrix(camera2D.combined);
        drawer.getBatch().begin();
        drawer.drawPlayerInfo();
        drawer.getBatch().end();

        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            boothTest.receiveInteraction(boothTest, 1);
        }
        
        System.out.println("Player X: " + player.getX());
        System.out.println("Player Y: " + player.getY());
        System.out.println("Player Z: " + player.getZ() + "\n\n");
    }

    @Override
    public void dispose() {
        sceneManager.dispose();
        cone.dispose();
        environmentCubemap.dispose();
        diffuseCubemap.dispose();
        specularCubemap.dispose();
        brdfLUT.dispose();
    }
}