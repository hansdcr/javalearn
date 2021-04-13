package Socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        final int DEFAULT_PORT = 8888;
        ServerSocket serverSocket = null;


        try {
            // 绑定监听端口
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("启动服务器，监听端口" + DEFAULT_PORT);

            // accept阻塞的等待连接
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("客户端[" + socket.getPort() + "]已连接");

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                // 读取客户端发送的消息
                String msg = reader.readLine();
                if(msg != null){
                    System.out.println("客户端发送了["+socket.getPort()+"]: "+ msg);

                    // 回复客户发送的消息
                    writer.write("服务器回复：" + msg + "\n");
                    writer.flush(); // 刷新缓冲区
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
          if(serverSocket != null){
              try {
                  serverSocket.close();
                  System.out.println("关闭服务器端socket");
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
        }
    }
}
