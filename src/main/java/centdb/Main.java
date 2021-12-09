package centdb;

import centdb.lock.ApplyLock;
import centdb.lock.ReleaseLock;
import centdb.usermodule.UserOptions;

import java.io.IOException;

public class Main {
    

    public static void main(String[] args) {
        try {
            UserOptions uo = new UserOptions();
            uo.startPoint();
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
}
