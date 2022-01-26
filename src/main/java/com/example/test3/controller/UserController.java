package com.example.test3.controller;

import com.example.test3.exception.FieldErrorException;
import com.example.test3.exception.UserExsitsException;
import com.example.test3.exception.UserInfoException;
import com.example.test3.global.ErrorCode;
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
        ResponseCustom res = new ResponseCustom();
        try {
            List<UserModel> userModels = new ArrayList<>();
            userModels = userService.findAll();
            res.setCode(1);
            res.setStatus(HttpStatus.OK.value());
            res.setMessage("Get data thành công");
            res.setData(userModels);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (SQLException e) {
            res.setMessage("Lỗi truy vấn");
            res.setStatus(HttpStatus.resolve(501).value());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            res.setMessage("Lỗi hệ thống");
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserModel user) {
        ResponseCustom res = new ResponseCustom();
        try {
            Object userModel = new UserModel();
            userModel = userService.addUser(user);
            res.setData(user);
            res.setCode(1);
            res.setMessage("Thành công");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (UserInfoException uie) {
            res.setStatus(uie.getCode());
            res.setMessage(uie.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (UserExsitsException ex) {
            res.setStatus(ex.getCode());
            res.setMessage(ex.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            res.setMessage("Lỗi hệ thống");
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserModel user) {
        ResponseCustom res = new ResponseCustom();
        try {
            Object userModel = new UserModel();
            userModel = userService.updateUser(user);
            res.setCode(1);
            res.setStatus(HttpStatus.OK.value());
            res.setData(userModel);
            res.setMessage("Update thành công");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (FieldErrorException fee) {
            res.setStatus(ErrorCode.CHARACTER_ERROR);
            res.setMessage(fee.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (UserInfoException uie) {
            res.setStatus(ErrorCode.INVALID_USER_INFO);
            res.setMessage(uie.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (FileNotFoundException fnfe) {
            res.setMessage(fnfe.getMessage());
            res.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            res.setMessage("Lỗi hệ thống");
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteBuildingById(@Valid @RequestParam long[] ids) {
        ResponseCustom res = new ResponseCustom();
        try {
            for (long id : ids) {
                userService.deleteUser(id);
            }
            res.setStatus(HttpStatus.OK.value());
            res.setCode(1);
            res.setMessage("Xóa thành công");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (FileNotFoundException fnfe) {
            res.setMessage(fnfe.getMessage());
            res.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            res.setMessage("Lỗi hệ thống");
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable long id) {
        ResponseCustom res = new ResponseCustom();
        try {
            UserModel userModel = new UserModel();
            userModel = userService.findById(id);
            res.setMessage("Get data thành công");
            res.setCode(1);
            res.setData(userModel);
            res.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(res, HttpStatus.OK);

        } catch (SQLException sqle) {
            res.setMessage("Lỗi truy vấn");
            res.setStatus(HttpStatus.resolve(501).value());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            res.setMessage("Lỗi hệ thống");
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/user")
    public ResponseEntity<Object> findByAddress(@RequestParam(value = "address") String address) {
        ResponseCustom res = new ResponseCustom();
        try {
            List<UserModel> userModel = new ArrayList<>();
            userModel = userService.findByAddress(address);
            res.setData(userModel);
            res.setStatus(HttpStatus.OK.value());
            res.setCode(1);
            res.setMessage("Biding success");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (FieldErrorException fee) {
            res.setStatus(ErrorCode.CHARACTER_ERROR);
            res.setMessage(fee.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (SQLException sqle) {
            res.setMessage("Lỗi truy vấn");
            res.setStatus(HttpStatus.resolve(501).value());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            res.setMessage("Lỗi hệ thống");
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }


    @GetMapping(value = "/")
    public ResponseEntity<Object> findByName(@RequestParam(value = "name") String name) {
        ResponseCustom res = new ResponseCustom();
        try {
            List<UserModel> userModel = new ArrayList<>();
            userModel = userService.findByName(name);
            res.setData(userModel);
            res.setStatus(HttpStatus.OK.value());
            res.setCode(1);
            res.setMessage("Biding success");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (FieldErrorException fee) {
            res.setStatus(ErrorCode.CHARACTER_ERROR);
            res.setMessage(fee.getMessage());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (SQLException sqle) {
            res.setMessage("Lỗi truy vấn");
            res.setStatus(HttpStatus.resolve(501).value());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            res.setMessage("Lỗi hệ thống");
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<Object> sortByName() throws Exception {
        ResponseCustom res = new ResponseCustom();
        try {
            res.setMessage("Sắp xếp thành công");
            res.setStatus(HttpStatus.OK.value());
            res.setCode(1);
            res.setData(userService.sortByName());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (FileNotFoundException fnfe) {
            res.setMessage(fnfe.getMessage());
            res.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            res.setMessage("Lỗi hệ thống");
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

    }

}
