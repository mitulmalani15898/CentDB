package centdb.usermodule;

import centdb.analytics.Analytics;

import java.io.IOException;

public class MenuOperation {
    public void performOperation(int choice, String userName) throws IOException {
        switch(choice) {
            case 1: //Writing queries
                    break;
            case 2: //Export
                    break;
            case 3: //Data model
                break;
            case 4:
                Analytics analyticsObject = new Analytics();
                analyticsObject.countAllQueries(userName);
                analyticsObject.countUpdateQueries(userName);
                break;
            default: System.out.println("Incorrect choice, please enter valid choice");

        }
    }
}
