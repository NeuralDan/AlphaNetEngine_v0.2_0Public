package game;

import Engine.core.Camera;
import Engine.core.Entity;
import Engine.core.Transform;
import Engine.rendering.*;
import org.joml.Matrix4f;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class EntityRenderer {

    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.0f;

    private BasicShader shader;

    private Window window;
    private Camera camera;

    public EntityRenderer(Window window, Camera camera){
        shader = new BasicShader();
        this.window = window;
        this.camera = camera;
    }

    public void prepare(){
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void renderEntity(List<Entity> entities){
        prepare();

        if(window.isResize()){
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(false);
        }
        shader.bind();

        Matrix4f projectionMatrix = Transform.createProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shader.loadProjectionMatrix(projectionMatrix);
        shader.loadViewMatrix(camera);

        for(Entity entity : entities){
            TexturedModel texturedModel = entity.getModel();
            Matrix4f transformationMatrix = Transform.createTransformationMatrix(entity.getPosition(),
                    entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
            shader.loadTransformationMatrix(transformationMatrix);

            enable(texturedModel);
            glDrawElements(GL_TRIANGLES, texturedModel.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
            disable();
        }
        shader.unbind();
    }

    public void enable(TexturedModel model){
        glBindVertexArray(model.getModel().getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glActiveTexture(GL_TEXTURE0);
        shader.loadMaterial(model.getMaterial());
    }

    public void disable(){
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
    }

    public void cleanUp(){
        shader.cleanUp();
    }

}
