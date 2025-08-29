package co.spring.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.spring.rest.entity.bo.User;
import co.spring.rest.service.UserServ;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class UserCtrl {

    @Autowired
    private UserServ userServ;

    @GetMapping("/user")    
    public ResponseEntity<List<User>> getListUsers(){

        List<User> listUsers = userServ.getListUsers();
        return ResponseEntity.ok(listUsers);
    }
}
