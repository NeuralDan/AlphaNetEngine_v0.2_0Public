package game;

import Engine.core.*;
import Engine.rendering.*;
import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class TestGame implements IGame {

    private List<Entity> entities = new ArrayList<Entity>();

    private EntityRenderer renderer;

    private Loader loader = new Loader();

    private RawModel model;
    private RawModel model2;

    private TexturedModel texturedModel;

    private Entity entity;

    private Camera camera;

    @Override
    public void init(Window window) throws Exception {
        camera = new Camera();
        renderer = new EntityRenderer(window, camera);

        float[] vertices = {
                -0.5f,  0.5f,  0.5f,
                -0.5f, -0.5f,  0.5f,
                0.5f, -0.5f,  0.5f,
                0.5f,  0.5f,  0.5f,
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

        texturedModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("AvengersLogo")));
        entity = new Entity(texturedModel, new Vector3f(0,0,0), 0, 0, 0, 1);
        entities.add(entity);
    }

    @Override
    public void input(CoreEngine coreEngine) {

    }

    @Override
    public void update(float interval) {

        //System.out.println(entity.getPosition().toString());
    }

    @Override
    public void render(Window window) {
        //entity.increasePos(0, 0, -0.02f);
        //System.out.println(entity.getPosition().toString());
        //entity.increaseRot(0, 1, 0);
        camera.move();
        renderer.renderEntity(entity);

        window.setClearColor(0, 1, 0, 0.0f);
    }

    @Override
    public void cleanUp(){
        loader.cleanUp();
        renderer.cleanUp();
    }
}
