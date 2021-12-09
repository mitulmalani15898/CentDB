package centdb.analytics;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Analytics {
    public static void countAllQueries(String userName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("./logs/Query.txt"))) {
            String userLine;
            ArrayList<String> str = new ArrayList<String>();
            while((userLine = br.readLine()) != null) {
                String[] values = userLine.split(" ");
                if(values[6].equals(userName)) {
                    str.add(values[8]);
                }
            }
            if(str.size() != 0){
                countDatabases(str, userName);
            } else {
                System.out.println("You didn't perform any operations to show the analytics");
            }

        }
    }

    public static void countUpdateQueries(String userName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("./logs/Event.txt"))) {
            String userLine;
            ArrayList<String> str = new ArrayList<String>();
            while((userLine = br.readLine()) != null) {
                String[] values = userLine.split(" ");
                if(values[6].equals(userName)) {
                    str.add(values[10]);
                }
            }
            if(str.size() != 0){
                countUpdatedTables(str, userName);
            } else {
                System.out.println("You didn't perform any update operations to show the analytics");
            }

        }
    }

    public static void countUpdatedTables(ArrayList<String> list, String username) throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("AnalyticsFileForTableUpdates.txt"))) {
            TreeMap<String, Integer> tmap = new TreeMap<String, Integer>();
            for (String t : list) {
                Integer c = tmap.get(t);
                tmap.put(t, (c == null) ? 1 : c + 1);
            }

            bw.write("Analytics report for the logged in user for all update operations performed on Tables" + '\n');
            bw.write("--------------------------------------------------------------" + '\n');
            bw.write('\n');

            System.out.println("Table updations:");
            System.out.println("---------------------------------------------------------");

            for (Map.Entry m : tmap.entrySet()){
                String s = "Total  " + m.getValue() + " Update operations are performed on Table - " + m.getKey();
                System.out.println(s);
                bw.write(s + '\n');
            }
            System.out.println();
            System.out.println();
        }
    }

public static void countDatabases(ArrayList<String> list, String username) throws IOException {
    try(BufferedWriter bw = new BufferedWriter(new FileWriter("AnalyticsFileForDatabases.txt"))) {
        TreeMap<String, Integer> tmap = new TreeMap<String, Integer>();
        for (String t : list) {
            Integer c = tmap.get(t);
            tmap.put(t, (c == null) ? 1 : c + 1);
        }
        bw.write("Analytics report for the logged in user for all queries executed on Databases" + '\n');
        bw.write("--------------------------------------------------------------" + '\n');
        bw.write('\n');

        System.out.println("Database queries");
        System.out.println("---------------------------------------------------------");


        for (Map.Entry m : tmap.entrySet()){
            String s = "User with the username - " + username + " submitted " + m.getValue() + " queries on database - " + m.getKey();
            System.out.println(s);
            bw.write(s + '\n');
        }
        System.out.println();
        System.out.println();
    }
}
}
