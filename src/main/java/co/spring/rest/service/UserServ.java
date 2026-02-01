package co.spring.rest.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.spring.rest.entity.bo.User;
import co.spring.rest.entity.dto.UserDto;
import co.spring.rest.entity.repository.IUserRepository;
import co.spring.rest.error.CreatedError;
import co.spring.rest.error.NotFoundError;

@Service
public class UserServ {

    @Autowired
    private IUserRepository iUserRepository;

    public List<UserDto> getListUsers(){
        return iUserRepository
            .findAll()
            .stream()
            .map((user) -> {
                return new UserDto(user.getId(), user.getName(), ""+user.getBirthDay(), ""+user.getSalary(), user.isActive());
            })
            .toList();
    }

    public UserDto findById(long id){
        return iUserRepository
            .findById(id)
            .map(user -> new UserDto(user.getId(), user.getName(), ""+user.getBirthDay(), ""+user.getSalary(), user.isActive()))
            .orElseThrow(() -> new NotFoundError("User not found","User cloud not find in the list.",null));
    }

    public List<UserDto> findBySalary(BigDecimal salary){

        List<UserDto> userList = iUserRepository
            .findBySalary(salary)
            .stream()
            .map((user) -> {
                return new UserDto(user.getId(), user.getName(), ""+user.getBirthDay(), ""+user.getSalary(), user.isActive());
            })
            .toList();

        if(userList.isEmpty() || userList == null)
            throw new NotFoundError("User not found","User cloud not find for salary in the list.",null);

        return userList;
    }

    public UserDto add(User user){

        User aUser = null;

        try {
            aUser = iUserRepository.save(user);
        } catch (Exception e) {
            throw new CreatedError("User not created", "User not created, error internal "+e.getMessage(), e);
        }
        return new UserDto(aUser.getId(), aUser.getName(), ""+aUser.getBirthDay(), ""+aUser.getSalary(), aUser.isActive());
    }

    public UserDto update(long id, User user){

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

        return new UserDto(aUser.getId(), aUser.getName(), ""+aUser.getBirthDay(), ""+aUser.getSalary(), aUser.isActive());
    }

    /**
    public User updateItem(int id, Map<String,Object> user){
        return userDAO.updateItem(id, user);
    }
    */
    public UserDto delete(long id){

        User aUser = iUserRepository.findById(id).orElseThrow(() -> new NotFoundError("User not found","User cloud not find in the list.",null));

        iUserRepository.delete(aUser);

        return new UserDto(aUser.getId(), aUser.getName(), ""+aUser.getBirthDay(), ""+aUser.getSalary(), aUser.isActive());
    }

    public List<UserDto> deleteBySalary(BigDecimal salary){

        List<User> users = iUserRepository.findBySalary(salary);

        for (User user : users) {
            iUserRepository.delete(user);
        }

        return users
            .stream()
            .map((user) -> {
                return new UserDto(user.getId(), user.getName(), ""+user.getBirthDay(), ""+user.getSalary(), user.isActive());
            })
            .toList();
    }

}
