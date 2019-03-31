package game;

import Engine.core.Entity;
import Engine.rendering.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class EntityRenderer {

    private BasicShader shader;

    public void init(){
        shader = new BasicShader();
    }

    public void clear(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void renderEntity(Entity entity, Window window){
        clear();
        TexturedModel texturedModel = entity.getModel();
        RawModel model = texturedModel.getModel();

        if(window.isResize()){
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(false);
        }
        shader.bind();

        shader.updateUniforms(texturedModel.getTexture());

        glBindVertexArray(model.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        shader.unbind();
    }

}
