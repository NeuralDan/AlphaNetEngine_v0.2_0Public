package Engine.rendering.objLoader;

import Engine.core.Utils;
import Engine.rendering.Loader;
import Engine.rendering.RawModel;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class OBJLoader {

    public static RawModel loadModel(String fileName){
        List<String> lines = Utils.readAllLines(fileName);

        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Face> faces = new ArrayList<>();

        for(String line : lines){
            String[] tokens = line.split("\\s+");
            switch(tokens[0]){
                case "v":
                    Vector3f vertex = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));
                    vertices.add(vertex);
                    break;
                case "vt":
                    Vector2f texCoord = new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]));
                    textures.add(texCoord);
                    break;
                case "vn":
                    Vector3f normal = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));
                    normals.add(normal);
                    break;
                case "f":
                    Face face = new Face(tokens[1], tokens[2], tokens[3]);
                    faces.add(face);
                    break;
                default:
                    break;
            }
        }
        return reorderLists(vertices, textures, normals, faces);
    }

    private static RawModel reorderLists(List<Vector3f> posList, List<Vector2f> texCoordList, List<Vector3f> normList, List<Face> facesList){
        List<Integer> indices = new ArrayList<>();
        float[] posArr = new float[posList.size() * 3];
        int i = 0;
        for(Vector3f pos : posList){
            posArr[i * 3] = pos.x;
            posArr[i * 3 + 1] = pos.y;
            posArr[i * 3 + 2] = pos.z;
            i++;
        }
        float[] texCoordArr = new float[posList.size() * 2];
        float[] normArr = new float[posList.size() * 3];

        for(Face face : facesList){
            IdxGroup[] faceVertexIndices = face.getFaceVertexIndices();
            for(IdxGroup indValue : faceVertexIndices){
                processFaceVertex(indValue, texCoordList, normList, indices, texCoordArr, normArr);
            }
        }
        int[] indicesArr = new int[indices.size()];
        indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();
        RawModel rawModel = Loader.loadToVAO(posArr, texCoordArr, normArr, indicesArr);
        return rawModel;
    }

    private static void processFaceVertex(IdxGroup indices, List<Vector2f> texCoordList, List<Vector3f> normList, List<Integer> indicesList, float[] texCoordArr, float[] normArr){
        int posIndex = indices.idxPos;
        indicesList.add(posIndex);

        if(indices.idxTexCoord >= 0){
            Vector2f texCoord = texCoordList.get(indices.idxTexCoord);
            texCoordArr[posIndex * 2] = texCoord.x;
            texCoordArr[posIndex * 2 + 1] = 1 - texCoord.y;
        }
        if(indices.idxVecNormal >= 0){
            Vector3f normal = normList.get(indices.idxVecNormal);
            normArr[posIndex * 3] = normal.x;
            normArr[posIndex * 3 + 1] = normal.y;
            normArr[posIndex * 3 + 2] = normal.z;
        }
    }

}
