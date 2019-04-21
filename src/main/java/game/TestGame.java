package game;

import Engine.core.*;
import Engine.rendering.*;
import Engine.rendering.objLoader.OBJLoader;
import org.joml.*;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class TestGame implements IGame {

    private List<Entity> entities = new ArrayList<Entity>();

    private EntityRenderer renderer;

    private RawModel model;

    private TexturedModel texturedModel;

    private Camera camera;

    private Vector3f cameraInc;

    @Override
    public void init(Window window) throws Exception {
        camera = new Camera();
        renderer = new EntityRenderer(window, camera);

        cameraInc = new Vector3f();

        model = OBJLoader.loadModel("box");

        texturedModel = new TexturedModel(model, new Material(new Texture(Loader.loadTexture("grassblock")), new Vector3f(0,1,1)));
        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-2), 0, 0, 0, 0.5f);
        Entity entity1 = new Entity(texturedModel, new Vector3f(1f, 1f, -2), 0,0,0, 0.5f);
        Entity entity2 = new Entity(texturedModel, new Vector3f(0,0,-3f), 0,0,0, 0.5f);
        Entity entity3 = new Entity(texturedModel, new Vector3f(1f, 0, -3f), 0,0,0, 0.5f);
        entities.add(entity);
        entities.add(entity1);
        entities.add(entity2);
        entities.add(entity3);
    }

    @Override
    public void input(MouseInput mouseInput) {
        cameraInc.set(0,0,0);
        if(CoreEngine.isKeyPressed(GLFW_KEY_W)){
            cameraInc.z = -1;
        }else if(CoreEngine.isKeyPressed(GLFW_KEY_S)){
            cameraInc.z = 1;
        }
        if(CoreEngine.isKeyPressed(GLFW_KEY_A)){
            cameraInc.x = -1;
        }else if(CoreEngine.isKeyPressed(GLFW_KEY_D)){
            cameraInc.x = 1;
        }
        if(CoreEngine.isKeyPressed(GLFW_KEY_Z)){
            cameraInc.y = -1;
        }else if(CoreEngine.isKeyPressed(GLFW_KEY_X)){
            cameraInc.y = 1;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        camera.movePosition(cameraInc.x * 0.05f, cameraInc.y * 0.05f, cameraInc.z * 0.05f);

        if(mouseInput.isRightButtonPressed()){
            Vector2f rot = mouseInput.getDisplVec();
            camera.moveRotation(rot.x * 0.2f, rot.y * 0.2f, 0);
        }
    }

    @Override
    public void render(Window window) {
        renderer.renderEntity(entities);

        window.setClearColor(0, 0, 0, 0.0f);
    }

    @Override
    public void cleanUp(){
        Loader.cleanUp();
        renderer.cleanUp();
    }
}
