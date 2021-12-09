package centdb.usermodule;

import java.io.IOException;
import java.util.Scanner;

public class MenuOptions {
    public void options(String userName) throws IOException {

        MenuOperation menuOperation = new MenuOperation();
        System.out.println("Choose one of the below operations to perform");
        System.out.println("1. Write queries");
        System.out.println("2. Export");
        System.out.println("3. Data Model");
        System.out.println("4. Analytics");
        System.out.println("5. Show Logs");
        System.out.println("Enter your choice");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        menuOperation.performOperation(choice, userName);
        System.out.println("Do you want to continue with other options, type yes or no");
        Scanner s = new Scanner(System.in);
        String continueWithOtherChoice = s.nextLine();
        if (continueWithOtherChoice.equals("yes")) {
            options(userName);
            s.close();
        } else {
            System.out.println("Thank you for using our cent DB");
        }
        sc.close();
    }
}
