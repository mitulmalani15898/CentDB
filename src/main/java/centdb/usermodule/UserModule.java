package centdb.usermodule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserModule {
    public static void registerUser() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Create your user id to register");
        String userId = sc.nextLine();
        System.out.println("Create a password");
        String passWord = sc.nextLine();
        System.out.println("Choose answer to your security question - what's your favorite game");
        String securityAnswer = sc.nextLine();
        FileWriter f = null;
        try {
            f = new FileWriter("User_Profile.txt", true);
            f.write(performEncryption(userId) + ",@&" + performEncryption(passWord) + ",@&" + securityAnswer + "\n");
            f.close();
        }
        catch(IOException exception) {
            System.out.println(exception);
        }
        System.out.println("User registered successfully");
        UserOptions u = new UserOptions();
        u.startPoint();
    }

    public static void loginUser() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your username to login");
        String userId = sc.nextLine();
        System.out.println("Enter your password");
        String passWord = sc.nextLine();
        System.out.println("Enter security answer - what's your favorite game");
        String securityAnswer = sc.nextLine();
        boolean flag;
        flag = authenticateUser(performEncryption(userId), performEncryption(passWord), securityAnswer);
        if(flag == true) {
            MenuOptions menuOptions = new MenuOptions();
            menuOptions.options();

        } else {
            System.out.println("Username or password is incorrect");
        }
    }

    public static String performEncryption(String stringToEncrypt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch(NoSuchAlgorithmException exception) {
            System.out.println(exception);
        }
        md.update(stringToEncrypt.getBytes());
        String hashedString = new String(md.digest());
        return hashedString;
    }

    public static boolean authenticateUser(String userId, String passWord, String securityAnswer) {
        Scanner fileScanner = null;
        String path = "User_Profile.txt";
        try{
            fileScanner = new Scanner(new File(path));
        }
        catch(FileNotFoundException exception) {
            System.out.println(exception);
        }
        ArrayList<String> list = new ArrayList<String>();
        while(fileScanner.hasNextLine()) {
            list.add(fileScanner.nextLine());
        }
        fileScanner.close();
        for (String data: list) {
            String[] fieldList = data.split(",@&");
            for(int i=0; i<fieldList.length; i++) {
                if(i%3 == 0) {
                    if(fieldList[i].equals(userId) && fieldList[i+1].equals(passWord) && fieldList[i+2].equals(securityAnswer)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
