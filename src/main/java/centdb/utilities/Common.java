package centdb.utilities;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Common {
    public static String getTablesFileNameFromDatabase(File directory, String tableName) {
        File[] files = directory.listFiles();
        String fileName = "";
        for (File file : files) {
            List<String> filename = Arrays.asList(file.getName().split("\\."));
            if (tableName.equals(filename.get(0))) {
                fileName = file.getAbsolutePath();
            }
        }
        return fileName;
    }
}
