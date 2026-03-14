package co.spring.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.spring.rest.entity.bo.User;
import co.spring.rest.entity.repository.IUserRepository;
import co.spring.rest.error.NotFoundError;
import co.spring.rest.iservice.IAuthServ;

@Service
public class AuthServ implements IAuthServ{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public boolean login(String email, String password) {

        User aUser = iUserRepository.findByEmail(email).orElseThrow(() -> new NotFoundError("User not found","User cloud not find in the list.",null));

        return passwordEncoder.matches(password, aUser.getPassword());

    }

}
