package co.spring.rest.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.spring.rest.entity.bo.User;
import co.spring.rest.service.UserServ;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;







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

    @GetMapping("/user/search")
    public ResponseEntity<List<User>> findBySalary(@RequestParam(required = true) String salary) {
        
        List<User> listUser = userServ.findBySalary(BigDecimal.valueOf(Double.parseDouble(salary)));

        if(listUser.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        return ResponseEntity.ok(listUser);

    }

    @PostMapping("/user")
    public ResponseEntity<User> add(@RequestBody User user) {
        
        User u = userServ.add(user);

        if(u==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> update(@PathVariable int id, @RequestBody User user) {
        
        User u = userServ.update(id, user);
        return ResponseEntity.ok(u);
        
    }   
    
    

}
