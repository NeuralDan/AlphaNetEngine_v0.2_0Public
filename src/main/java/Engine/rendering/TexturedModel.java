package Engine.rendering;

public class TexturedModel {

    private RawModel model;
    private Material material;

    public TexturedModel(RawModel model, Material material) {
        this.model = model;
        this.material = material;
    }

    public RawModel getModel() {
        return model;
    }

    public Material getMaterial() {
        return material;
    }

}
