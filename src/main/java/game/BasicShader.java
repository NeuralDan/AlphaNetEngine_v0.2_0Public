package game;

import Engine.core.Camera;
import Engine.core.ResourceLoader;
import Engine.core.Transform;
import Engine.rendering.Shader;
import org.joml.Matrix4f;

public class BasicShader extends Shader {

    public BasicShader(){
        super();

        addVertexShader(ResourceLoader.loadShader("basicShader.vs"));
        addFragmentShader(ResourceLoader.loadShader("basicShader.fs"));
        compileShader();

        addUniform("transformationMatrix");
        //addUniform("projectionMatrix");
        addUniform("viewMatrix");
    }

    public void loadProjectionMatrix(Matrix4f matrix){
        setUniform("projectionMatrix", matrix);
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Transform.createViewMatrix(camera);
        setUniform("viewMatrix", viewMatrix);
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        setUniform("transformationMatrix", matrix);
    }

}
