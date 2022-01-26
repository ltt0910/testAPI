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
        try {
            List<UserModel> userModels = new ArrayList<>();
            userModels = userService.findAll();
            ResponseCustom res = new ResponseCustom(1,HttpStatus.OK.value(),userModels,"Get data thành công");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (SQLException e) {
            ResponseCustom res = new ResponseCustom(0,HttpStatus.resolve(501).value(),null,e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            ResponseCustom res = new ResponseCustom(0,HttpStatus.INTERNAL_SERVER_ERROR.value(), null,e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserModel user) {
        try {
            Object userModel = new UserModel();
            userModel = userService.addUser(user);
            ResponseCustom res = new ResponseCustom(1,HttpStatus.OK.value(),userModel,"Thành công");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (UserInfoException uie) {
            ResponseCustom res = new ResponseCustom(0,uie.getCode(),null,uie.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (UserExsitsException ex) {
            ResponseCustom res = new ResponseCustom(0,ex.getCode(), null,ex.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseCustom res = new ResponseCustom(0,HttpStatus.INTERNAL_SERVER_ERROR.value(), null,e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserModel user) {
        try {
            Object userModel = new UserModel();
            userModel = userService.updateUser(user);
            ResponseCustom res = new ResponseCustom(1,HttpStatus.OK.value(),userModel,"Thành công");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (FieldErrorException fee) {
            ResponseCustom res = new ResponseCustom(0,fee.getCode(), null,fee.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (UserInfoException uie) {
            ResponseCustom res = new ResponseCustom(0,uie.getCode(),null,uie.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (FileNotFoundException fnfe) {
            ResponseCustom res = new ResponseCustom(0,HttpStatus.NOT_FOUND.value(), null,fnfe.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseCustom res = new ResponseCustom(0,HttpStatus.INTERNAL_SERVER_ERROR.value(), null,e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteBuildingById(@Valid @RequestParam long[] ids) {
        try {
            for (long id : ids) {
                userService.deleteUser(id);
            }
            ResponseCustom res = new ResponseCustom(1,HttpStatus.OK.value(),"Đã xóa","Xóa thành công");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (FileNotFoundException fnfe) {
            ResponseCustom res = new ResponseCustom(0,HttpStatus.NOT_FOUND.value(), null,fnfe.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseCustom res = new ResponseCustom(0,HttpStatus.INTERNAL_SERVER_ERROR.value(), null,e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable long id) {
        try {
            UserModel userModel = new UserModel();
            userModel = userService.findById(id);
            ResponseCustom res = new ResponseCustom(1,HttpStatus.OK.value(),userModel,"Thành công");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (SQLException sqle) {
            ResponseCustom res = new ResponseCustom(0,HttpStatus.resolve(501).value(),null,sqle.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseCustom res = new ResponseCustom(0,HttpStatus.INTERNAL_SERVER_ERROR.value(), null,e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/user")
    public ResponseEntity<Object> findByAddress(@RequestParam(value = "address") String address) {
        try {
            List<UserModel> userModel = new ArrayList<>();
            userModel = userService.findByAddress(address);
            ResponseCustom res = new ResponseCustom(1,HttpStatus.OK.value(),userModel,"Thành công");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (FieldErrorException fee) {
            ResponseCustom res = new ResponseCustom(0,fee.getCode(),null,fee.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (SQLException sqle) {
            ResponseCustom res = new ResponseCustom(0,HttpStatus.resolve(501).value(),null,sqle.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseCustom res = new ResponseCustom(0,HttpStatus.INTERNAL_SERVER_ERROR.value(), null,e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }


    @GetMapping(value = "/")
    public ResponseEntity<Object> findByName(@RequestParam(value = "name") String name) {
        try {
            List<UserModel> userModel = new ArrayList<>();
            userModel = userService.findByName(name);
            ResponseCustom res = new ResponseCustom(1,HttpStatus.OK.value(),userModel,"Thành công");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (FieldErrorException fee) {
            ResponseCustom res = new ResponseCustom(0,fee.getCode(),null,fee.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (SQLException sqle) {
            ResponseCustom res = new ResponseCustom(0,HttpStatus.resolve(501).value(),null,sqle.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseCustom res = new ResponseCustom(0,HttpStatus.INTERNAL_SERVER_ERROR.value(), null,e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<Object> sortByName() throws Exception {
        try {
            ResponseCustom res = new ResponseCustom(1,HttpStatus.OK.value(), userService.sortByName(),"Thành công");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (FileNotFoundException fnfe) {
            ResponseCustom res = new ResponseCustom(0,HttpStatus.NOT_FOUND.value(), null,fnfe.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseCustom res = new ResponseCustom(0,HttpStatus.INTERNAL_SERVER_ERROR.value(), null,e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

}
