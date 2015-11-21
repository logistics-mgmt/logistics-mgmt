package com.jdbc.demo;

import com.jdbc.demo.domain.Client;

import java.util.List;

/**
 * Created by Mateusz on 02-Nov-15.
 */
public interface ClientDAO {
    List<Client> getAll();
    Client add(Client client);
    Client get(int id);
    Client get(String name);
    void update(Client client);
    void delete(int id);
}
