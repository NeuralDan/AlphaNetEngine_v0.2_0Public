package Engine.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<String> readAllLines(String fileName){
        List<String> list = new ArrayList<>();
        BufferedReader br = null;

        try{
            br = new BufferedReader(new FileReader("./res/models/" + fileName + ".obj"));

            String line;
            while((line = br.readLine()) != null){
                list.add(line);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
        return list;
    }

}
