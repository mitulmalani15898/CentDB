package centdb.DBQuery;

import centdb.utilities.ColumnDataType;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static centdb.dbquery.constants.Constants.*;

public class SelectQuery {
    ColumnDataType obj = new ColumnDataType();
    public void selectQuery(String query,String database){
        List<String> ls = Arrays.asList(query.split(" "));
        final Instant instance = Instant.now();
        //String selectQueryRegex = "^(?i)(SELECT\\s[a-zA-Z\\d]+(,\\s[a-zA-Z\\d]+)*\\sFROM\\s[a-zA-Z\\\\d]+\\sWHERE\\s[a-zA-Z\\d]+\\s[=]\\s[a-zA-Z\\d]+;)$";
        String checkExpression = (ls.get(1).equals("*"))?selectAllQueryCheck:selectFieldsQueryCheck;
        Pattern pattern = Pattern.compile(checkExpression,Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(query);
        String tableName = "";
        List<String> columnName = new ArrayList<>();
        if(match.find()){

            if(ls.get(1).equals("*")) {

                if(ls.size()==4){
                    tableName = ls.get(3).substring(0, ls.get(3).length() - 1);
                }
                else{
                    tableName = ls.get(3);
                }
                File directory = new File("database/"+database+"/"+tableName);
                if(directory.exists()) {

                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader("database/"+database+"/"+tableName));
                        String firstLine = bufferedReader.readLine();
                        List<String> tableColumnNames = Arrays.asList(firstLine.split("\\|"));
                        for(String col: tableColumnNames){
                            System.out.print(col+"|");
                        }
                        System.out.println();
                        if(ls.contains("where")||ls.contains("WHERE")){

                            String column = ls.get(5);
                            int index = tableColumnNames.indexOf(column);

                            if( index != -1){
                                String relation = ls.get(6);
                                String value = ls.get(7).substring(0, ls.get(7).length() - 1);
                                if(relation.equals("=")){
                                    String line = null;
                                    while((line=bufferedReader.readLine())!=null){
                                        List<String> checkLine = Arrays.asList(line.split("\\|"));
                                        if(checkLine.get(index).equals(value)){
                                            for(String pr:checkLine){
                                                System.out.print(pr+"|");
                                            }
                                            System.out.println();
                                        }
                                    }
                                }else if(relation.equals(">=") && (obj.columnDataType(directory,"data.txt",column).equals("int") || obj.columnDataType(directory,"data.txt",column).equals("decimal")) ){
                                    String line = null;
                                    while((line=bufferedReader.readLine())!=null){
                                        List<String> checkLine = Arrays.asList(line.split("\\|"));
                                        if(Double.parseDouble(checkLine.get(index))>=Double.parseDouble(value)){
                                            for(String pr:checkLine){
                                                System.out.print(pr+"|");
                                            }
                                            System.out.println();
                                        }
                                    }
                                }else if(relation.equals("<=") && (obj.columnDataType(directory,"data.txt",column).equals("int") || obj.columnDataType(directory,"data.txt",column).equals("decimal"))){
                                    String line = null;
                                    while((line=bufferedReader.readLine())!=null){
                                        List<String> checkLine = Arrays.asList(line.split("\\|"));
                                        if(Double.parseDouble(checkLine.get(index))<=Double.parseDouble(value)){
                                            for(String pr:checkLine){
                                                System.out.print(pr+"|");
                                            }
                                            System.out.println();
                                        }
                                    }
                                }else{
                                    System.out.println("Trying to compare non-integer/decimal data types");
                                }
                            }
                        }
                        else{
                            String line =  null;
                            while((line=bufferedReader.readLine())!=null){
                                List<String> checkLine = Arrays.asList(line.split("\\|"));
                                for(String pr:checkLine){
                                    System.out.print(pr+"|");
                                }
                                System.out.println();
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("Table Doesnt Exist");
                }
            }
            else{

                List<String> selectColumns = new ArrayList<>();
                for(int i=1;i<ls.indexOf("from");i++){
                    if(ls.get(i).substring(ls.get(i).length()-1,ls.get(i).length()).equals(",")){
                        selectColumns.add(ls.get(i).substring(0,ls.get(i).length()-1));
                    }else{
                        selectColumns.add(ls.get(i));
                    }
                }
                tableName = ls.get(ls.indexOf("from")+1);
                File directory = new File(database+"/"+tableName);
                if(directory.exists()){

                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader("database/"+database+"/"+tableName));
                        String firstLine = bufferedReader.readLine();
                        List<String> tableColumnNames = Arrays.asList(firstLine.split("\\|"));
                        int [] track = new int [selectColumns.size()];
                        int start = 0;
                        for(String col: selectColumns){
                            if(tableColumnNames.contains(col)){
                                track[start] = tableColumnNames.indexOf(col);
                                start++;
                            }
                            System.out.print(col+"|");
                        }
                        if(ls.contains("where")||ls.contains("WHERE")){

                            String column;
                            if(ls.contains("where")){
                                column = ls.get(ls.indexOf("where")+1);
                            }
                            else{
                                column = ls.get(ls.indexOf("WHERE")+1);
                            }
                            int index = tableColumnNames.indexOf(column);
                            if(index != -1){

                                String relation = ls.get(ls.indexOf(column)+1);
                                String value = ls.get(ls.indexOf(relation)+1);
                                value  = value.substring(0,value.length()-1);


                                if(relation.equals("=")){

                                    String line = null;

                                    System.out.println();
                                    while((line=bufferedReader.readLine())!=null){
                                        List<String> checkLine = Arrays.asList(line.split("\\|"));

                                        if(checkLine.get(index).equals(value)){
                                            int begin = 0;
                                            for(int i=0;i<checkLine.size();i++){
                                                if(i==track[begin]){

                                                    System.out.print(checkLine.get(i)+"|");
                                                    begin++;
                                                    if(begin==track.length){
                                                        break;
                                                    }
                                                }
                                            }
                                            System.out.println();
                                        }
                                    }
                                }
                            }


                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

//                System.out.println("Table name: "+tableName);
        }else{
            System.out.println("Syntax error please review your query");
        }

    }
}


//if(match.find() && ifTable() && ifTableFileExists()){
