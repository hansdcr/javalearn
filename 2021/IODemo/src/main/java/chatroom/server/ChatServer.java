package chatroom.server;

import chatroom.client.ChatClient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {
    private final String SERVER_HOST="127.0.0.1";
    private final Integer SERVER_PORT = 8888;
    private final String QUIT = "quit";
    private ServerSocket serverSocket;
    private Map<Integer, Writer> clients;

    public ChatServer(){
        clients = new HashMap<Integer, Writer>();
    }

    public synchronized void addClient(Socket socket) throws IOException {
        if(socket != null){
            int port = socket.getPort();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );
            clients.put(port, writer);
            System.out.println("客户端[" + port + "]已连接到服务器");
        }
    }
    public synchronized void removeClient(Socket socket) throws IOException {
        if (socket != null) {
            int port = socket.getPort();
            if (clients.containsKey(port)) {
                clients.get(port).close();
            }
            clients.remove(port);
            System.out.println("客户端[" + port + "]已断开连接");
        }
    }

    public synchronized void forwardMessage(Socket socket, String msg) throws IOException {
        for(Integer port: clients.keySet()){
            if(!port.equals(socket.getPort())){
                Writer writer = clients.get(port);
                writer.write(msg);
                writer.flush();
            }
        }
    }

    public void start(){
        try {
            // 启动服务器
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("服务器已经启动，监听端口: "+ SERVER_PORT);

            while (true){
                // 等待客户端连接
                Socket socket = serverSocket.accept();
                System.out.println("客户端 ["+socket.getPort()+"]已经连接...");

                // 循环接收客户端消息
                new Thread(new ChatHandler(this, socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void close() throws IOException {
        serverSocket.close();
        System.out.println("服务端断开连接");
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }

}
