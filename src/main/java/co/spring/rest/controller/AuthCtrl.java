package co.spring.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.spring.rest.entity.dto.LoginDto;
import co.spring.rest.service.AuthServ;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthCtrl {

    @Autowired
    private AuthServ authServ;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        
        if(!authServ.login(loginDto.getEmail(), loginDto.getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginDto);

        return ResponseEntity.ok(loginDto);

    }  
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String entity) {
        //TODO: process POST request
        
        return ResponseEntity.noContent().build();
    }
    

}
