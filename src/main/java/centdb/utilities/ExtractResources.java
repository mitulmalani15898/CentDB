package centdb.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ExtractResources {
    public static String extractResources(String query){
        query = query.toLowerCase().trim();
        List<String> queryWords = Arrays.asList(query.split(" "));
        String resource = "";
        String check = queryWords.get(0).toLowerCase();
        int index;
        switch(check){
            case "select":  index = queryWords.indexOf("from")+1;
                            resource = queryWords.get(index).substring(0,1).toUpperCase()+queryWords.get(index).substring(1);
                            break;
            case "insert":  index = queryWords.indexOf("into")+1;
                            resource = queryWords.get(index).substring(0,1).toUpperCase()+queryWords.get(index).substring(1);
                            break;
            case "update":  index = queryWords.indexOf("update")+1;
                            resource = queryWords.get(index).substring(0,1).toUpperCase()+queryWords.get(index).substring(1);
                            break;
            case "drop":    index = queryWords.indexOf("table")+1;
                            resource = queryWords.get(index).substring(0,1).toUpperCase()+queryWords.get(index).substring(1);
                            break;
            case "delete":  index = queryWords.indexOf("from")+1;
                            resource = queryWords.get(index).substring(0,1).toUpperCase()+queryWords.get(index).substring(1);
                            break;
            default:        resource = null;

        }



        return resource;
    }
    
}
