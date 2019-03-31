package Engine.core;

import Engine.rendering.TexturedModel;
import org.joml.Vector3f;

public class Entity extends GameObject {

    private TexturedModel model;

    private Vector3f position;
    private Vector3f rotation;

    private float scale;

    public Entity(TexturedModel model){
        this.model = model;
        this.position = new Vector3f(0,0,0);
        this.scale = 1;
        this.rotation = new Vector3f(0,0,0);
    }

    public Vector3f getPosition(){
        return position;
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

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public TexturedModel getModel(){
        return model;
    }
}
