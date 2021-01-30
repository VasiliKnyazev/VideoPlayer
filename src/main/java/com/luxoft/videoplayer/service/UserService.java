package com.luxoft.videoplayer.service;

import com.luxoft.videoplayer.model.Role;
import com.luxoft.videoplayer.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(int id);

    User findByLogin(String login);

    User findByEmail(String email);

    void add(User user);

    void deleteById(int userId);

    void edit(User user);

    Role getRole(String role);

}
