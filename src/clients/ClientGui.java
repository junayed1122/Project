package clients;

import server.GroupChatServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ClientGui {

    private String clinetCounterTextFile = "    /home/new_user/Downloads/ChatApp/count.txt";
    private JTextArea textArea1;
    private JTextField textField1;
    private JButton sendButton;
    private JPanel panel;
    private JLabel label;

    public JTextArea getTextArea1() {
        return textArea1;
    }

    public ClientGui(DataOutputStream os) {
        String count = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(clinetCounterTextFile));
            FileWriter fileWriter = new FileWriter(clinetCounterTextFile);
            count = bufferedReader .readLine();
            int c = Integer.parseInt(count);
            c++;
            fileWriter.write(String.valueOf(c));
            fileWriter.close();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame jFrame=new JFrame("Client_"+count);
        jFrame.setSize(500,500);
        jFrame.setContentPane(panel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s=textField1.getText();
                textField1.setText("");
                if(!s.equals(""))
                {
                    try {
                        os.writeUTF(s.trim());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
}
