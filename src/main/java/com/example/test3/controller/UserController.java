package com.example.test3.controller;

import com.example.test3.exception.FieldErrorException;
import com.example.test3.exception.UserExsitsException;
import com.example.test3.exception.UserInfoException;
import com.example.test3.model.UserModel;
import com.example.test3.response.ResponseCustom;
import com.example.test3.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        ResponseCustom<List<UserModel>> res;
        try {
            res = userService.findAll();
        } catch (Exception e) {
            res = new ResponseCustom(0, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, e.getMessage());
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserModel user) {
        ResponseCustom res;
        try {
            res = userService.addUser(user);
        } catch (UserInfoException uie) {
            res = new ResponseCustom(0, uie.getCode(), null, uie.getMessage());
        } catch (UserExsitsException ex) {
            res = new ResponseCustom(0, ex.getCode(), null, ex.getMessage());
        } catch (Exception e) {
            res = new ResponseCustom(0, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, e.getMessage());
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserModel user) {
        ResponseCustom res ;
        try {
            res = userService.updateUser(user);
        } catch (FieldErrorException fee) {
            res = new ResponseCustom(0, fee.getCode(), null, fee.getMessage());
        } catch (UserInfoException uie) {
            res = new ResponseCustom(0, uie.getCode(), null, uie.getMessage());
        } catch (FileNotFoundException fnfe) {
            res = new ResponseCustom(0, HttpStatus.NOT_FOUND.value(), null, fnfe.getMessage());
        } catch (Exception e) {
            res = new ResponseCustom(0, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, e.getMessage());
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteBuildingById(@Valid @RequestParam long[] ids) {
        ResponseCustom res;
        try {
            res = userService.deleteUser(ids);
        } catch (FileNotFoundException fnfe) {
            res = new ResponseCustom(0, HttpStatus.NOT_FOUND.value(), null, fnfe.getMessage());
        } catch (Exception e) {
            res = new ResponseCustom(0, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, e.getMessage());
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable long id) {
        ResponseCustom res;
        try {
            res = userService.findById(id);
        } catch (SQLException sqle) {
            res = new ResponseCustom(0, HttpStatus.resolve(501).value(), null, sqle.getMessage());
        } catch (Exception e) {
            res = new ResponseCustom(0, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, e.getMessage());
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<Object> findByAddress(@RequestParam(value = "address") String address) {
        ResponseCustom res;
        try {
            List<UserModel> userModel = new ArrayList<>();
            res = new ResponseCustom(1, HttpStatus.OK.value(), userService.findByAddress(address), "Thành công");
        } catch (FieldErrorException fee) {
            res = new ResponseCustom(0, fee.getCode(), null, fee.getMessage());
        } catch (SQLException sqle) {
            res = new ResponseCustom(0, HttpStatus.resolve(501).value(), null, sqle.getMessage());
        } catch (Exception e) {
            res = new ResponseCustom(0, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, e.getMessage());
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @GetMapping(value = "/")
    public ResponseEntity<Object> findByName(@RequestParam(value = "name") String name) {
        ResponseCustom res;
        try {
            res = new ResponseCustom(1, HttpStatus.OK.value(), userService.findByName(name), "Thành công");
        } catch (FieldErrorException fee) {
            res = new ResponseCustom(0, fee.getCode(), null, fee.getMessage());
        } catch (SQLException sqle) {
            res = new ResponseCustom(0, HttpStatus.resolve(501).value(), null, sqle.getMessage());
        } catch (Exception e) {
            res = new ResponseCustom(0, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, e.getMessage());
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<Object> sortByName(){
        ResponseCustom res;
        try {
            res = new ResponseCustom(1, HttpStatus.OK.value(), userService.sortByName(), "Thành công");
        } catch (FileNotFoundException fnfe) {
            res = new ResponseCustom(0, HttpStatus.NOT_FOUND.value(), null, fnfe.getMessage());
        } catch (Exception e) {
            res = new ResponseCustom(0, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, e.getMessage());
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<Object> inser5MillionRecord(){
        ResponseCustom res;
        try {
            userService.add5MillionRecord();
            res = new ResponseCustom(1, HttpStatus.OK.value(), "Ok", "Thêm thành công");
        } catch (SQLException sqle) {
            res = new ResponseCustom(0, HttpStatus.resolve(501).value(), null, sqle.getMessage());
        } catch (Exception e) {
            res = new ResponseCustom(0, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, e.getMessage());
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
