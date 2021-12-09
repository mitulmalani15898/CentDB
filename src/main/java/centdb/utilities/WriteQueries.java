package centdb.utilities;

import centdb.DBQuery.*;
import centdb.LogManagement;
import centdb.TransactionProcessing;
import centdb.DBQuery.UpdateQuery;
import centdb.lock.ApplyLock;
import centdb.lock.ReleaseLock;
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
        long startTime, endTime, executionTime;
      ApplyLock applyNewLock = new ApplyLock(database);
      ReleaseLock releaseOldLock = new ReleaseLock(database);
      TransactionProcessing tp = new TransactionProcessing(database);
        do {
            System.out.println("Enter the Query");
            String query = scanner.nextLine();
            List<String> words = Arrays.asList(query.split(" "));
            String check = words.get(0).trim().toLowerCase();
            String userId = UserModule.loginUserId;

            switch (check) {
                case "begin":
                    TransactionProcessing.queryIdentify(query, userId, database);
                    break;
                case "select":
                    LogManagement.queryLogs(query, userId, database);
                    startTime = System.currentTimeMillis();
                    select.selectQuery(query, database);
                    endTime = System.currentTimeMillis();
                    executionTime = endTime - startTime;
                    LogManagement.generalLogs(query, executionTime, userId, database);
                    break;
                case "insert":
                    LogManagement.queryLogs(query, userId, database);
                    startTime = System.currentTimeMillis();
                    insert.insertQuery(query, database);
                    endTime = System.currentTimeMillis();
                    executionTime = endTime - startTime;
                    LogManagement.generalLogs(query, executionTime, userId, database);
                    break;
                case "update":
                    LogManagement.queryLogs(query, userId, database);
                    startTime = System.currentTimeMillis();
                    update.updateQuery(query, database);
                    endTime = System.currentTimeMillis();
                    executionTime = endTime - startTime;
                    LogManagement.generalLogs(query, executionTime, userId, database);
                    break;
                case "drop":
                    LogManagement.queryLogs(query, userId, database);
                    startTime = System.currentTimeMillis();
                    drop.dropQuery(query, database);
                    endTime = System.currentTimeMillis();
                    executionTime = endTime - startTime;
                    LogManagement.generalLogs(query, executionTime, userId, database);
                    break;
                case "delete":
                    LogManagement.queryLogs(query, userId, database);
                    startTime = System.currentTimeMillis();
                    delete.deleteQuery(query, database);
                    endTime = System.currentTimeMillis();
                    executionTime = endTime - startTime;
                    LogManagement.generalLogs(query, executionTime, userId, database);
                    break;
                case "use":
                    LogManagement.queryLogs(query, userId, database);
                    startTime = System.currentTimeMillis();
                    useDatabase.useDatabaseQuery(query);
                    endTime = System.currentTimeMillis();
                    executionTime = endTime - startTime;
                    LogManagement.generalLogs(query, executionTime, userId, database);
                    break;
                case "create":
                    if (words.get(1).equals("table")) {
                        LogManagement.queryLogs(query, userId, database);
                        startTime = System.currentTimeMillis();
                        createTable.createTableQuery(query, database);
                        endTime = System.currentTimeMillis();
                        executionTime = endTime - startTime;
                        LogManagement.generalLogs(query, executionTime, userId, database);
                    } else {
                        LogManagement.queryLogs(query, userId, database);
                        startTime = System.currentTimeMillis();
                        createDatabase.createDatabaseQuery(query);
                        endTime = System.currentTimeMillis();
                        executionTime = endTime - startTime;
                        LogManagement.generalLogs(query, executionTime, userId, database);
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
