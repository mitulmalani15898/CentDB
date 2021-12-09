package centdb.DBQuery;

import centdb.utilities.Common;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DropQuery {
    public void dropQuery(String query, String databaseName) {
//        String query = "DROP TABLE tablename";

        String insertRegex = "(DROP\\s+TABLE)\\s+(\\S+)\\;?";
        Pattern regex = Pattern.compile(insertRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(query);
        if (matcher.find()) {
            String tableName = "", filePath = "";
            // Group 1 - DROP TABLE
            // Group 2 - table_name
            tableName = matcher.group(2);

            File directory = new File("database\\MyDatabase");
            if (directory.exists()) {
                filePath = Common.getTablesFilePathFromDatabase(directory, tableName);
                if (!filePath.isEmpty()) {
                    File file = new File(filePath);
                    if (file.delete()) {
                        System.out.println("Dropped table '" + tableName + "'");
                    } else {
                        System.out.println("Something went wrong while deleting table.");
                    }
                } else {
                    System.out.println("'" + tableName + "' table does not exist.");
                }
            } else {
                System.out.println("Database does not exist.");
            }
        } else {
            System.out.println("Please provide valid drop query.");
        }
    }
}
