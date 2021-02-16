package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;


public class GroupChatServer {

    private static int portNumber = 5555;
    private static final int maxClientsCount = 10;
    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static ServerGui serverGui;
    private static final clientThread[] threads = new clientThread[maxClientsCount];

    public static void main(String args[]) {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }
        serverGui=new ServerGui();
        serverGui.getTextArea1().append("\n\nWELCOME TO CHAT ROOM");
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                int i = 0;
                for (i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == null) {
                        (threads[i] = new clientThread(clientSocket, threads, serverGui)).start();
                        break;
                    }
                }
                if (i == maxClientsCount) {
                    DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
                    os.writeUTF("Server too busy. Try later.");
                    os.flush();
                    os.close();
                    clientSocket.close();
                    break;
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}