package server;

import javax.swing.*;

public class ServerGui {

    private JTextArea textArea1;
    private JPanel panel;

    public ServerGui() {
        JFrame jFrame=new JFrame("SERVER");
        jFrame.setSize(500,500);
        jFrame.setContentPane(panel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textArea1.setText("                                            Server Started");
        jFrame.setVisible(true);
    }

    public JTextArea getTextArea1() {
        return textArea1;
    }

}

