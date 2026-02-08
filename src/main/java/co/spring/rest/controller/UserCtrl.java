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
import co.spring.rest.entity.dto.UserDto;
import co.spring.rest.service.UserServ;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/user")
public class UserCtrl {

    @Autowired
    private UserServ userServ;

    @GetMapping()    
    public ResponseEntity<List<UserDto>> getListUsers(){

        List<UserDto> listUsers = userServ.getListUsers();
        return ResponseEntity.ok(listUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable int id) {

        UserDto user = userServ.findById(id);

        if(user!=null)
            return ResponseEntity.ok(user);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> findBySalary(@RequestParam(required = true) String salary) {
        
        List<UserDto> listUser = userServ.findBySalary(BigDecimal.valueOf(Double.parseDouble(salary)));

        if(listUser.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        return ResponseEntity.ok(listUser);

    }

    @PostMapping()
    public ResponseEntity<UserDto> add(@RequestBody User user) {
        
        UserDto aUser = userServ.add(user);

        if(aUser==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        return ResponseEntity.ok(aUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable int id, @RequestBody User user) {
        
        UserDto u = userServ.update(id, user);
        return ResponseEntity.ok(u);
        
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateItem(@PathVariable long id,@RequestBody Map<String,Object> user){

        //User u = userServ.updateItem(id, user);
        return ResponseEntity.badRequest().build();

    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable long id){
        UserDto u = userServ.delete(id);

        if(u==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(u);
    }

    @DeleteMapping("")
    public ResponseEntity<List<UserDto>> deleteBySalary(@RequestParam(required = true) String salary){
        
        List<UserDto> listUser = userServ.deleteBySalary(BigDecimal.valueOf(Double.parseDouble(salary)));

        if(listUser.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        return ResponseEntity.ok(listUser);

    }
    
    

}
