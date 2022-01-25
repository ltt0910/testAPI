package com.example.test3.repository;

import com.example.test3.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository {
    UserEntity insertUser(UserEntity user);
    UserEntity updateUser(UserEntity user);
    void deleteUser(long id);
    boolean exsist(long id);
    List<UserEntity> findAll();
    UserEntity findById(long id);
    List<UserEntity> findByName(String name);
    List<UserEntity> findByAddress(String address);
}
