package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.scene.SceneSkybox;
import net.mgsx.gltf.scene3d.utils.IBLBuilder;

import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.math.Matrix4;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;

public class WorldsBestSpeedEaterSimulator extends ApplicationAdapter
{
	private SceneManager sceneManager;
	private SceneAsset player; 
	private Scene playerModel;
	private PerspectiveCamera camera;
	private Cubemap diffuseCubemap;
	private Cubemap environmentCubemap;
	private Cubemap specularCubemap;
	private Texture brdfLUT;
	private float time;
	private SceneSkybox skybox;
	private DirectionalLightEx light;
        private FirstPersonCameraController cameraController;

        // Player Movement
        float speed = 5f;
        float rotationSpeed = 10f;
        private Matrix4 playerTransform = new Matrix4();
        private final Vector3 moveTranslation = new Vector3();
        private final Vector3 currentPosition = new Vector3();
        
        // Camera
        private float camHeight = 20f;
        private float camPitch = -60f;
        

        
	@Override
	public void create() {

            // create playerModel
            player = new GLTFLoader().load(Gdx.files.internal("donought.gltf"));
            playerModel = new Scene(player.scene);
            sceneManager = new SceneManager();
            sceneManager.addScene(playerModel);

            // setup camera 
            camera = new PerspectiveCamera(60f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            float d = .02f;
            camera.near = 1f;
            camera.far = 200;
            sceneManager.setCamera(camera);
            camera.position.set(0, camHeight, 4f);

            cameraController = new FirstPersonCameraController(camera);
            Gdx.input.setInputProcessor(cameraController);


            // setup light
            light = new DirectionalLightEx();
            light.direction.set(1, -3, 1).nor();
            light.color.set(Color.WHITE);
            sceneManager.environment.add(light);

            // setup quick IBL (image based lighting)
            IBLBuilder iblBuilder = IBLBuilder.createOutdoor(light);
            environmentCubemap = iblBuilder.buildEnvMap(1024);
            diffuseCubemap = iblBuilder.buildIrradianceMap(256);
            specularCubemap = iblBuilder.buildRadianceMap(10);
            iblBuilder.dispose();

            // This texture is provided by the library, no need to have it in your assets.
            brdfLUT = new Texture(Gdx.files.classpath("net/mgsx/gltf/shaders/brdfLUT.png"));

            sceneManager.setAmbientLight(1f);
            sceneManager.environment.set(new PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, brdfLUT));
            sceneManager.environment.set(PBRCubemapAttribute.createSpecularEnv(specularCubemap));
            sceneManager.environment.set(PBRCubemapAttribute.createDiffuseEnv(diffuseCubemap));

            // setup skybox
            skybox = new SceneSkybox(environmentCubemap);
            sceneManager.setSkyBox(skybox);
            
            buildBoxes();
	}
        
        private void buildBoxes() {
            ModelBuilder modelBuilder = new ModelBuilder();
            modelBuilder.begin();
            
            for (int x = 0; x < 100; x += 10) {
                for (int z = 0; z < 100; z += 10) {
                    Material material = new Material();
                    material.set(PBRColorAttribute.createBaseColorFactor(Color.RED));
                    MeshPartBuilder builder = modelBuilder.part(x + ", " + z, GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, material);
                    BoxShapeBuilder.build(builder, x, 0, z, 1f, 1f, 1f);
                }
            }
            
            ModelInstance model = new ModelInstance(modelBuilder.end());
            sceneManager.addScene(new Scene(model));
        }
	
	@Override
	public void resize(int width, int height) {
            sceneManager.updateViewport(width, height);
	}
	
        private void processInput(float deltaTime) {
            // Update the player transform
            
            playerTransform.set(playerModel.modelInstance.transform);       // Store the player modelInstance's transform data into the playerTransform Matrix4
            
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                moveTranslation.z += speed * deltaTime;
            }
            
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                moveTranslation.z -= speed * deltaTime;
            }
            
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                playerTransform.rotate(Vector3.Y, rotationSpeed * deltaTime);
            }
            
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                playerTransform.rotate(Vector3.Y, -rotationSpeed * deltaTime);
            }
            
            // Apply the move translation to the transform
            playerTransform.translate(moveTranslation);
            
            // Set the modified transform
            playerModel.modelInstance.transform.set(playerTransform);
            
            // Update vector position
            playerModel.modelInstance.transform.getTranslation(currentPosition);
            
            // CLear the move translation out
            moveTranslation.set(0, 0, 0);
            
            
        }
        
	@Override
	public void render() {
            float deltaTime = Gdx.graphics.getDeltaTime();
            time += deltaTime;
            
//            playerModel.modelInstance.transform.rotate(Vector3.Y, 10f * deltaTime);  ROTATES THE CAMERA HORIZONTALLY CLOCKWISE
            
            processInput(deltaTime);
            camera.position.set(currentPosition.x, camHeight + 50, currentPosition.z - camPitch);
            camera.lookAt(currentPosition);
            camera.update();
            
//            cameraController.update();

            // render
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            sceneManager.update(deltaTime);     // Update the Scene 
            sceneManager.render();                    // Render the Scene
	}
        
	
	@Override
	public void dispose() {
            sceneManager.dispose();
            player.dispose();
            environmentCubemap.dispose();
            diffuseCubemap.dispose();
            specularCubemap.dispose();
            brdfLUT.dispose();
            skybox.dispose();
	}

}