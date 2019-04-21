package Engine.rendering;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class Loader {

    private static ArrayList<Integer> vaos = new ArrayList<Integer>();
    private static ArrayList<Integer> vbos = new ArrayList<Integer>();
    private static ArrayList<Integer> textures = new ArrayList<Integer>();

    public static RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices){
        int vaoID = 0;
        try{
            vaoID = createVAO();
            bindIndicesBuffer(indices);
            storeDataInAttributeList(0, 3, positions);
            storeDataInAttributeList(1, 2, textureCoords);
            storeDataInAttributeList(2, 3, normals);
            unbindVAO();
        }finally {
            return new RawModel(vaoID, indices.length);
        }
    }

    public static int loadTexture(String fileName){
        ByteBuffer image;
        int width, height;
        int id = glGenTextures();
        try(MemoryStack stack = MemoryStack.stackPush()){
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            image = stbi_load("res/textures/"+fileName+".png", w, h, comp, 4);
            if(image == null){
                throw new RuntimeException("Failed to load a texture file! File: "+ fileName+ " " + System.lineSeparator() + stbi_failure_reason());
            }
            width = w.get();
            height = h.get();
        }
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        return id;
    }

    private static int createVAO(){
        int vaoID = glGenVertexArrays();
        vaos.add(vaoID);
        glBindVertexArray(vaoID);
        return vaoID;
    }

    private static void storeDataInAttributeList(int attributeNumber, int coordSize, float[] values){
        int vboID = 0;
        FloatBuffer buffer = null;
        try {
            vboID = glGenBuffers();
            vbos.add(vboID);
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            buffer = storeDataInFloatBuffer(values);
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            glVertexAttribPointer(attributeNumber, coordSize, GL_FLOAT, false, 0, 0);
        }finally {
            if(buffer != null){
                MemoryUtil.memFree(buffer);
            }
        }
    }

    private static void bindIndicesBuffer(int[] indices){
        int vboID = 0;
        IntBuffer buffer = null;
        try{
            vboID = glGenBuffers();
            vbos.add(vboID);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
            buffer = storeDataInIntBuffer(indices);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        }finally {
            if(buffer != null){
                MemoryUtil.memFree(buffer);
            }
        }
    }

    private static void unbindVAO(){
        glBindVertexArray(0);
    }

    private static FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    private static IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public static void cleanUp(){
        glDisableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for(int vbo : vbos){
            glDeleteBuffers(vbo);
        }

        glBindVertexArray(0);
        for(int vao : vaos){
            glDeleteVertexArrays(vao);
        }

        for(int texture : textures){
            glDeleteTextures(texture);
        }
    }

}
