package centdb;

import centdb.utilities.Common;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateTableQuery {
    public static boolean flag;
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String query = "";
        query = "CREATE TABLE TestTable (PersonID int(255) PRIMARY KEY,LastName varchar(255),FirstName varchar(255) REFERENCES Animals(AnimalID),Address varchar(255),City varchar(255));";
        String createTableRegex = "(CREATE\\s+TABLE)\\s+(\\S+)\\s*(\\((\\S+)\\s(VARCHAR|INT|FLOAT|BOOLEAN)(\\(\\d+\\))?\\s*(\\s+PRIMARY KEY\\s*)?(,\\s*(\\S+)\\s+(VARCHAR|INT|FLOAT|BOOLEAN)(\\(\\d+\\))?\\s*(\\s+REFERENCES\\s+(\\S+)\\((\\S+)\\))?)*\\))";
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
            List<String> characterseparated=new ArrayList<>();
            HashMap<String,List<String>> metadata=new HashMap<>();
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i).contains("PRIMARY")) {
                   String WithoutPk= values.get(i) .substring(1, values.get(i).lastIndexOf( "PRIMARY"));
                   String WPk= values.get(i).substring(values.get(i).lastIndexOf( "PRIMARY" ));
                   pipeseparated.add(((WithoutPk.replaceAll("[\\\\[\\\\](]","|").replaceAll(" ","|").replaceAll("[\\\\[\\\\])]",""))+WPk));

                } else {
                    pipeseparated.add(values.get(i).replaceAll(" ", "|"));
                }
            }
            for (int i = 0; i < pipeseparated.size(); i++) {
                characterseparated.add(pipeseparated.get(i).replaceAll("[\\\\[\\\\](]", "|").replaceAll("[\\\\[\\\\])]", ""));
            }
            metadata.put(tableName,characterseparated);
            File directory = new File("databasename");
            String refrences="REFERENCES";
            String foreignTable;
            if (directory.exists()) {
                    for(String rows: characterseparated) {
                        if(rows.contains("REFERENCES")){
                            foreignTable=rows.substring(rows.indexOf("REFERENCES")+refrences.length()+1, rows.lastIndexOf("|")) + "Metadata";
                            boolean check = new File(directory, foreignTable).exists();
                            if(!check){
                                flag=false;
                                System.out.println("Foreign table doesnot exists so table not created");
                                break;
                            }
                        else{
                            System.out.println("Table created successfully!!");
                                File tableDirectory = new File(directory+File.separator+tableName+"Directory");
                                tableDirectory.mkdirs();
                                File metaFile=new File(tableDirectory+File.separator+tableName+"Metadata"+".txt");
                                File tableFile=new File(tableDirectory+File.separator+tableName+".txt");
                                tableFile.createNewFile();
                                FileWriter writer = new FileWriter(metaFile);
                                for(String rows1: characterseparated){
                                    if (flag=true){
                                        writer.write(rows1 + System.lineSeparator());
                                    }
                                }

                                writer.close();
                            }}}

        } else {
                System.out.println("Database does not exist.");
            }
        }
    }
}

