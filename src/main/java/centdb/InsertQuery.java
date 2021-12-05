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

public class InsertQuery {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String query = "";
        // System.out.println("Enter query:");
        // query = scanner.nextLine();
        query = "INSERT INTO table(id, name, email) VALUES (11, 'def', 'ghi')";

        String insertRegex = "(INSERT\\s+INTO)\\s+(\\S+)\\s*\\((.*?)\\)\\s+(VALUES)\\s+\\((.*?)\\)\\;?";
        Pattern regex = Pattern.compile(insertRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(query);
        if (matcher.find()) {
            String tableName = "", columnNames = "", columnValues = "", fileName = "";
            List<String> columns, values;
            // Group 1 - INSERT INTO
            // Group 2 - table_name
            // Group 3 - column_name
            // Group 4 - VALUES
            // Group 5 - actual_values
            tableName = matcher.group(2);
            columnNames = matcher.group(3);
            columnValues = matcher.group(5);
            columns = Arrays.asList(columnNames.split(","));
            values = Arrays.asList(columnValues.split(","));

            File directory = new File("database");
            if (directory.exists()) {
                fileName = Common.getTablesFileNameFromDatabase(directory, tableName);
                if (!fileName.isEmpty()) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
                        String firstLine = bufferedReader.readLine();
                        List<String> tableColumnNames = Arrays.asList(firstLine.split("\\|"));
                        if (columns.size() == values.size() && columns.size() == tableColumnNames.size()) {
                            System.out.println("matched insert query");
                        } else {
                            System.out.println("Please provide valid insert query.");
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
            System.out.println("Please provide valid insert query.");
        }

    }
}
