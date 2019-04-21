package Engine.core;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

    public static Matrix4f createProjectionMatrix(float fov, float width, float height, float zNear, float zFar){
        Matrix4f projectionMatrix = new Matrix4f();
        float aspectRatio = width/height;
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);

        return projectionMatrix;
    }

    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale){
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(translation);
        matrix.rotateX((float)Math.toRadians(rx));
        matrix.rotateY((float)Math.toRadians(ry));
        matrix.rotateZ((float)Math.toRadians(rz));
        matrix.scale(new Vector3f(scale, scale, scale));
        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera){
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.rotateX((float)Math.toRadians(camera.getRotation().x));
        viewMatrix.rotateY((float)Math.toRadians(camera.getRotation().y));
        Vector3f cameraPos = camera.getPosition();
        Vector3f negCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        viewMatrix.translate(negCameraPos);
        return viewMatrix;
    }

}
