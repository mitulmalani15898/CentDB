package centdb.utilities;

import java.io.File;

public class Common {
    public static String getTablesFilePathFromDatabase(File directory, String tableName) {
        File[] files = directory.listFiles();
        String filePath = "";
        for (File file : files) {
            if (tableName.equals(file.getName())) {
                filePath = file.getAbsolutePath();
            }
        }
        return filePath;
    }
}
