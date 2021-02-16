package clients;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class GroupChatClient implements Runnable {

    private static int portNumber = 5555;
    private static String host = "localhost";
    private static Socket clientSocket = null;
    private static DataOutputStream os = null;
    private static DataInputStream is = null;
    private static BufferedReader inputLine = null;
    private static boolean closed = false;


    public static void main(String[] args) {
        try {
            clientSocket = new Socket(host, portNumber);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new DataOutputStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host "+host);
        }

        if (clientSocket != null && os != null && is != null) {
            try {
                new Thread(new GroupChatClient()).start();
                while (!closed) {
                    os.writeUTF(inputLine.readLine().trim());
                }
                os.close();
                is.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }
    public void run() {
        ClientGui clientGui=new ClientGui(os);
        String responseLine;
        try {
            while ((responseLine = is.readUTF()) != null) {
                clientGui.getTextArea1().append("\n"+responseLine);
                if (responseLine.equalsIgnoreCase("bye"))
                    break;
            }
            closed = true;
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
}