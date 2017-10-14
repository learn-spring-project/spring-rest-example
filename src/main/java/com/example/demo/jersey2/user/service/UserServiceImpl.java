package com.example.demo.jersey2.user.service;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/10/13.
 */
@Component("userService")
public class UserServiceImpl implements  UserService {
    @Override
    public String say() {
        return "Hello Jersey";
    }
}
