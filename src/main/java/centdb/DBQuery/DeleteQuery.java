package centdb.DBQuery;

import centdb.utilities.Common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
        query = "DELETE FROM customer WHERE customer_id=1";

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

            File directory = new File("database\\MyDatabase");
            if (directory.exists()) {
                filePath = Common.getTablesFilePathFromDatabase(directory, tableName);
                if (!filePath.isEmpty()) {
                    try {
                        BufferedReader dataReader = new BufferedReader(new FileReader(filePath + File.separator + "data.txt"));
                        String line = dataReader.readLine();
                        List<String> tableColumnNames = Arrays.asList(line.split("\\|"));
                        if (tableColumnNames.contains(columnName)) {
                            int index = tableColumnNames.indexOf(columnName);
                            List<String> fileData, singleData;
                            fileData = new ArrayList<>(Files.readAllLines(Paths.get("database/MyDatabase/" + tableName + "/data.txt"), StandardCharsets.UTF_8));
                            for (int i = 0; i < fileData.size(); i++) {
                                singleData = Arrays.asList(fileData.get(i).split("\\|"));
                                if (columnValue.equals(singleData.get(index))) {
                                    fileData.remove(i);
                                    break;
                                }
                            }
                            Files.write(Paths.get("database/MyDatabase/" + tableName + "/data.txt"), fileData, StandardCharsets.UTF_8);
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
