package Engine.rendering;

import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private int id;

    public Texture(String fileName) throws Exception{
        this(loadTexture(fileName));
    }

    public Texture(int id){
        this.id = id;
    }

    public void bind(){
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int getId() {
        return id;
    }

    private static int loadTexture(String fileName) throws Exception{
        int width;
        int height;
        ByteBuffer buffer;

        try(MemoryStack stack = MemoryStack.stackPush()){
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            File file = new File("./res/textures/" + fileName);
            String filePath = file.getAbsolutePath();
            buffer = stbi_load(filePath, w, h, channels, 4);
            if(buffer == null){
                throw new Exception("Image file [" + filePath + "] not loaded:" + stbi_failure_reason());
            }

            width = w.get();
            height = h.get();
        }

        int textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        glGenerateMipmap(GL_TEXTURE_2D);

        stbi_image_free(buffer);

        return textureId;
    }

    public void cleanUp(){
        glDeleteTextures(id);
    }

}
