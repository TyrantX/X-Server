import core.IOContext;
import impl.IOSelectorProvider;
import x.Xyz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @param: none
 * @description:
 * @author: KingJ
 * @create: 2019-06-06 20:39
 **/
public class UDPClientTest {
    private static boolean done;

    public static void main(String[] args) throws IOException {
        // 缓存文件目录
        File cachePath = Xyz.getCacheDir("client/test");
        IOContext.setup()
                .ioProvider(new IOSelectorProvider())
                .start();

        ServerInfo info = UDPClientSearcher.searchServer(5000);
        System.out.println("UDPClientTest => Server:" + info);

        if (info == null) {
            return;
        }

        // 当前连接数量
        int size = 0;
        final List<TCPClient> tcpClientList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            try {
                TCPClient client = TCPClient.startWith(info, cachePath);
                if (client == null) {
                    throw new NullPointerException();
                }

                tcpClientList.add(client);
                System.out.println("UDPClientTest => 连接成功：" + (++size));
            } catch (IOException | NullPointerException e) {
                System.out.println("UDPClientTest => 连接异常！");
                break;
            }
        }

        System.in.read();

        Runnable runnable = () -> {
            while (!done) {
                for (TCPClient client : tcpClientList) {
                    client.send("Hello！");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        System.in.read();

        // 等待线程完成
        done = true;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (TCPClient client : tcpClientList) {
            client.exit();
        }

        IOContext.close();
    }
}
