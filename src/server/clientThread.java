package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class clientThread extends Thread  {

    private DataInputStream is = null;
    private DataOutputStream os = null;
    private Socket clientSocket = null;
    private ServerGui serverGui;
    private final clientThread[] threads;
    private int maxClientsCount;

    public clientThread(Socket clientSocket, clientThread[] threads, ServerGui serverGui) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        this.serverGui=serverGui;
        this.maxClientsCount = threads.length;
    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        clientThread[] threads = this.threads;
        try {
            is = new DataInputStream(clientSocket.getInputStream());
            os = new DataOutputStream(clientSocket.getOutputStream());
            os.writeUTF("Enter your name.");
            String name = is.readUTF().trim();
            os.writeUTF("Hello " + name+". Welcome to our chat room.\nTo leave enter /quit in a new line");
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null && threads[i] != this) {
                    threads[i].os.writeUTF("A new user named"+name+" has entered the chat room.");
                }
            }

            while (true) {
                String line = is.readUTF();
                serverGui.getTextArea1().append("\n"+name +": "+ line);
                if (line.equalsIgnoreCase("bye")) {
                    break;
                }
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null) {
                        threads[i].os.writeUTF(name+" :" + line);
                    }
                }
            }
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null && threads[i] != this) {
                    threads[i].os.writeUTF("The user "+name+" is leaving the chat room.");
                }
            }
            os.flush();
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] == this) {
                    threads[i] = null;
                }
            }
            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {

        }
    }
}
