package centdb.DBQuery;

import centdb.utilities.Common;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertQuery {
    public void insertQuery(String query, String databaseName) {
//        String query = "INSERT INTO product(product_id, product_name, product_price, customer_id) VALUES (4, bed, 68.96, 15)";

        String insertRegex = "(INSERT\\s+INTO)\\s+(\\S+)\\s*\\((.*?)\\)\\s+(VALUES)\\s+\\((.*?)\\)\\;?";
        Pattern regex = Pattern.compile(insertRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(query);
        if (matcher.find()) {
            String tableName = "", columnNames = "", columnValues = "", filePath = "", line = "", metaLine = "";
            List<String> columns, values;
            // Group 1 - INSERT INTO
            // Group 2 - table_name
            // Group 3 - column_name
            // Group 4 - VALUES
            // Group 5 - actual_values
            tableName = matcher.group(2);
            columnNames = matcher.group(3).replaceAll("\\s+", "");
            columnValues = matcher.group(5).replaceAll("\\s+", "");
            columns = Arrays.asList(columnNames.split(","));
            values = Arrays.asList(columnValues.split(","));

            File directory = new File("database\\MyDatabase");
            if (directory.exists()) {
                filePath = Common.getTablesFilePathFromDatabase(directory, tableName);
                if (!filePath.isEmpty()) {
                    try {
                        BufferedReader dataReader = new BufferedReader(new FileReader(filePath + File.separator + "data.txt"));
                        String firstLine = dataReader.readLine();
                        List<String> tableColumnNames = Arrays.asList(firstLine.split("\\|"));
                        if (columns.size() == values.size() && columns.size() == tableColumnNames.size()) {
                            BufferedReader metadataReader = new BufferedReader(new FileReader(filePath + File.separator + "metadata.txt"));
                            List<String> metadata = new ArrayList<>();
                            while ((metaLine = metadataReader.readLine()) != null) {
                                metadata.add(metaLine);
                            }
                            boolean invalidColumnName = false, invalidDatatype = false, invalidSize = false, primaryKeyViolation = false, foreignKeyViolation = false;
                            for (int i = 0; i < values.size(); ++i) {
                                List<String> columnMeta = Arrays.asList(metadata.get(i).split("\\|"));
                                // same column names
                                if (!columnMeta.get(0).equals(columns.get(i))) {
                                    invalidColumnName = true;
                                }
                                // varchar data type and size
                                if (columnMeta.get(1).equals("varchar")) {
                                    if (values.get(i).length() > Integer.parseInt(columnMeta.get(2))) {
                                        invalidSize = true;
                                    }
                                }
                                // int data type
                                if (columnMeta.get(1).equals("int")) {
                                    if (!values.get(i).matches("\\d+")) {
                                        invalidDatatype = true;
                                    }
                                }
                                // float data type
                                if (columnMeta.get(1).equals("float")) {
                                    if (!values.get(i).matches("\\d+\\.\\d+")) {
                                        invalidDatatype = true;
                                    }
                                }
                                // boolean data type
                                if (columnMeta.get(1).equals("boolean")) {
                                    if (!Arrays.asList("true", "false").contains(values.get(i))) {
                                        invalidDatatype = true;
                                    }
                                }
                                // primary key violation check (duplicate value in pk)
                                if (columnMeta.size() == 3 && columnMeta.get(2).equals("PRIMARY KEY")) {
                                    List<String> data;
                                    List<String> primaryData = new ArrayList<>();
                                    String dataLine = "";
                                    while ((dataLine = dataReader.readLine()) != null) {
                                        data = Arrays.asList(dataLine.split("\\|"));
                                        primaryData.add(data.get(0));
                                    }
                                    if (primaryData.contains(values.get(0))) {
                                        primaryKeyViolation = true;
                                    }
                                }
                                // foreign key violation (no value in reference table)
                                if (columnMeta.size() == 5 && columnMeta.get(2).equals("REFERENCES")) {
                                    List<String> data;
                                    List<String> primaryData = new ArrayList<>();
                                    String refTableName = columnMeta.get(3);
                                    String refTablePath = Common.getTablesFilePathFromDatabase(directory, refTableName);
                                    String refLine = "";
                                    BufferedReader refTableReader = new BufferedReader(new FileReader(refTablePath + "\\data.txt"));
                                    while ((refLine = refTableReader.readLine()) != null) {
                                        data = Arrays.asList(refLine.split("\\|"));
                                        primaryData.add(data.get(0));
                                    }
                                    if (!primaryData.contains(values.get(i))) {
                                        foreignKeyViolation = true;
                                    }
                                }
                            }
                            if (invalidColumnName) {
                                System.out.println("Please provide valid column names that matches table schema.");
                            } else if (invalidDatatype) {
                                System.out.println("Please provide data with proper data types that matches table schema");
                            } else if (invalidSize) {
                                System.out.println("The size of data should match with column definition.");
                            } else if (primaryKeyViolation) {
                                System.out.println("There should not be duplicate values for primary key.");
                            } else if (foreignKeyViolation) {
                                System.out.println("There must be value for reference in referenced table.");
                            } else {
                                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath + "\\data.txt", true));
                                fileWriter.append(columnValues.replaceAll(",", "|"));
                                fileWriter.append("\n");
                                fileWriter.close();
                            }
                        } else {
                            System.out.println("Please provide valid insert query.");
                        }
                    } catch (IOException ioException) {
                        System.out.println(ioException.getMessage());
                    }
                } else {
                    System.out.println("'" + tableName + "' table does not exist.");
                }
            } else {
                System.out.println("Database does not exist.");
            }
        } else {
            System.out.println("Please provide valid insert query.");
        }

    }
}
