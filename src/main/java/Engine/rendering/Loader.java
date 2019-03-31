package Engine.rendering;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Loader {

    private ArrayList<Integer> vaos = new ArrayList<Integer>();
    private ArrayList<Integer> vbos = new ArrayList<Integer>();

    public RawModel loadToVAO(float[] positions, float[] textureCoords, int[] indices){
        int vaoID = 0;
        try{
            vaoID = createVAO();
            bindIndicesBuffer(indices);
            storeDataInAttributeList(0, 3, positions);
            storeDataInAttributeList(1, 2, textureCoords);
            unbindVAO();
        }finally {
            return new RawModel(vaoID, indices.length);
        }
    }

    private int createVAO(){
        int vaoID = glGenVertexArrays();
        vaos.add(vaoID);
        glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber, int coordSize, float[] values){
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

    private void bindIndicesBuffer(int[] indices){
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

    private void unbindVAO(){
        glBindVertexArray(0);
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    private IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public void cleanUp(){
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for(int vbo : vbos){
            glDeleteBuffers(vbo);
        }

        glBindVertexArray(0);
        for(int vao : vaos){
            glDeleteVertexArrays(vao);
        }
    }

}
