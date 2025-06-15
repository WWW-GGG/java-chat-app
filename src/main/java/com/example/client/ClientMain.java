package com.example.client;

import java.io.IOException;
import java.util.Scanner;

import com.example.common.Message;

public class ClientMain 
{
    public static void main(String[] args) throws IOException 
    {
        ChatClient client = new ChatClient("localhost", 8808);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        new Thread(() -> 
        {
            try 
            {
                while (true) 
                {
                    Message msg = client.receiveMessage();
                    if (msg != null)
                    {
                        System.out.println(msg.getSender() + ": " + msg.getContent());
                    }
                }
            } 
            catch (Exception e) 
            {
                System.out.println("Disconnected from server.");
            }
        }).start();

        while (true) 
        {
            String text = scanner.nextLine();
            Message msg = new Message(name, text, System.currentTimeMillis());
            client.sendMessage(msg);
        }
    }
}
