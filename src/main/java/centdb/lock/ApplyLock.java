package centdb.lock;

import centdb.readwrite.Read;
import centdb.readwrite.Write;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplyLock {
    public static File lockFile;
    public static String directory;
    public ApplyLock(String directory){
        lockFile = new File(directory+"/"+"LockFile.txt");
        this.directory = directory;
        try {
            lockFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void applyNewLock(List<String> resources){
        try {
            String readLockFile = new String(Files.readAllBytes(Paths.get(String.valueOf(lockFile))));
            List<String> lockedTables = new ArrayList<>();
            lockedTables = Arrays.asList(readLockFile.split("\n"));
            List<String> lockedFinal = new ArrayList<>();
            String temp = "";
            for(String lock:lockedTables){
                lockedFinal.add(lock);
                temp+=lock+"\n";
            }
            for(String resource:resources){

                if(lockedFinal.contains(resource)){
                    System.out.println("control");
                    throw new LockException("Resources requested are currently locked like "+resource);

                }
                else{
                    lockedFinal.add(resource);
                    temp+=resource+"\n";
                }
            }
            FileWriter writer = new FileWriter(directory+"/"+"LockFile.txt");
            writer.write(temp);
            writer.close();
        } catch (IOException | LockException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Applied Lock");

    }
}
