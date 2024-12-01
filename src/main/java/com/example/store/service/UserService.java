package com.example.store.service;

import com.example.store.dto.UserDto;
import com.example.store.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
