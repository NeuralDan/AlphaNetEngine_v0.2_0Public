package Engine.rendering;

import org.joml.Vector3f;

public class Material {

    private Texture texture;
    private Vector3f color;

    public Material(Texture texture, Vector3f color){
        this.texture = texture;
        this.color = color;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector3f getColor() {
        return color;
    }

}
