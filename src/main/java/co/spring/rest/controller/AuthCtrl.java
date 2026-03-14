package co.spring.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import co.spring.rest.entity.dto.LoginDto;
import co.spring.rest.entity.dto.UserDto;
import co.spring.rest.service.AuthServ;
import jakarta.validation.Valid;

import java.net.URI;

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

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto, UriComponentsBuilder uriComponentsBuilder) {
        
        UserDto aUser = authServ.registerUser(userDto);

        if(aUser==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        URI uri = uriComponentsBuilder.path("/api/auth/login").build().toUri();
        
        return ResponseEntity.created(uri).body(aUser);

    }
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        
        String principal = authServ.login(loginDto.getEmail(), loginDto.getPassword());

        return ResponseEntity.ok(principal);

    }  
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String entity) {
        //TODO: process POST request
        
        return ResponseEntity.noContent().build();
    }
    

}
