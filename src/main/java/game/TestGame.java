package game;

import Engine.core.*;
import Engine.rendering.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class TestGame implements IGame {

    private List<Entity> entities = new ArrayList<Entity>();

    private EntityRenderer renderer;

    private Loader loader = new Loader();

    private RawModel model;

    private TexturedModel texturedModel;

    private Entity entity;
    private Entity entity1;

    private Matrix4f projectionMatrix;
    private Matrix4f worldMatrix;

    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.0f;

    @Override
    public void init() throws Exception {
        renderer = new EntityRenderer();
        renderer.init();
        float[] vertices = {
                -0.5f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f,  0.5f, 0.0f,
        };

        int[] indices = {
                0, 1, 3, 3, 1, 2,
        };

        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0
        };

        model = loader.loadToVAO(vertices, textureCoords, indices);

        texturedModel = new TexturedModel(model, new Texture("AvengersLogo.png"));
        TexturedModel texturedModel1 = new TexturedModel(model, new Texture("nightBack.png"));
        entity = new Entity(texturedModel);
        entity1 = new Entity(texturedModel1);
        entities.add(entity);
    }

    @Override
    public void input(CoreEngine coreEngine) {

    }

    @Override
    public void update(float interval) {

    }

    @Override
    public void render(Window window) {
        renderer.renderEntity(entity1, window);
        window.setClearColor(0, 1, 0, 0.0f);
    }

    @Override
    public void cleanUp(){
        loader.cleanUp();
        texturedModel.getTexture().cleanUp();
    }
}
