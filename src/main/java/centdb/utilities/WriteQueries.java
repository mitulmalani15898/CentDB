package centdb.utilities;

import centdb.dbquery.SelectQuery;
import centdb.dbquery.UpdateQuery;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WriteQueries {
    private static SelectQuery select = new SelectQuery();
    private static UpdateQuery update = new UpdateQuery();
    public static void writeQuery(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the database name");
        String database = scanner.nextLine();
        System.out.println("Enter the Query");
        String query = scanner.nextLine();

        List<String> words = Arrays.asList(query.split(" "));

        String check = words.get(0).trim().toLowerCase();

        switch(check){
            case "select":  select.selectQuery(query,database);
                            break;
            case "insert":  System.out.println("insert");
                break;
            case "update":  update.updateQuery(query,database);
                            break;
            case "drop":    System.out.println("drop");
                break;
            case "delete":  System.out.println("delete");
                break;
            case "use": System.out.println("use");
                break;
            case "create": if(words.get(1).equals("table")){
                                System.out.println("create table");
                            }else{
                                System.out.println("create database");
                            }
                            break;
            default:    System.out.println("Invalid Input");

        }

    }
    public static void main(String[] args){
        writeQuery();
    }

}
