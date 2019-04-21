package Engine.core;

import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private Vector3f position = new Vector3f(0,0,0);
    private Vector3f rotation = new Vector3f(0,0,0);

    public Camera(){}

    public void movePosition(float dx, float dy, float dz){
        if(dz != 0){
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * dz;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * dz;
        }
        if(dx != 0){
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * dx;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) *  dx;
        }
    }

    public void moveRotation(float dx, float dy, float dz){
        rotation.x += dx;
        rotation.y += dy;
        rotation.z += dz;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}
