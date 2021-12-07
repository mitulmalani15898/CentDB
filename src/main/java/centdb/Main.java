package centdb;

import centdb.DBQuery.SelectQuery;

import java.util.Scanner;

public class Main {
    public static String databaseName = "database";
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter the select query");
        String input;
        SelectQuery select = new SelectQuery();
//        input = scn.nextLine();
        select.selectQuery("select id, product_name, product_price, status from Employee where product_price = 56.78;", databaseName);
    }
}
