package centdb;

import centdb.utilities.Common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteQuery {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String query = "";
        // System.out.println("Enter query:");
        // query = scanner.nextLine();
        query = "DELETE FROM table WHERE id='mitul'";

        String insertRegex = "(DELETE\\s+FROM)\\s+(\\S+)\\s+(WHERE)\\s+(\\S+)\\s*=\\s*(\\S+)\\s*\\;?";
        Pattern regex = Pattern.compile(insertRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(query);
        if (matcher.find()) {
            String tableName = "", columnName = "", columnValue = "", filePath = "";
            List<String> columns, values;
            // Group 1 - DELETE FROM
            // Group 2 - table_name
            // Group 3 - WHERE
            // Group 4 - column_name
            // Group 5 - column_value
            tableName = matcher.group(2);
            columnName = matcher.group(4);
            columnValue = matcher.group(5);

            System.out.println(tableName);
            System.out.println(columnName);
            System.out.println(columnValue);

            File directory = new File("database");
            if (directory.exists()) {
                filePath = Common.getTablesFilePathFromDatabase(directory, tableName);
                if (!filePath.isEmpty()) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
                        String firstLine = bufferedReader.readLine();
                        System.out.println(firstLine);
                        List<String> tableColumnNames = Arrays.asList(firstLine.split("\\|"));
                        System.out.println("tableColumnNames " + tableColumnNames);
                        if (tableColumnNames.contains(columnName)) {
                            System.out.println(tableColumnNames.contains(columnName));
                        } else {
                            System.out.println(columnName + " column does not exist.");
                        }
                    } catch (IOException ioException) {
                        System.out.println(ioException.getMessage());
                    }
                } else {
                    System.out.println(tableName + " table does not exist.");
                }
            } else {
                System.out.println("Database does not exist.");
            }
        } else {
            System.out.println("Please provide valid delete query.");
        }
    }
}
