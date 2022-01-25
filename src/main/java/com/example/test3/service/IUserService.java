package com.example.test3.service;

import com.example.test3.model.UserModel;

import java.util.List;

public interface IUserService {
    UserModel addUser(UserModel userModel);
    UserModel updateUser(UserModel userModel);
    void deleteUser(long id);
    List<UserModel> findAll();
    UserModel findById(long id);
    List<UserModel> findByName(String name);
    List<UserModel> findByAddress(String address);
    List<UserModel> sortByName();
}
