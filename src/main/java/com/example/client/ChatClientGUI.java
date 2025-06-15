package com.example.client;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.example.common.Message;

public class ChatClientGUI extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private ChatClient client;
    private String username;

    public ChatClientGUI(String host, int port, String username) throws IOException {
        super("Chat Client - " + username);
        this.username = username;
        client = new ChatClient(host, port);

        chatArea = new JTextArea(20, 40);
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField(30);
        sendButton = new JButton("Send");

        JPanel panel = new JPanel();
        panel.add(inputField);
        panel.add(sendButton);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Thread to receive messages
        new Thread(() -> {
            try {
                while (true) {
                    Message msg = client.receiveMessage();
                    if (msg != null) {
                        chatArea.append(msg.getSender() + ": " + msg.getContent() + "\n");
                    }
                }
            } catch (Exception e) {
                chatArea.append("Disconnected from server.\n");
            }
        }).start();
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            try {
                Message msg = new Message(username, text, System.currentTimeMillis());
                client.sendMessage(msg);
                inputField.setText("");
            } catch (IOException e) {
                chatArea.append("Failed to send message.\n");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String host = JOptionPane.showInputDialog("Server IP:", "localhost");
        String portStr = JOptionPane.showInputDialog("Port:", "8808");
        String username = JOptionPane.showInputDialog("Your name:");
        int port = Integer.parseInt(portStr);
        new ChatClientGUI(host, port, username);
    }
}