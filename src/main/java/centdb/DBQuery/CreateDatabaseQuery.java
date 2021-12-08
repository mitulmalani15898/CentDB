package centdb.DBQuery;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateDatabaseQuery {
    public void createDatabaseQuery(String query) {
//        query = "CREATE DATABASE database1;";
        String createDatabaseRegex = "(CREATE\\s+DATABASE)\\s+(\\S+)\\;?";
        Pattern regex = Pattern.compile(createDatabaseRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(query);
        if (matcher.find()) {
            String createDatabaseName = "", filePath = "";
            // Group 1 - CREATE DATABASE
            // Group 2 - databasename
            createDatabaseName = matcher.group(2);
            createDatabaseName = createDatabaseName.replaceAll(";", "");
            System.out.println(createDatabaseName + "database created successfully.");
            File directory = new File("database" + File.separator + createDatabaseName);
            if (directory.exists()) {
                System.out.println("Directory already exist.");
            } else {
                directory.mkdirs();
            }
        } else {
            System.out.println("Please provide valid create databse query.");
        }
    }
}
