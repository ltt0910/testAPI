package com.example.test3.converter;

import com.example.test3.entity.UserEntity;
import com.example.test3.model.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    @Autowired
    private ModelMapper modelMapper;

    public UserEntity convertToEntity(UserModel model) {
        UserEntity entity = modelMapper.map(model, UserEntity.class);
        return entity;
    }

    public UserModel convertToModel(UserEntity userEntity) {
        UserModel model = modelMapper.map(userEntity, UserModel.class);
        return model;
    }
}
