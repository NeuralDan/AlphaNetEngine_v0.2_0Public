package Engine.rendering.objLoader;

public class Face {

    private IdxGroup[] idxGroups;

    public Face(String v1, String v2, String v3){
        idxGroups = new IdxGroup[3];

        idxGroups[0] = parseLine(v1);
        idxGroups[1] = parseLine(v2);
        idxGroups[2] = parseLine(v3);
    }

    private IdxGroup parseLine(String line){
        IdxGroup idxGroup = new IdxGroup();

        String[] lineTokens = line.split("/");
        int length = lineTokens.length;
        idxGroup.idxPos = Integer.parseInt(lineTokens[0]) - 1;
        if(length > 1){
            String texCoord = lineTokens[1];
            idxGroup.idxTexCoord = texCoord.length() > 0 ? Integer.parseInt(texCoord) - 1 : IdxGroup.NO_VALUE;
            if(length > 2){
                idxGroup.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1;
            }
        }
        return idxGroup;
    }

    public IdxGroup[] getFaceVertexIndices(){
        return idxGroups;
    }

}
