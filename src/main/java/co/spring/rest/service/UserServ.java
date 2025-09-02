package co.spring.rest.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    public User findById(int id){
        return userDAO.findById(id);
    }

    public List<User> findBySalary(BigDecimal salary){
        return userDAO.findBySalary(salary);
    }

    public User add(User user){
        return userDAO.add(user);
    }

    public User update(int id, User user){
        return userDAO.update(id, user);
    }

    public User updateItem(int id, Map<String,Object> user){
        return userDAO.updateItem(id, user);
    }

    public User delete(int id){
        return userDAO.delete(id);
    }

}
