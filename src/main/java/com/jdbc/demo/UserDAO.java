package com.jdbc.demo;

import java.util.List;

import com.jdbc.demo.domain.psql.Client;
import com.jdbc.demo.domain.security.*;

public interface UserDAO {
 
    User getById(int id);
     
    User getBySSO(String sso);

    User add(User user);
    
    void deleteBySSO(String sso);
     
    List<User> getAllUsers();
     
}