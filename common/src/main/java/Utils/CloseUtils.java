package Utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @param: none
 * @description:
 * @author: KingJ
 * @create: 2019-05-27 20:12
 **/
public class CloseUtils {
    public static void close(Closeable... closeables) {
        if (closeables == null) {
            return;
        }

        for (Closeable closeable : closeables) {
            if (closeable == null) {
                continue;
            }
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
