package com.example.demo.echo.domain;

/**
 * Created by Administrator on 2017/10/12.
 */
public class Echo {
    private final long id;
    private final String content;

    public Echo(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return this.id;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        return "Echo{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
