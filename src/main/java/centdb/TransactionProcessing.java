package centdb;

import centdb.dbquery.SelectQuery;
import centdb.lock.ApplyLock;
import centdb.lock.ReleaseLock;
import centdb.utilities.ExtractResources;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionProcessing {
	public static boolean isTransactionQuery = false;
	public static String transactionQuery="";
//	public static ArrayList<String> queries = new ArrayList<String>();
	public static ExtractResources extract = new ExtractResources();
	public static ApplyLock applyNewLock = new ApplyLock("database");;
	public static ReleaseLock releaseOldLock = new ReleaseLock("database");
	public static SelectQuery select = new SelectQuery();
	public TransactionProcessing(String database){
		applyNewLock = new ApplyLock(database);
		releaseOldLock = new ReleaseLock(database);
	}

	//	public static void queryIdentify(String query) {
	public static void main(String[] args) {
		String originalQuery = "begin transaction SELECT * FROM Employee; SELECT * FROM Employee; INSERT INTO table (id, name, email) VALUES (11, 'def', 'ghi'); INSERT INTO Person VALUES('Mouse', 'Micky','500 South Buena Vista Street, Burbank','California',43); DELETE from Person WHERE PersonID=3; SELECT * FROM Employeenew; COMMIT SELECT * FROM Employeenew;";
		String query = originalQuery.toLowerCase();
		if(query.contains("begin transaction") || query.contains("begin tran")) {
			if(query.contains("commit") || query.contains("rollback")) {
				isTransactionQuery = true;
				String[] queryParts = originalQuery.split(" ");
				int index=0;
				for(int i=0;i<queryParts.length;i++) {
					String queryStart = queryParts[i] + " " + queryParts[i+1];
					if(queryStart.equalsIgnoreCase("begin transaction") || queryStart.equalsIgnoreCase("begin tran")) {
						index = i;
						break;
					}
				}
				System.out.println(index);
				//queryLogs(queryParts[index]+" "+queryParts[index+1])
				index=index+2;
				while(index < queryParts.length) {
					if(! ( queryParts[index].equalsIgnoreCase("commit") ||  queryParts[index].equalsIgnoreCase("rollback") )) {
						transactionQuery = transactionQuery+" "+queryParts[index];
						index++;
					}
					else {
						break;
					}
				}
				//transactionQuery=transactionQuery+" "+queryParts[index];
//				System.out.println(transactionQuery);
//				System.out.println(isTransactionQuery);
			}
		}
		if(transactionQuery != null) {

			processTransaction(transactionQuery);
		}
	}
	
	public static void processTransaction(String query) {
		String[] queries = query.split(";");
		List<String> resources = new ArrayList<>();
		for(String q:queries){

			if(!q.substring(0,7).toLowerCase().equals("create")){
				System.out.println(q);
				if(!resources.contains(extract.extractResources(q))){
					resources.add(extract.extractResources(q));
				}

			}
		}
		applyNewLock.applyNewLock(resources);

		//Perform the following operations only if the last transaction statement is "Commit".
		//Because if the last statement is "Rollback", then there is no point in performing all these operations.

//		if(queries[queries.length-1].equalsIgnoreCase("commit")) {

		for(int i=0;i<queries.length;i++) {
			queries[i]=queries[i].trim()+";";
			System.out.println(queries[i]);
			//queryLogs(queries[i])
			String[] words = queries[i].split(" ");
			
			switch(words[0].trim().toLowerCase()) {
			
			case "select":
				select.selectQuery(queries[i],"database");
				System.out.println();
				//select(queries[i]);
				break;
				
			case "update":
				System.out.println("Calling Update");
				break;
				
			case "create":
				System.out.println("Calling Create Table");
				break;
				
			case "delete":
				System.out.println("Calling Delete");
				break;
				
			case "drop":
				System.out.println("Calling Drop");
				break;
				
			case "insert":
				System.out.println("Calling Insert");
				break;
				
			default:
				System.out.println("No Case Matched");
			}
		}

	//}
		releaseOldLock.releaseOldLock(resources);

	}

}
