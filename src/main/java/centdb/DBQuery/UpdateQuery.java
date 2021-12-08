package centdb.dbquery;

import centdb.utilities.ColumnDataType;
import static centdb.dbquery.constants.Constants.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateQuery {
    ColumnDataType obj = new ColumnDataType();
    public static void updateQuery(String query,String database){

        List<String> ls = Arrays.asList(query.split(" "));
        Pattern pattern = Pattern.compile(updateQuery,Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(query);

        String tableName = "";
        while(match.find()){
            tableName = ls.get(1);
            int start = 3;
            File directory = new File("database/"+database+"/"+tableName);
            HashMap<String,String> columnsToUpdate = new HashMap<String,String>();
            while(ls.indexOf("where")>start || ls.indexOf("WHERE")>start){
                String temp = "";
                if(ls.get(start+2).substring(ls.get(start+2).length()-1,ls.get(start+2).length()).equals(",")){

                    temp = ls.get(start+2).substring(0,ls.get(start+2).length()-1);
                }
                else{

                    temp = ls.get(start+2);

                }
                columnsToUpdate.put(ls.get(start),temp);
                start = start + 3;
            }

            HashMap<String, String> condition = new HashMap<String,String>();
//            for(String hm:columnsToUpdate.keySet()){
//                System.out.println(hm+" "+columnsToUpdate.get(hm));
//            }
            condition.put(ls.get(start+1),ls.get(start+3).substring(0,ls.get(start+3).length()-1));


            int [] map = new int [columnsToUpdate.size()];
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(directory+"/data.txt"));
                FileWriter writer = new FileWriter(directory+"/datanew.txt");
                String firstLine = bufferedReader.readLine();
                List<String> tableColumnNames = Arrays.asList(firstLine.split("\\|"));
                String temp = firstLine+"\n";
                int conditionColumnIndex = tableColumnNames.indexOf(ls.get(start+1));

                int i = 0;
                for(String col: tableColumnNames){
                    if(columnsToUpdate.containsKey(col)){
//                        System.out.println(col+" Id:"+tableColumnNames.indexOf(col));
                        map[i] = tableColumnNames.indexOf(col);
//                        System.out.println(map[i]+" Column:"+tableColumnNames.get(map[i]));
                        i++;
                    }
                }
                //System.out.println("end");
                String line = null;
                while((line=bufferedReader.readLine())!=null){

                    int k = 0;
                    List<String> check = Arrays.asList(line.split("\\|"));


                    if(check.get(conditionColumnIndex).equals(ls.get(start+3).substring(0,ls.get(start+3).length()-1))){
//                        System.out.println("Contains");
                        int j = 0;

                        for(String ch:check){
                            if(j<map.length){
                                if(k==map[j]){
//                                System.out.println("Index: "+map[j]+"  " +tableColumnNames.get(j));
                                    temp += columnsToUpdate.get(tableColumnNames.get(map[j]));
                                    temp += "|";
                                    j++;
                                }else{
                                    temp += ch;
                                    temp += "|";
                                }
                            }else{
                                temp += ch;
                                temp += "|";
                            }

                            k++;
                        }
                    }else{
                        temp += line;
                    }
                    temp += "\n";
                }

                writer.write(temp);

                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
