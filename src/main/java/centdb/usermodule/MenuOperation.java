package centdb.usermodule;

import centdb.erdiagram.ERDCreation;
import centdb.sqldump.SqlDumpGenerator;
import centdb.utilities.WriteQueries;

import java.io.IOException;

public class MenuOperation {
    public void performOperation(String choice) throws IOException {
        switch (choice) {
            case "1":
                //Writing queries
                WriteQueries.writeQuery();
                break;
            case "2":
                //Export
                SqlDumpGenerator sqlDumpGenerator = new SqlDumpGenerator();
                sqlDumpGenerator.generateSqlDump();
                break;
            case "3":
                //Data model
                try {
                    ERDCreation erdCreation = new ERDCreation();
                    erdCreation.generateErDiagram();
                } catch (IOException ioException) {
                    System.out.println(ioException.getMessage());
                }
                break;
            case "4":
                //Analytics

                break;
            default:
                System.out.println("Incorrect choice, please enter valid choice");
                break;
        }
    }
}
