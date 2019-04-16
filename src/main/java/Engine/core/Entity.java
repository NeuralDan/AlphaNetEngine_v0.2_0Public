package Engine.core;

import Engine.rendering.TexturedModel;
import org.joml.Vector3f;

public class Entity extends GameObject {

    private TexturedModel model;

    private Vector3f position;
    private float rotX, rotY, rotZ;

    private float scale;

    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale){
        this.model = model;
        this.position = position;
        this.scale = scale;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
    }

    public Vector3f getPosition(){
        return position;
    }

    public void increasePos(float dx, float dy, float dz){
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRot(float dx, float dy, float dz){
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    public void setPosition(float x, float y, float z){
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public float getScale(){
        return scale;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

    public TexturedModel getModel(){
        return model;
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }
}
