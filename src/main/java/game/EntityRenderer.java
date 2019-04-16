package game;

import Engine.core.Camera;
import Engine.core.Entity;
import Engine.core.Transform;
import Engine.rendering.*;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class EntityRenderer {

    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float NEAR_PLANE = 0.01f;
    private static final float FAR_PLANE = 1000;

    private Matrix4f projectionMatrix;

    private BasicShader shader;

    private Window window;
    private Camera camera;

    public EntityRenderer(Window window, Camera camera){
        shader = new BasicShader();
        this.window = window;
        createProjectionMatrix();
        //shader.loadProjectionMatrix(projectionMatrix);
        this.camera = camera;
    }

    public void prepare(){
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void renderEntity(Entity entity){
        prepare();
        TexturedModel texturedModel = entity.getModel();
        RawModel model = texturedModel.getModel();

        if(window.isResize()){
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(false);
        }
        shader.bind();

        glBindVertexArray(model.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        Matrix4f transformationMatrix = Transform.createTransformationMatrix(entity.getPosition(),
                entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        shader.loadViewMatrix(camera);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
        glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        shader.unbind();
    }

    private void createProjectionMatrix(){
        float aspectRatio = (float)window.getWidth() / (float)window.getHeight();
//        float halfWidth = 1.0f;
//        float halfHeight = halfWidth/aspectRatio;
//        projectionMatrix = Projection.perspective(-halfWidth, halfWidth, -halfHeight, halfHeight, NEAR_PLANE, FAR_PLANE);

        projectionMatrix = new Matrix4f();
        projectionMatrix.identity();
        projectionMatrix.setPerspective(FOV, aspectRatio, NEAR_PLANE, FAR_PLANE);


//        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
//        float x_scale = y_scale / aspectRatio;
//        float frustum_length = FAR_PLANE - NEAR_PLANE;
//
//        projectionMatrix = new Matrix4f();
//        projectionMatrix.m00(x_scale);
//        projectionMatrix.m11(y_scale);
//        projectionMatrix.m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
//        projectionMatrix.m23(-1);
//        projectionMatrix.m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
//        projectionMatrix.m33(0);
    }

    public void cleanUp(){
        shader.cleanUp();
    }

}
