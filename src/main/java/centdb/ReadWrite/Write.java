package centdb.ReadWrite;

import java.io.FileWriter;
import java.io.IOException;

public class Write {
    public static void writeFile(String path, String tableName, String content){
        try {
            FileWriter writer = new FileWriter(path+"/"+tableName+".txt");
            writer.write(content);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
