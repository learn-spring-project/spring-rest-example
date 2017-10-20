package com.example.demo.jersey2.user.service;

import com.example.demo.jersey2.user.domain.User;

import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */
public interface UserService {
    public String say();

    public User addUser(User user);

    public User findById(Integer id);

    public User deleteById(Integer id);

    public List<User> findAllUsers();

    public User  updateUser(User user);
}
