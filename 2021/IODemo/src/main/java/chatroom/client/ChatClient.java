package chatroom.client;

import java.io.*;
import java.net.Socket;

public class ChatClient {
    private final String SERVER_HOST="127.0.0.1";
    private final Integer SERVER_PORT = 8888;
    private final String QUIT = "quit";
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;

    public String receive() throws IOException {
        String msg = null;
        if(!socket.isInputShutdown()){
            msg = reader.readLine();
        }
        return msg;
    }

    public void send(String msg) throws IOException {
        if(!socket.isOutputShutdown()){
            System.out.println("--------");
            writer.write(msg+"\n");
            writer.flush();
        }
    }


    public void start(){
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            System.out.println("客户端已经启动，port: "+socket.getLocalPort());

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // 处理用户输入
            new Thread(new UserInputHandler(this)).start();

            // 读取服务器转发的消息
            String msg = null;
            while ((msg = receive()) != null ){
                System.out.println(msg);
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

    public void close() throws IOException {
        socket.close();
        System.out.println("客户端断开连接.");
    }

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        chatClient.start();
    }
}
