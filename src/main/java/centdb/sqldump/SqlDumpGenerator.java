package centdb.sqldump;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SqlDumpGenerator {
    public String generateCreateQuery(File file) {
        StringBuilder createTableLine = new StringBuilder("CREATE TABLE " + file.getName() + " (");
        try {
            BufferedReader metaReader = new BufferedReader(new FileReader(file.getAbsolutePath() + File.separator + "metadata.txt"));
            String line = "";
            while ((line = metaReader.readLine()) != null) {
                List<String> metadata = Arrays.asList(line.split("\\|"));
                if (metadata.size() == 2) {
                    if (metadata.contains("int") || metadata.contains("float") || metadata.contains("boolean")) {
                        createTableLine.append(metadata.get(0) + " " + metadata.get(1));
                    }
                } else if (metadata.size() == 3) {
                    if (metadata.contains("int") || metadata.contains("float") || metadata.contains("boolean")) {
                        createTableLine.append(metadata.get(0) + " " + metadata.get(1));
                    } else if (metadata.contains("varchar")) {
                        createTableLine.append(metadata.get(0) + " " + metadata.get(1) + "(" + metadata.get(2) + ")");
                    }
                    if (metadata.contains("PRIMARY KEY")) {
                        createTableLine.append(" " + metadata.get(2));
                    }
                }
                if (metadata.size() == 5 && metadata.contains("REFERENCES")) {
                    createTableLine.append(metadata.get(0) + " " + metadata.get(1) + " " + metadata.get(2) + " " + metadata.get(3) + "(" + metadata.get(4) + ")");
                }
                if (line != null) {
                    createTableLine.append(", ");
                }
            }
            createTableLine.append(");\n");
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
        return createTableLine.toString();
    }

    public void generateSqlDump() {
        Scanner scanner = new Scanner(System.in);
        String database = "";
        System.out.println("Enter database name for exporting SQL dump:");
        // database = scanner.nextLine();
        database = "database\\MyDatabase";
        List<String> databaseName = Arrays.asList(database.split("\\\\"));

        if (!database.isEmpty()) {
            File directory = new File(database);
            if (directory.exists()) {
                File sqlDumpDirectory = new File("SqlDump");
                System.out.println(sqlDumpDirectory.getAbsolutePath());
                File sqlDumpFile = new File(sqlDumpDirectory.getAbsolutePath() + "\\" + databaseName.get(1) + "_sql_dump.sql");
                if (!sqlDumpFile.exists()) {
                    try {
                        sqlDumpFile.createNewFile();
                    } catch (IOException ioException) {
                        System.out.println(ioException.getMessage());
                    }
                }
                File[] files = directory.listFiles();
                String line = "";
                try {
                    BufferedWriter fileWriter = new BufferedWriter(new FileWriter(sqlDumpFile));
                    for (File file : files) {
                        List<String> values;
                        String tableName = file.getName();
                        String createTableQuery = "";
                        StringBuilder insertTableQuery = new StringBuilder("INSERT INTO " + tableName + " VALUES ");
                        BufferedReader dataReader = new BufferedReader(new FileReader(file.getAbsolutePath() + File.separator + "data.txt"));
                        line = dataReader.readLine();
                        createTableQuery = generateCreateQuery(file);
                        while ((line = dataReader.readLine()) != null) {
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
                        insertTableQuery.append(";\n");
                        createTableQuery = createTableQuery.substring(0, createTableQuery.length() - 5) + ";\n";
                        String insertStatement = insertTableQuery.substring(0, insertTableQuery.length() - 4) + ";\n";
                        fileWriter.append(createTableQuery);
                        fileWriter.append(insertStatement);
                    }
                    fileWriter.close();
                } catch (IOException ioException) {
                    System.out.println(ioException.getMessage());
                }
            } else {
                System.out.println("'" + database + "' database does not exist.");
            }
        } else {
            System.out.println("Please provide database name.");
        }
    }
}
