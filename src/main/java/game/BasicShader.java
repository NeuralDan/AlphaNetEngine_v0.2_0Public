package game;

import Engine.core.ResourceLoader;
import Engine.rendering.Shader;
import Engine.rendering.Texture;
import org.joml.Matrix4f;

public class BasicShader extends Shader {

    public BasicShader(){
        super();

        addVertexShader(ResourceLoader.loadShader("basicShader.vs"));
        addFragmentShader(ResourceLoader.loadShader("basicShader.fs"));
        compileShader();

        addUniform("textureSampler");
        //addUniform("projectionMatrix");
        //addUniform("worldMatrix");
    }

    @Override
    public void updateUniforms(Texture texture){
        texture.bind();
        setUniformi("textureSampler", 0);
        //setUniform("projectionMatrix", projectionMatrix);
        //setUniform("worldMatrix", worldMatrix);
    }

}
