package centdb;

import centdb.DBQuery.CreateDatabaseQuery;
import centdb.DBQuery.CreateTableQuery;
import centdb.DBQuery.DeleteQuery;
import centdb.DBQuery.DropQuery;
import centdb.DBQuery.InsertQuery;
import centdb.DBQuery.SelectQuery;
import centdb.DBQuery.UpdateQuery;
import centdb.DBQuery.UseDatabaseQuery;
import centdb.lock.ApplyLock;
import centdb.lock.ReleaseLock;
import centdb.utilities.ExtractResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionProcessing {
	
	private static SelectQuery select = new SelectQuery();
    private static UpdateQuery update = new UpdateQuery();
    private static InsertQuery insert = new InsertQuery();
    private static DropQuery drop = new DropQuery();
    private static DeleteQuery delete = new DeleteQuery();
    private static UseDatabaseQuery useDatabase = new UseDatabaseQuery();
    private static CreateDatabaseQuery createDatabase = new CreateDatabaseQuery();
    private static CreateTableQuery createTable = new CreateTableQuery();
    
    public static boolean isTransactionQuery = false;
    public static String transactionQuery = "";

    public static ExtractResources extract = new ExtractResources();
//    public static ApplyLock applyNewLock = new ApplyLock("database");
//    public static ReleaseLock releaseOldLock = new ReleaseLock("database");
    public static ApplyLock applyNewLock;
    public static ReleaseLock releaseOldLock;

    public TransactionProcessing(String database) {
        applyNewLock = new ApplyLock(database);
        releaseOldLock = new ReleaseLock(database);
    }

    public static void queryIdentify(String query1, String userId, String database) {
//        String originalQuery = "begin transaction SELECT * FROM customer; SELECT * FROM product; INSERT INTO product(product_id, product_name, product_price, customer_id) VALUES (7, bed, 68.96, 3); DELETE FROM customer WHERE customer_id=1; SELECT * FROM customer; COMMIT";
        String originalQuery = query1;
        String query = originalQuery.toLowerCase();
        String last = "";
        if (query.contains("begin transaction") || query.contains("begin tran")) {
            if (query.contains("commit") || query.contains("rollback")) {
                isTransactionQuery = true;
                String[] queryParts = originalQuery.split(" ");
                int index = 0;
                for (int i = 0; i < queryParts.length; i++) {
                    String queryStart = queryParts[i] + " " + queryParts[i + 1];
                    if (queryStart.equalsIgnoreCase("begin transaction") || queryStart.equalsIgnoreCase("begin tran")) {
                        index = i;
                        break;
                    }
                }
                System.out.println(index);
                //queryLogs(queryParts[index]+" "+queryParts[index+1])
                index = index + 2;
                while (index < queryParts.length) {
                    if (!(queryParts[index].equalsIgnoreCase("commit") || queryParts[index].equalsIgnoreCase("rollback"))) {
                        transactionQuery = transactionQuery + " " + queryParts[index];
                        index++;
                    } else {
                        break;
                    }
                }
                //transactionQuery=transactionQuery+" "+queryParts[index];
                last = queryParts[index];
				System.out.println("Is this a Transaction Query: "+isTransactionQuery);
				System.out.println("The Transaction Query is:\r\n"+transactionQuery);
            }
        }
        if (transactionQuery != null && last.equalsIgnoreCase("commit")) {

            try {
				processTransaction(transactionQuery, userId, database);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        else {
        	System.out.println("Rollbacked the Transaction Successfully");
        }
    }

    public static void processTransaction(String query2, String userId, String database) throws IOException {
    	long startTime, endTime, executionTime;
        String[] queries = query2.split(";");
        List<String> resources = new ArrayList<>();
        for (String q : queries) {

            if (!q.substring(0, 7).toLowerCase().equals("create")) {
                System.out.println(q);
                if (!resources.contains(extract.extractResources(q))) {
                    resources.add(extract.extractResources(q));
                }

            }
        }
        applyNewLock.applyNewLock(resources);

        //Perform the following operations only if the last transaction statement is "Commit".
        //Because if the last statement is "Rollback", then there is no point in performing all these operations.

//		if(queries[queries.length-1].equalsIgnoreCase("commit")) {

        for (int i = 0; i < queries.length; i++) {
            queries[i] = queries[i].trim() + ";";
            System.out.println(queries[i]);
            String query = queries[i];
            String[] words = query.split(" ");

            switch (words[0].trim().toLowerCase()) {
            
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
                if (words[1].trim().toLowerCase().equals("table")) {
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
        }

        //}
        releaseOldLock.releaseOldLock(resources);

    }

}
