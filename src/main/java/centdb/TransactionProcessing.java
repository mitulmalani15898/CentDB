package centdb;

public class TransactionProcessing {
	public static boolean isTransactionQuery = false;
	public static String transactionQuery="";
	
//	public static void queryIdentify(String query) {
	public static void main(String[] args) {
		String originalQuery = "SELECT * FROM Person BEGIN TRANSACTION INSERT INTO Person VALUES('Mouse', 'Micky','500 South Buena Vista Street, Burbank','California',43) SAVE TRANSACTION InsertStatement DELETE Person WHERE PersonID=3 SELECT * FROM Person COMMIT SELECT * FROM Person";
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
				while(index < queryParts.length) {
					if(! ( queryParts[index].equalsIgnoreCase("commit") ||  queryParts[index].equalsIgnoreCase("rollback") )) {
						transactionQuery = transactionQuery+" "+queryParts[index];
						index++;
					}
					else {
						break;
					}
				}
				transactionQuery=transactionQuery+" "+queryParts[index];
				System.out.println(transactionQuery);
				System.out.println(isTransactionQuery);
			}
		}
		if(transactionQuery != null) {
			processTransaction(transactionQuery);
		}
	}
	
	public static void processTransaction(String query) {
		
	}

}
