package chatroom.client;

import chatroom.server.ChatHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Queue;

public class UserInputHandler implements Runnable {
    private ChatClient chatClient;
    private String QUIT = "quit";

    public UserInputHandler(ChatClient chatClient){
        this.chatClient = chatClient;
    }

    public void run() {
        try {
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                String input = consoleReader.readLine();
                // 向服务器发送消息
                chatClient.send(input);

                if(input.equals(QUIT)){
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
