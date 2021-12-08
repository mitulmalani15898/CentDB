package centdb.utilities;

import centdb.DBQuery.*;
import centdb.LogManagement;
import centdb.TransactionProcessing;
import centdb.dbquery.UpdateQuery;
import centdb.usermodule.UserModule;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WriteQueries {
    private static SelectQuery select = new SelectQuery();
    private static UpdateQuery update = new UpdateQuery();
    private static InsertQuery insert = new InsertQuery();
    private static DropQuery drop = new DropQuery();
    private static DeleteQuery delete = new DeleteQuery();
    private static UseDatabaseQuery useDatabase = new UseDatabaseQuery();
    private static CreateDatabaseQuery createDatabase = new CreateDatabaseQuery();
    private static CreateTableQuery createTable = new CreateTableQuery();

    public static void writeQuery() throws IOException {
        String choice = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the database name");
        String database = scanner.nextLine();
        do {
            System.out.println("Enter the Query");
            String query = scanner.nextLine();
            List<String> words = Arrays.asList(query.split(" "));
            String check = words.get(0).trim().toLowerCase();
            String userId = UserModule.loginUserId;

            switch (check) {
                case "begin":
                    TransactionProcessing.queryIdentify(query);
                    break;
                case "select":
                    LogManagement.queryLogs(query, userId, database);
                    select.selectQuery(query, database);
                    break;
                case "insert":
                    LogManagement.queryLogs(query, userId, database);
                    insert.insertQuery(query, database);
                    break;
                case "update":
                    LogManagement.queryLogs(query, userId, database);
                    update.updateQuery(query, database);
                    break;
                case "drop":
                    LogManagement.queryLogs(query, userId, database);
                    drop.dropQuery(query, database);
                    break;
                case "delete":
                    LogManagement.queryLogs(query, userId, database);
                    delete.deleteQuery(query, database);
                    break;
                case "use":
                    LogManagement.queryLogs(query, userId, database);
                    useDatabase.useDatabaseQuery(query);
                    break;
                case "create":
                    if (words.get(1).equals("table")) {
                        LogManagement.queryLogs(query, userId, database);
                        createTable.createTableQuery(query, database);
                    } else {
                        LogManagement.queryLogs(query, userId, database);
                        createDatabase.createDatabaseQuery(query);
                    }
                    break;
                default:
                    System.out.println("Invalid Input");
            }
            System.out.println("Do you want to continue writing queries? (y, n):");
            choice = scanner.nextLine();
        }
        while (choice.equals("y"));
    }
}
