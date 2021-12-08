package centdb.DBQuery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ERDCreation {
    public static  void main (String[] args) throws IOException {
        File directory = new File("databasename");
        File[] listOfFiles = directory.listFiles();
        List<String> tableName=new ArrayList<>();
        List<String> tableData=new ArrayList<>();
        FileWriter myWriter = new FileWriter("ERD.txt");
        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            File[] eachFile=file.listFiles();
            for(int j=0;j<eachFile.length;j++) {
                if (String.valueOf(eachFile[j]).contains("Metadata")){
                File tablemetadata = new File(String.valueOf(eachFile[j]));
                tableName.add(String.valueOf(file).replaceAll(String.valueOf(directory), "").replaceAll("Directory", "").substring(1));
//                System.out.println(tablemetadata);

                    Scanner myTableReader = new Scanner(tablemetadata);
                    myWriter.write("\n");
                    myWriter.write("TABLE NAME: "+ tableName.get(i).toUpperCase());
                    myWriter.write("\n");
                    myWriter.write("--------------------------------------------------");
                    myWriter.write("\n");
                    myWriter.write("DATA: ");
                    while (myTableReader.hasNextLine()) {
                        String tabledata = myTableReader.nextLine();
                        myWriter.write(tabledata);
                        myWriter.write("\n");
//                        myWriter.write("\n");
                    }

//                    myTableReader.close();
//                    for(int k=0;k<tableData.size();k++){
//                        System.out.println(tableData.get(k));

                    }
//
                }

        }
//            System.out.println(tableName.get(i));
        myWriter.close();
            }

        }



