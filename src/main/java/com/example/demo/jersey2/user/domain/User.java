package com.example.demo.jersey2.user.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/13.
 */
public class User implements Serializable {
    private String userName;
    private int    userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
