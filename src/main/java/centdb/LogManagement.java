package centdb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogManagement {
	
	private static String generalLogsFile = "./logs/General.txt";
	private static String eventLogsFile = "./logs/Event.txt";
	private static String queryLogsFile = "./logs/Query.txt";
	private static String timestamp;
	private static File general;
	private static File event;
	private static File query;
	private static FileWriter writeGeneral;
	private static FileWriter writeEvent;
	private static FileWriter writeQuery;
	
	public static long findExecutionTime() {
		long startTime = System.currentTimeMillis();
		//execute the query i.e. Call the query executer
		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		return executionTime;
	}
	
	public static String stateDatabase(String databaseName) {
		String state = "";
		String databasePath = "./"+databaseName+"/";
		File[] allFiles = new File(databasePath).listFiles();
		state=state+"Tables:"+allFiles.length;
		int records=0;
		for(int i=0;i<allFiles.length;i++) {
			BufferedReader readFile;
			try {
				readFile = new BufferedReader(new FileReader(allFiles[i]));
				while(readFile.readLine() != null) {
					records++;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		records=records-allFiles.length;
		
		state=state+"; Number of Records:"+records;

		return state;
	}
	
	//Call generalLogs for only Valid queries because "execution time" is asked
	public static void generalLogs(String log, long time, String user, String databaseName) throws IOException {
		general = new File(generalLogsFile);
			if(general.createNewFile()) {
				System.out.println("File for General Logs has been created");
			}
			writeGeneral = new FileWriter(general,true);
			
			String state = stateDatabase(databaseName);
			
			String executionTime = Long.toString(time);
			writeGeneral.append(executionTime+" milliseconds --> ");
			writeGeneral.append(user+" --> ");
			writeGeneral.append(databaseName+" --> ");
			writeGeneral.append(state+" --> ");
			writeGeneral.append(log+"\r\n");
			writeGeneral.flush();		
	}
	
	//Call eventLogs for only Valid Queries
	public static void eventLogs(String log, String user, String databaseName) throws IOException {
		event = new File(eventLogsFile);
			if(event.createNewFile()) {
				System.out.println("File for Event Logs has been created");
			}
			writeEvent = new FileWriter(event,true);
			Date date = new Date();
			SimpleDateFormat formatDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
			timestamp = formatDate.format(date);	
			writeEvent.append(timestamp+" --> ");
			writeEvent.append(user+" --> ");
			writeEvent.append(databaseName+" --> ");
			writeEvent.append(log+"\r\n");
			writeEvent.flush();			
	}
	
	//Call queryLogs method for every query-Valid & Invalid
	public static void queryLogs(String log, String user, String databaseName) throws IOException {
//		user = "User1";
		query = new File(queryLogsFile);
			if(query.createNewFile()) {
				System.out.println("File for Query Logs has been created");
			}
			writeQuery = new FileWriter(query,true);
			Date date = new Date();
			SimpleDateFormat formatDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
			timestamp = formatDate.format(date);
			writeQuery.append(timestamp+" --> ");
			writeQuery.append(user+" --> ");
			writeQuery.append(databaseName+" --> ");
			writeQuery.append(log+"\r\n");
			writeQuery.flush();
	}

}
