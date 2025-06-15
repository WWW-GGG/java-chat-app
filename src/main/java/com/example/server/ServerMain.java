package com.example.server;

public class ServerMain 
{
    public static void main(String[] args) 
    {
        ChatServer server = new ChatServer(8808); 
        server.start();
        System.out.println("Chat server started. Press Ctrl+C to stop.");
    }
}
//pomysl nad randomizacja portu (w okr zakresie ofc)