package com.example.test3.repository;

import com.example.test3.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface IUserRepository {
    UserEntity insertUser(UserEntity user) throws SQLException;
    UserEntity updateUser(UserEntity user) throws SQLException;
    void deleteUser(long id) throws SQLException;
    boolean exsist(long id);
    List<UserEntity> findAll();
    UserEntity findById(long id);
    List<UserEntity> findByName(String name) ;
    List<UserEntity> findByAddress(String address);
}
