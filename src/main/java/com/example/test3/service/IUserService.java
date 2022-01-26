package com.example.test3.service;

import com.example.test3.model.UserModel;

import java.sql.SQLException;
import java.util.List;

public interface IUserService {
    UserModel addUser(UserModel userModel) throws Exception;
    UserModel updateUser(UserModel userModel) throws Exception;
    void deleteUser(long id) throws Exception;
    List<UserModel> findAll() throws Exception;
    UserModel findById(long id) throws Exception;
    List<UserModel> findByName(String name) throws Exception;
    List<UserModel> findByAddress(String address) throws Exception;
    List<UserModel> sortByName() throws Exception;
}
