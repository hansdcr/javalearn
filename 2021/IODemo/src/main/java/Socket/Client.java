package Socket;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        final int DEFAULT_SERVER_PORT = 8888;
        final String DEFAULT_SERVER_HOST = "127.0.0.1";
        Socket socket = null;
        BufferedWriter writer = null;

        // 创建socket
        try {
            socket = new Socket(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            //   从终端读取消息
            String input = consoleReader.readLine();

            // 发送数据
            writer.write(input +"\n");
            writer.flush();

            // 读取服务器返回的消息
            String msg = reader.readLine();
            System.out.println(msg);


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.close();
                    System.out.println("关闭客户端socket");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
