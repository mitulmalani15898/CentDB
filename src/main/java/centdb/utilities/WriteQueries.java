package centdb.utilities;

import centdb.DBQuery.*;
import centdb.dbquery.UpdateQuery;

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

            switch (check) {
                case "select":
                    select.selectQuery(query, database);
                    break;
                case "insert":
                    insert.insertQuery(query, database);
                    break;
                case "update":
                    update.updateQuery(query, database);
                    break;
                case "drop":
                    drop.dropQuery(query, database);
                    break;
                case "delete":
                    delete.deleteQuery(query, database);
                    break;
                case "use":
                    useDatabase.useDatabaseQuery(query);
                    break;
                case "create":
                    if (words.get(1).equals("table")) {
                        createTable.createTableQuery(query, database);
                    } else {
                        createDatabase.createDatabaseQuery(query, database);
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
