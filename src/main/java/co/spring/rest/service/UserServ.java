package co.spring.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.spring.rest.entity.bo.User;
import co.spring.rest.entity.repository.UserDAO;

@Service
public class UserServ {

    @Autowired
    private UserDAO userDAO;

    public List<User> getListUsers(){
        return userDAO.getListUsers();
    }

}
