package Engine.rendering;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private int program;
    private HashMap<String, Integer> uniforms;

    private int vertexShader;
    private int fragmentShader;

    public Shader(){
        program = glCreateProgram();
        uniforms = new HashMap<String, Integer>();

        if(program == 0){
            System.err.println("Shader creation failed");
            System.exit(1);
        }
    }

    public void bind(){
        glUseProgram(program);
    }

    public void addVertexShader(String text){
        vertexShader = addProgram(text, GL_VERTEX_SHADER);
    }

    public void addFragmentShader(String text){
        fragmentShader = addProgram(text, GL_FRAGMENT_SHADER);
    }

    public void compileShader(){
        glLinkProgram(program);

        if(glGetProgrami(program, GL_LINK_STATUS) == 0){
            System.err.println(glGetProgramInfoLog(program, 1024));
            System.exit(1);
        }

        if(vertexShader != 0){
            glDetachShader(program, vertexShader);
        }
        if(fragmentShader != 0){
            glDetachShader(program, fragmentShader);
        }

        glValidateProgram(program);

        if(glGetProgrami(program, GL_VALIDATE_STATUS) == 0){
            System.err.println(glGetProgramInfoLog(program, 1024));
            System.exit(1);
        }
    }

    private int addProgram(String text, int type){
        int shader = glCreateShader(type);

        if(shader == 0){
            System.err.println("Shader creation failed");
            System.exit(1);
        }

        glShaderSource(shader, text);
        glCompileShader(shader);

        if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0){
            System.err.println(glGetShaderInfoLog(shader, 1024) + type);
            System.exit(1);
        }

        glAttachShader(program, shader);
        return shader;
    }

    public void addUniform(String uniformName){
        int uniformLocation = glGetUniformLocation(program, uniformName);

        if(uniformLocation == 0xFFFFFFFF){
            System.err.println("Error: Could not find uniform: " + uniformName);
            System.exit(1);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public void setUniformi(String uniformName, int value){
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }

    public void unbind(){
        glUseProgram(0);
    }

    public void cleanUp(){
        unbind();
        if(program != 0){
            glDeleteProgram(program);
        }
    }

}
