package centdb.utilities;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class ColumnDataType {
    public String columnDataType(File path, String tableName, String fieldName){

        try {
            String filePath = path+"/"+tableName+"_Metadata"+".txt";

            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = null;
            while((line = bufferedReader.readLine())!=null){
                List<String> fieldDetails = Arrays.asList(line.split("\\|"));

                if(fieldDetails.get(0).equals(fieldName)){

                    return fieldDetails.get(1);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
