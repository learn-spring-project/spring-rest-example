package com.example.demo.echo.domain;

/**
 * Created by Administrator on 2017/10/12.
 */
public class Message {
    private String from;
    private String to;
    private String content;

    public Message() {
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public String getContent() {
        return this.content;
    }

    public void setFrom(String value) {
        this.from = value;
    }

    public void setTo(String value) {
        this.to = value;
    }

    public void setContent(String value) {
        this.content = value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
