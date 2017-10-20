package com.example.demo.jersey2.user.dao;

import com.example.demo.jersey2.user.domain.User;
import com.example.demo.jersey2.user.domain.UserXML;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/20.
 */
@Repository("userDao")
public class UserDao {
    private static Map<Integer, User> userMap = new HashMap<Integer, User>();

    public User addUser(User user) {
        userMap.put(user.getUserId(), user);
        return user;
    }

    public User deleteUser(Integer id) {
        User user = userMap.get(id);
        userMap.remove(id);
        return user;
    }

    public User updateUser(User user) {
        userMap.put(user.getUserId(), user);
        return user;
    }


    public User findUserById(Integer id) {
        return userMap.get(id);
    }

    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        users.addAll(userMap.values());
        return users;
    }


}
