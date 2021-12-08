package centdb.usermodule;

import java.io.IOException;
import java.util.Scanner;

import static centdb.usermodule.UserModule.*;

public class UserOptions {
    public void startPoint() throws IOException {
        System.out.println("\nUser console:");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("Press 1 to register or else 2 to login");
        Scanner s = new Scanner(System.in);
        int u = s.nextInt();
        if (u == 1) {
            registerUser();
        } else if (u == 2) {
            loginUser();
        } else {
            System.out.println("Please choose between 1 and 2 options");
            startPoint();
        }
        s.close();
    }

}
