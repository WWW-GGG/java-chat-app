package com.example.common;

import java.io.Serializable;


public class Message implements Serializable 
{
    private static final long serialVersionUID = 1L;

    private String sender;
    private String content;
    private long timestamp;

    public Message() 
    {
    }
    public Message(String sender, String content, long timestamp) 
    {   
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getSender() 
    {
        return sender;
    }

    public void setSender(String sender) 
    {
        this.sender = sender;
    }

        public String getContent() 
        {
            return content;
        }
    
        public void setContent(String content) 
        {
            this.content = content;
        }
    
        public long getTimestamp() 
        {
            return timestamp;
        }
    
        public void setTimestamp(long timestamp) 
        {
            this.timestamp = timestamp;
        }
}

   