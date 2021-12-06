package centdb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SqlDumpGenerator {
    public static String generateCreateQuery(String tableName, String line) {
        List<String> columns = Arrays.asList(line.split("\\|"));
        StringBuilder createTableLine = new StringBuilder("CREATE TABLE " + tableName + " (");
        System.out.println("columns " + columns);
        for (int i = 0; i < columns.size(); i++) {
            createTableLine.append(columns.get(i) + ", ");
        }
        createTableLine.append(")");
        return createTableLine.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String database = "";
        System.out.println("Enter database name for exporting SQL dump:");
        // database = scanner.nextLine();
        database = "database";

        if (!database.isEmpty()) {
            File directory = new File(database);
            if (directory.exists()) {
                File sqlDumpDirectory = new File("SqlDump");
                File sqlDumpFile = new File(sqlDumpDirectory.getAbsolutePath() + File.separator + database + "_sql_dump.sql");
                System.out.println(sqlDumpFile.getAbsolutePath());
                File[] files = directory.listFiles();
                String line = "";
                for (File file : files) {
                    List<String> fileName = Arrays.asList(file.getName().split("\\."));
                    String tableName = fileName.get(0);
                    List<String> values;
                    String createTableQuery = "";
                    StringBuilder insertTableQuery = new StringBuilder("INSERT INTO " + tableName + " VALUES ");
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsolutePath()));
                        line = bufferedReader.readLine();
                        createTableQuery = generateCreateQuery(tableName, line);
                        System.out.println(createTableQuery);
                        while ((line = bufferedReader.readLine()) != null) {
                            values = Arrays.asList(line.split("\\|"));
                            insertTableQuery.append("(");
                            for (int i = 0; i < values.size(); i++) {
                                insertTableQuery.append(values.get(i));
                                if (i != values.size() - 1) {
                                    insertTableQuery.append(", ");
                                }
                            }
                            insertTableQuery.append("), ");
                        }
                        System.out.println("createTableQuery " + createTableQuery);
                        System.out.println("insertTableQuery " + insertTableQuery);
                    } catch (IOException ioException) {
                        System.out.println(ioException.getMessage());
                    }
                }
            } else {
                System.out.println(database + " database does not exist.");
            }
        } else {
            System.out.println("Please provide database name.");
        }
    }
}
