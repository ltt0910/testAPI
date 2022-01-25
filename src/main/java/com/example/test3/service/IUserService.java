package com.example.test3.service;

import com.example.test3.model.UserModel;

import java.sql.SQLException;
import java.util.List;

public interface IUserService {
    UserModel addUser(UserModel userModel) throws SQLException;
    UserModel updateUser(UserModel userModel) throws SQLException;
    void deleteUser(long id) throws SQLException;
    List<UserModel> findAll() throws SQLException;
    UserModel findById(long id) throws SQLException;
    List<UserModel> findByName(String name) throws SQLException;
    List<UserModel> findByAddress(String address) throws SQLException;
    List<UserModel> sortByName() throws SQLException;
}
