package centdb.readwrite;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Read {

    public static String readFile(String path, String tableName){
        String data="";
        try {
             data = new String(Files.readAllBytes(Paths.get(path+"/"+tableName+".txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
