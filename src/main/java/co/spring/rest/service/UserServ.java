package co.spring.rest.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.spring.rest.entity.bo.User;
import co.spring.rest.entity.repository.IUserRepository;
import co.spring.rest.error.CreatedError;
import co.spring.rest.error.NotFoundError;

@Service
public class UserServ {

    @Autowired
    private IUserRepository iUserRepository;

    public List<User> getListUsers(){
        return iUserRepository.findAll();
    }

    public User findById(long id){
        return iUserRepository.findById(id).orElseThrow(() -> new NotFoundError("User not found","User cloud not find in the list.",null));
    }

    public List<User> findBySalary(BigDecimal salary){

        List<User> userList = iUserRepository.findBySalary(salary);

        if(userList.isEmpty() || userList == null)
            throw new NotFoundError("User not found","User cloud not find for salary in the list.",null);

        return userList;
    }

    public User add(User user){

        User aUser = null;

        try {
            aUser = iUserRepository.save(user);
        } catch (Exception e) {
            throw new CreatedError("User not created", "User not created, error internal "+e.getMessage(), e);
        }
        return aUser;
    }

    public User update(long id, User user){

        User aUser = iUserRepository.findById(id).orElseThrow(() -> new NotFoundError("User not found","User cloud not find in the list.",null));

        aUser.setName(user.getName());
        aUser.setLastName(user.getLastName());
        aUser.setBirthDay(user.getBirthDay());
        aUser.setSalary(user.getSalary());
        aUser.setActive(user.isActive());

        try {
            iUserRepository.save(aUser);
        } catch (Exception e) {
            throw new CreatedError("User not update", "User not update, error internal "+e.getMessage(), e);
        }

        return aUser;
    }

    /**
    public User updateItem(int id, Map<String,Object> user){
        return userDAO.updateItem(id, user);
    }
    */
    public User delete(long id){

        User aUser = iUserRepository.findById(id).orElseThrow(() -> new NotFoundError("User not found","User cloud not find in the list.",null));

        iUserRepository.delete(aUser);

        return aUser;
    }

    public List<User> deleteBySalary(BigDecimal salary){

        List<User> users = iUserRepository.findBySalary(salary);

        for (User user : users) {
            iUserRepository.delete(user);
        }

        return users;
    }

}
