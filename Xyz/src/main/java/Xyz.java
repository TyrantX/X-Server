import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Xyz {
    private static final String CACHE_DIR = "cache";

    public static File getCacheDir(String dir) {
        String path = System.getProperty("user.dir") + (File.separator + CACHE_DIR + File.separator + dir);
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdir()) {
                throw new RuntimeException("Create File Error...");
            }
        }
        return file;
    }

    public static File createTempFile(File parent) {
        String string = UUID.randomUUID().toString() + ".tmp";
        File file = new File(parent, string);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
