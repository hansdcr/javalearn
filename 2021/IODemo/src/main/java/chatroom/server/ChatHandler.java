package chatroom.server;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatHandler implements  Runnable {
    private Socket socket;
    private ChatServer server;
    private final String QUIT = "quit";

    public ChatHandler(ChatServer server, Socket socket){
        this.server = server;
        this.socket = socket;
    }



    public void run() {
        if(socket != null){
            try {
                server.addClient(socket);

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String msg = null;
                while ((msg = reader.readLine()) != null){
                    msg = "客户端["+socket.getPort()+"]发来消息: "+ msg +"\n";
                    System.out.println(msg);

                    // 把消息转发给其他客户端
                    server.forwardMessage(socket,msg);

                    if(msg.equals(QUIT)){
                        System.out.println("客户端["+socket.getPort()+"]断开连接.");
                        break;
                    }
                }

            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    server.removeClient(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }
}
