package co.spring.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import co.spring.rest.config.JwtConfig;
import co.spring.rest.entity.bo.User;
import co.spring.rest.entity.dto.JwtDto;
import co.spring.rest.entity.dto.LoginDto;
import co.spring.rest.entity.dto.UserDto;
import co.spring.rest.service.AuthServ;
import co.spring.rest.service.JwtServ;
import co.spring.rest.service.UserServ;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@PreAuthorize("permitAll")
@RestController
@RequestMapping("/api/auth")
public class AuthCtrl {

    @Autowired
    private AuthServ authServ;

    @Autowired
    private UserServ userServ;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtServ jwtServ;

    @PreAuthorize("isAuthenticated")
    @GetMapping()
    public ResponseEntity<UserDto> profile() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = ((User)authentication.getPrincipal()).getEmail();

        UserDto aUserDto = userServ.findByEmail(email);

        return ResponseEntity.ok(aUserDto);

    }
    

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto, UriComponentsBuilder uriComponentsBuilder) {
        
        UserDto aUser = authServ.registerUser(userDto);

        if(aUser==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        URI uri = uriComponentsBuilder.path("/api/auth/login").build().toUri();
        
        return ResponseEntity.created(uri).body(aUser);

    }
    

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {
        
        UserDto userDto = authServ.login(loginDto.getEmail(), loginDto.getPassword());

        JwtDto jwtDto = jwtServ.generateAccessToken(userDto);

        String refreshToken = jwtServ.generateRefreshToken(userDto);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/auth/refresh-token");
        cookie.setMaxAge(jwtConfig.getExpirationRefreshTokenMinute());
        cookie.setSecure(true);

        response.addCookie(cookie);

        return ResponseEntity.ok(jwtDto);

    }  

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtDto> refreshToken(@CookieValue(value = "refreshToken") String cookie) {
        
        if(!jwtServ.validateToken(cookie))
            throw new BadCredentialsException("Invalid credentials");

        String email = jwtServ.getClaims(cookie).getSubject();

        UserDto userDto = userServ.findByEmail(email);

        JwtDto jwtDto = jwtServ.generateAccessToken(userDto);

        return ResponseEntity.ok(jwtDto);
    }
    

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String header) {
        
        String aToken = header.replace("Bearer ", "");
        
        boolean isValidate = jwtServ.validateToken(aToken);

        return ResponseEntity.ok(isValidate);
    }
    
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String entity) {
        //TODO: process POST request
        
        return ResponseEntity.noContent().build();
    }
    

}
