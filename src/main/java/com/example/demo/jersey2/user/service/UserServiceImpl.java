package com.example.demo.jersey2.user.service;

import com.example.demo.jersey2.user.dao.UserDao;
import com.example.demo.jersey2.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */
@Component("userService")
public class UserServiceImpl implements  UserService {
    @Autowired
    @Qualifier(value = "userDao")
    UserDao userDao;

    @Override
    public String say() {
        return "Hello Jersey";
    }

    @Override
    public User addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public User findById(Integer id) {
        return userDao.findUserById(id);
    }

    @Override
    public User deleteById(Integer id) {
        return userDao.deleteUser(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    @Override
    public User updateUser(User user) {
        return userDao.updateUser(user);
    }


}
