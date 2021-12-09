package centdb.DBQuery;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UseDatabaseQuery {
    public void useDatabaseQuery(String query) {
        String useDatabaseRegex = "(USE\\s+)(\\S+)\\;?";
        Pattern regex = Pattern.compile(useDatabaseRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(query);
        if (matcher.find()) {
            String databaseName = "", filePath = "";
            // Group 1 - Use DATABASE
            databaseName = matcher.group(2);

            databaseName = databaseName.replaceAll(";", "");
            File directory = new File("database" + File.separator + databaseName);
            if (directory.exists()) {
                System.out.println("USE " + databaseName);
            } else {
                System.out.println("Directory does not exist.");
            }
        } else {
            System.out.println("Please provide valid  databse query.");
        }
    }
}
