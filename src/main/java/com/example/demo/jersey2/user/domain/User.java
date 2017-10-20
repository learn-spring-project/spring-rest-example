package com.example.demo.jersey2.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/13.
 */

public class User implements Serializable {
    @JsonProperty(value = "name")
    private String userName;
    private int    userId;

    public User(String userName, int userId) {
        this.userName = userName;
        this.userId = userId;
    }

    /**
     * 必须要有默认构造函数
     */
    public User() {
    }

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
