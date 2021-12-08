package centdb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ERDCreation {
    private static String filed;
    private static String fk;
    public static  void main (String[] args) throws IOException {
        File directory = new File("databasename");
        File[] listOfFiles = directory.listFiles();
        List<String> tableName=new ArrayList<>();
        FileWriter myFileWriter = new FileWriter("ERD.txt");
        String references="REFERENCES";
        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            File[] eachFile=file.listFiles();
            for(int j=0;j<eachFile.length;j++) {
                if (String.valueOf(eachFile[j]).contains("Metadata")){
                File tablemetadata = new File(String.valueOf(eachFile[j]));
                tableName.add(String.valueOf(file).replaceAll(String.valueOf(directory), "").replaceAll("Directory", "").substring(1));

                    Scanner myTableReader = new Scanner(tablemetadata);
                    myFileWriter.write("\n");
                    myFileWriter.write("TABLE NAME: "+ tableName.get(i).toUpperCase());
                    myFileWriter.write("\n");
                    myFileWriter.write("--------------------------------------------------");
                    myFileWriter.write("\n");
                    myFileWriter.write("DATA: ");
                    while (myTableReader.hasNextLine()) {
                        String tabledata = myTableReader.nextLine();
                        myFileWriter.write(tabledata);
                        myFileWriter.write("\n");

                        if (tabledata.contains("REFERENCES|")){
                            filed=tabledata.substring(0,tabledata.indexOf("|"));
                            fk=tabledata.substring(tabledata.indexOf("REFERENCES") +references.length()+1);
                            if(fk.contains("|")){
                            myFileWriter.write("\n");
                            myFileWriter.write("RELATIONSHIP: table "+tableName.get(i) +" "+filed+" has  <=> with Table "+ fk);
                            myFileWriter.write("\n");
                        }}
                    }
                }
                }

        }
        myFileWriter.close();
        System.out.println("ERD created successfully!!!!");
            }
        }



