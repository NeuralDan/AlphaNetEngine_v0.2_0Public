package game;

import Engine.core.Camera;
import Engine.core.ResourceLoader;
import Engine.core.Transform;
import Engine.rendering.Material;
import Engine.rendering.Shader;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class BasicShader extends Shader {

    public BasicShader(){
        super();

        addVertexShader(ResourceLoader.loadShader("basicShader.vs"));
        addFragmentShader(ResourceLoader.loadShader("basicShader.fs"));
        compileShader();

        addUniform("transformationMatrix");
        addUniform("projectionMatrix");
        addUniform("viewMatrix");
        addUniform("color");
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

    public void loadMaterial(Material material){
        if(material.getTexture() != null){
            glBindTexture(GL_TEXTURE_2D, material.getTexture().getTextureID());
        }

        setUniform("color", material.getColor());
    }

}
