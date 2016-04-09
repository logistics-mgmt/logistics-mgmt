package com.jdbc.demo;

import java.util.List;

import com.jdbc.demo.domain.security.User;

public interface UserDAO {
 
    User getById(int id);
     
    User getByLogin(String login);

    void add(User user);
    
    void deleteByLogin(String login);
     
    List<User> getAll();
     
}