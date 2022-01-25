package com.example.test3.controller;

import com.example.test3.model.UserModel;
import com.example.test3.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public  List<UserModel> findAll() throws Exception{
        List<UserModel> userModels = new ArrayList<>();
        try {
             userModels = userService.findAll();

        }catch (Exception e){
            e.printStackTrace();
        }
        return userModels;
    }
    @PostMapping
    public UserModel addUser(@Valid @RequestBody UserModel user) throws Exception{
        UserModel userModel = new UserModel();
        try {
             userModel =  userService.addUser(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userModel;
    }

    @PutMapping
    public UserModel updateUser(@Valid @RequestBody UserModel user) throws Exception{
        UserModel userModel = new UserModel();
        try {
            userModel =  userService.updateUser(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userModel;
    }

    @DeleteMapping
    public void deleteBuildingById(@RequestParam long[] ids) throws Exception {
        try {
            for(long id:ids){
                userService.deleteUser(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @GetMapping("/{id}")
    public UserModel findById(@PathVariable long id) throws Exception{
        UserModel userModel = new UserModel();
        try {
            userModel =  userService.findById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userModel;
    }

    @GetMapping(value = "/user")
    public List<UserModel> findByAddress(@RequestParam(value="address") String address){
        List<UserModel> userModel = new ArrayList<>();
        try {
            userModel =  userService.findByAddress(address);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userModel;
    }

    @GetMapping(value = "/")
    public List<UserModel> findByName(@RequestParam(value="name") String name) throws Exception{
        List<UserModel> userModel = new ArrayList<>();
        try {
            userModel =  userService.findByName(name);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userModel;
    }

    @GetMapping(value = "/sort")
    public List<UserModel> sortByName(){
        if(userService.sortByName()!=null){
            return userService.sortByName();
        }
        return new ArrayList<>();
    }
}
