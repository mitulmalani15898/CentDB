package centdb.DBQuery;

import centdb.LogManagement;
import centdb.usermodule.UserModule;
import centdb.utilities.Common;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DropQuery {
    public void dropQuery(String query, String databaseName) throws IOException {
        String insertRegex = "(DROP\\s+TABLE)\\s+(\\S+)\\;";
        Pattern regex = Pattern.compile(insertRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(query);

        if (matcher.find()) {
            String tableName = "", filePath = "";
            // Group 1 - DROP TABLE
            // Group 2 - table_name
            tableName = matcher.group(2);

            File directory = new File("database" + File.separator + databaseName);
            if (directory.exists()) {
                filePath = Common.getTablesFilePathFromDatabase(directory, tableName);
                File tableDirectory = new File(filePath);
                if (!filePath.isEmpty()) {
                    File tablePath = new File(filePath);
                    File[] files = tablePath.listFiles();
                    for (File file : files) {
                        file.delete();
                    }
                    if (tableDirectory.delete()) {
                        System.out.println("Dropped table '" + tableName + "'");
                        LogManagement.eventLogs(query, UserModule.loginUserId, databaseName, tableName);
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
