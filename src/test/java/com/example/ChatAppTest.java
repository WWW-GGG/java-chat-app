package com.example;

import java.io.IOException;
import java.net.Socket;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.client.ChatClient;
import com.example.common.Message;
import com.example.server.ChatServer;

public class ChatAppTest 
{

    private com.example.server.ChatServer chatServer;
    private com.example.client.ChatClient chatClient;

    @BeforeEach
    public void setUp() throws IOException, InterruptedException
{
        chatServer = new ChatServer(8808); // 88008 ten co serwer maka
        chatServer.start();
        Thread.sleep(1000);
        chatClient = new ChatClient("localhost", 8808);
    }

    @AfterEach
    public void tearDown() throws IOException 
{
        if (chatClient != null) 
{
            chatClient.close();
        }
        if (chatServer != null) 
{
            chatServer.stop();
        }
    }

    @Test
    public void testSendMessage() throws IOException 
{
        String sender = "User1";
        String content = "Hello, World!";
        // 3ci arg
        long timestamp = System.currentTimeMillis();
        Message message = new Message(sender, content, timestamp);
        
        chatClient.sendMessage(message);
        try 
        { // sprawdz czy processing dziala
            Thread.sleep(200); 
        } catch (InterruptedException e) 
        {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        
        Message receivedMessage = chatServer.getLastMessage();
        assertNotNull(receivedMessage, "No message received by server");
        assertEquals(sender, receivedMessage.getSender(), "Sender mismatch");
        assertEquals(content, receivedMessage.getContent(), "Content mismatch");
        assertEquals(timestamp, receivedMessage.getTimestamp(), "Timestamp mismatch");
        assertEquals(sender, receivedMessage.getSender());

        assertEquals(sender, receivedMessage.getSender());
        assertEquals(content, receivedMessage.getContent());
        
    }

    @Test
    public void testClientConnection() throws IOException 
{
        Socket socket = new Socket("localhost", 8808);
        assertTrue(socket.isConnected());
        socket.close();
    }

    @Test
    public void testServerRunning() 
{
        assertTrue(chatServer.isRunning());
    }
}