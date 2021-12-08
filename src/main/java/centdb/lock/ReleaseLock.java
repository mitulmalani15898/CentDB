package centdb.lock;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReleaseLock {
    public static File releaseFile;
    public static String directory;
    public ReleaseLock(String directory){
        releaseFile = new File(directory+"/"+"LockFile.txt");
        this.directory = directory;
        try {
            releaseFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void releaseOldLock(List<String> resources){
        String readLockFile = null;
        try {
            readLockFile = new String(Files.readAllBytes(Paths.get(String.valueOf(releaseFile))));
            List<String> lockedTables = new ArrayList<>();
            lockedTables = Arrays.asList(readLockFile.split("\n"));
            List<String> lockedFinal = new ArrayList<>();
            String temp="";
            for(String lock:lockedTables){
                lockedFinal.add(lock);
            }
            for(String resource:resources){
                if(lockedFinal.contains(resource)){
                    lockedFinal.remove(resource);
                }
            }
            for(String updated:lockedFinal){
                temp+=updated+"\n";
            }
            FileWriter writer = new FileWriter(directory+"/"+"LockFile.txt");
            writer.write(temp);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
