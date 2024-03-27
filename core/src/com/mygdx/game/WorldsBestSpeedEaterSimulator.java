package com.mygdx.game;
                                                                                                                                                                                                        
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;

import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.utils.IBLBuilder;

import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;

import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;

public class WorldsBestSpeedEaterSimulator extends ApplicationAdapter
{
    // Cameras
    private PerspectiveCamera visualCam;                                                                                                        // Controls the visualization of the viewport
    private FirstPersonCameraController moveCam;                                                                                                // Controls the movement around the viewport
    
    // Assets
    private Asset cone;                                                                                                                         // Stores all necessary information about the cone Asset
    private Asset booth;                                                                                                                        // Stores all necessary information about the booth Asset
    private Asset plate;                                                                                                                        // Stores all necessary information about the plate Asset
    private Asset bacon;                                                                                                                        // Stores all necessary information about the bacon Asset

    
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

        // Camera Setup
        visualCam = new PerspectiveCamera(40f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());         // Create the visual camera and store it in visualCam
        visualCam.position.set(0,50, 0);                                                                                                   // Set the position of the visualCam to the center

        moveCam = new FirstPersonCameraController(visualCam);                                                                             // Create the move camera and store it in moveCam
        Gdx.input.setInputProcessor(moveCam);                                                                                                // Set the input processor for our game to be based on the moveCam's input processing methods
        
        
        // Asset Setup
        cone = new Asset("cone/conemove.gltf", 0, 49, -5, "ConeDog", true);                           // Create the cone asset with the given gltf file, xyz coords, and animation details
        booth = new Asset("booth/TrainingBooth.gltf", 0, -5, -40);                                                            // Create the booth asset with the given gltf file and xyz coords
        plate = new Asset("plate/TrainingBoothPlate.gltf", 0, 0, 0);                                                          // Create the plate asset with the given gltf file and xyz coords
//        bacon = new Asset("bacon/Bacon.gltf", 0, 0.1f, 0);                                                                       // Create the bacon asset with the given gltf file and xyz coords

        
        // Scene Setup
        sceneManager = new SceneManager();                                                                                                      // Create the SceneManager
        sceneManager.addScene(cone.getBody());                                                                                            // Add cone to the SceneManager to make it manage the cone
        sceneManager.addScene(booth.getBody());                                                                                           // Add booth to the SceneManager to make it manage the booth
        sceneManager.addScene(plate.getBody());                                                                                           // Add plate to the SceneManager to make it manage the plate
//        sceneManager.addScene(bacon.getBody());                                                                                           // Add bacon to the SceneManager to make it manage the bacon
        sceneManager.setCamera(visualCam);                                                                                                // Set visualCam as sceneManager's camera
                                 
        
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

        // Camera Rendering & Updating
        moveCam.update(dt);                                                                                                            // Update the moveCam so that movement input from user can be processed

        // Scene Rendering & Updating
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);                                                                    // Clear and redraw the bits on the screen?
        sceneManager.update(dt);                                                                                                          // Update the Scene with dt                                                                                  
        sceneManager.render();                                                                                                                  // Render the Scene
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