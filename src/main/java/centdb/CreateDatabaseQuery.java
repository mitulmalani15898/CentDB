package centdb;

import centdb.utilities.Common;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateDatabaseQuery {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String query = "";
        // System.out.println("Enter query:");
        // query = scanner.nextLine();
        query = "CREATE DATABASE databasename;";
        String createDatabaseRegex = "(CREATE\\s+DATABASE)\\s+(\\S+)\\;?";
        Pattern regex = Pattern.compile(createDatabaseRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(query);
        if (matcher.find()) {
            String createDatabaseName = "", filePath = "";
            // Group 1 - CREATE DATABASE
            // Group 2 - databasename
            createDatabaseName = matcher.group(2);
            createDatabaseName=createDatabaseName.replaceAll(";","");
            System.out.println(createDatabaseName);
            File directory = new File(createDatabaseName);
            if (directory.exists()) {
                System.out.println("Directory already exist.");
            } else {
                directory.mkdirs();
            }
        } else {
            System.out.println("Please provide valid create databse query.");
        }
    }
}
