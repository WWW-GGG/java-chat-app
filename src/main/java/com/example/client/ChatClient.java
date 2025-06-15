package com.example.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.example.common.Message;

public class ChatClient 
{
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ChatClient(String host, int port) throws IOException 
    {
        this.socket = new Socket(host, port);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public void sendMessage(Message message) throws IOException 
    {
        out.writeObject(message);
        out.flush();
    }

    public Message receiveMessage() throws IOException, ClassNotFoundException 
    {
        Object obj = in.readObject();
        if (obj instanceof Message) 
        {
            return (Message) obj;
        }
        return null;
    }

    public void close() throws IOException 
    {
        if (in != null) in.close();
        if (out != null) out.close();
        if (socket != null && !socket.isClosed()) socket.close();
    }

    public static void main(String[] args) 
    {
        if (args.length != 2) 
        {
            System.out.println("Usage: java ChatClient <server address> <server port>");
            return;
        }

        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);

        try 
        {
            ChatClient client = new ChatClient(serverAddress, serverPort);

            String sender = "clientUser";
            String content = "Server on";
            long timestamp = System.currentTimeMillis();
            Message messageToSend = new Message(sender, content, timestamp);
            client.sendMessage(messageToSend);
            System.out.println("Msg sent to server: " + messageToSend.getContent());

            // Example of receiving a message
            Message receivedMessage = client.receiveMessage();
            if (receivedMessage != null) 
            {
                System.out.println("Msg rec from server: " + receivedMessage.getContent());
            }

            client.close();
        } catch (IOException | ClassNotFoundException e) 
            {
            System.err.println("Error: " + e.getMessage());
            }
    }
}