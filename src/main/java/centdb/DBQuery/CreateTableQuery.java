package centdb.DBQuery;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateTableQuery {
    public static boolean flag;
    public static String otherKey;

    public void createTableQuery(String query, String databaseName) throws IOException {
        Scanner scanner = new Scanner(System.in);
//        String query = "";

        //***
        long startTime = System.currentTimeMillis();
        //execute the query i.e. Call the query executer
        //***

//        query = "CREATE TABLE TestTable (PersonID int(255) PRIMARY KEY,LastName varchar(255),FirstName varchar(255) REFERENCES Animals(AnimalID),Address varchar(255),City varchar(255));";
        String createTableRegex = "(CREATE\\s+TABLE)\\s+(\\S+)\\s*(\\((\\S+)\\s(VARCHAR|INT|FLOAT|BOOLEAN)(\\(\\d+\\))?\\s*(\\s+PRIMARY KEY\\s*)?(,\\s*(\\S+)\\s+(VARCHAR|INT|FLOAT|BOOLEAN)(\\(\\d+\\))?\\s*(\\s+REFERENCES\\s+(\\S+)\\((\\S+)\\))?)*\\))";

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        Pattern regex = Pattern.compile(createTableRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(query);
        if (matcher.find()) {
            String tableName = "", columnValues = "", filePath = "";
            List<String> columns1 = new ArrayList<>();
            // Group 1 - CREATE TABLE
            // Group 2 - TestTable
            // Group 3 - (PersonID int PRIMARY KEY,LastName varchar(255),FirstName varchar(255),Address varchar(255),City varchar(255));";
            tableName = matcher.group(2);
            columnValues = matcher.group(3);
            List<String> values = Arrays.asList(columnValues.split(","));
            List<String> pipeseparated = new ArrayList<>();
            List<String> characterseparated = new ArrayList<>();
            List<String> columnsname = new ArrayList<>();
            HashMap<String, List<String>> metadata = new HashMap<>();
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i).contains("PRIMARY")) {
                    String WithoutPk = values.get(i).substring(1, values.get(i).lastIndexOf("PRIMARY"));
                    String WPk = values.get(i).substring(values.get(i).lastIndexOf("PRIMARY"));
                    pipeseparated.add(((WithoutPk.replaceAll("[\\\\[\\\\](]", "|").replaceAll(" ", "|").replaceAll("[\\\\[\\\\])]", "")) + WPk));

                } else {
                    pipeseparated.add(values.get(i).replaceAll(" ", "|"));
                }
            }
            for (int i = 0; i < pipeseparated.size(); i++) {
                characterseparated.add(pipeseparated.get(i).replaceAll("[\\\\[\\\\](]", "|").replaceAll("[\\\\[\\\\])]", ""));
                columnsname.add(pipeseparated.get(i).substring(0, pipeseparated.get(i).indexOf("|")));
            }
//            System.out.println(columnsname);
            metadata.put(tableName, characterseparated);
            File directory = new File("database" + File.separator + databaseName);
            String refrences = "REFERENCES";
            String foreignDirectory;
            String foreignTable;
            String Table;
            String Foreginkey = null;
            if (directory.exists()) {
                String checking = characterseparated.toString();
                if (checking.contains("REFERENCES")) {
                    for (String rows : characterseparated) {

                        if (rows.contains("REFERENCES")) {
                            foreignDirectory = rows.substring(rows.indexOf("REFERENCES") + refrences.length() + 1, rows.lastIndexOf("|")) + "Directory";
                            foreignTable = rows.substring(rows.indexOf("REFERENCES") + refrences.length() + 1, rows.lastIndexOf("|")) + "Metadata";
//                        System.out.println(directory+File.separator+foreignDirectory+File.separator+foreignTable+".txt");
                            boolean check = new File(directory + File.separator + foreignDirectory + File.separator + foreignTable + ".txt").exists();
//                        System.out.println(check);
                            if (!check) {
                                flag = false;
                                System.out.println("File table doesnot exists so table not created");
                                break;
                            } else {
                                File ForeignFile = new File(directory + File.separator + foreignDirectory + File.separator + foreignTable + ".txt");
                                Scanner myReader = new Scanner(ForeignFile);
                                while (myReader.hasNextLine()) {
                                    String data = myReader.nextLine();
                                    if (data.contains("PRIMARY")) {
                                        otherKey = data.substring(0, data.indexOf("|"));
                                    }
                                }

                                Table = rows.substring(rows.indexOf("REFERENCES") + refrences.length() + 1, rows.lastIndexOf("|"));
                                Foreginkey = rows.substring(rows.indexOf(Table) + Table.length() + 1);
                                if (Foreginkey.equals(otherKey)) {
                                    System.out.println("Table has been created successfully!!");
                                    File tableDirectory = new File(directory + File.separator + tableName);
                                    tableDirectory.mkdirs();
                                    File metaFile = new File(tableDirectory + File.separator + "metadata" + ".txt");
                                    FileWriter writer = new FileWriter(metaFile);
                                    for (String rows1 : characterseparated) {
                                        if (flag = true) {
                                            writer.write(rows1 + System.lineSeparator());
                                        }
                                    }
                                    writer.close();
                                    File tableFile = new File(tableDirectory + File.separator + "data" + ".txt");
                                    tableFile.createNewFile();
                                    FileWriter columnwriter = new FileWriter(tableFile);
                                    for (String columns : columnsname) {
                                        columnwriter.write(columns + "|");
                                    }
                                    columnwriter.close();
                                } else {
                                    System.out.println("Foreign key doesnot match");
                                }
                            }
                            break;
                        }

                    }
                } else {
                    System.out.println("Table has been created successfully!!");
                    File tableDirectory = new File(directory + File.separator + tableName);
                    tableDirectory.mkdirs();
                    File metaFile = new File(tableDirectory + File.separator + "metadata" + ".txt");
                    FileWriter writer = new FileWriter(metaFile);
                    for (String rows1 : characterseparated) {
                        if (flag = true) {
                            writer.write(rows1 + System.lineSeparator());
                        }
                    }
                    writer.close();
                    File tableFile = new File(tableDirectory + File.separator + "data" + ".txt");
                    tableFile.createNewFile();
                    FileWriter columnwriter = new FileWriter(tableFile);
                    for (String columns : columnsname) {
                        columnwriter.write(columns + "|");
                    }
                    columnwriter.close();
                }

            } else {
                System.out.println("directory not exists");
            }
        }
    }
}



