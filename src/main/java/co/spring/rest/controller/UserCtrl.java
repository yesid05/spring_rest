package co.spring.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.spring.rest.entity.bo.User;
import co.spring.rest.service.UserServ;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



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

    @GetMapping("/user/{id}")
    public ResponseEntity<User> findById(@PathVariable int id) {

        User user = userServ.findById(id);

        if(user!=null)
            return ResponseEntity.ok(user);
        else
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);

    }
    

}
