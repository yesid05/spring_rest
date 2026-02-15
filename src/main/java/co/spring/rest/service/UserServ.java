package co.spring.rest.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.spring.rest.entity.bo.User;
import co.spring.rest.entity.dto.UserDto;
import co.spring.rest.entity.mapper.UserMapper;
import co.spring.rest.entity.repository.IUserRepository;
import co.spring.rest.error.CreatedError;
import co.spring.rest.error.NotFoundError;
import co.spring.rest.iservice.IUserServ;

@Service
public class UserServ implements IUserServ{

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDto> getList(){
        return iUserRepository
            .findAll()
            .stream()
            .map(userMapper::toUserDto)
            .toList();
    }

    @Override
    public UserDto findById(long id){

        User aUser = iUserRepository.findById(id).orElseThrow(() -> new NotFoundError("User not found","User cloud not find in the list.",null));

        return userMapper.toUserDto(aUser);
    }

    @Override
    public List<UserDto> findBySalary(BigDecimal salary){

        List<UserDto> userList = iUserRepository
            .findBySalary(salary)
            .stream()
            .map(userMapper::toUserDto)
            .toList();

        if(userList.isEmpty() || userList == null)
            throw new NotFoundError("User not found","User cloud not find for salary in the list.",null);

        return userList;
    }

    @Override
    public UserDto add(UserDto userDto){

        User aUser = null;

        try {
            aUser = iUserRepository.save(userMapper.toUser(userDto));
        } catch (Exception e) {
            throw new CreatedError("User not created", "User not created, error internal "+e.getMessage(), e);
        }
        return userMapper.toUserDto(aUser);
    }

    @Override
    public UserDto update(long id, UserDto userDto){

        User aUser = iUserRepository.findById(id).orElseThrow(() -> new NotFoundError("User not found","User cloud not find in the list.",null));
        
        User u = userMapper.toUser(userDto);

        aUser.setName(u.getName());
        aUser.setLastName(u.getLastName());
        aUser.setBirthDay(u.getBirthDay());
        aUser.setSalary(u.getSalary());
        aUser.setActive(u.isActive());

        try {
            iUserRepository.save(aUser);
        } catch (Exception e) {
            throw new CreatedError("User not update", "User not update, error internal "+e.getMessage(), e);
        }

        return userMapper.toUserDto(aUser);
    }

    /**
    public User updateItem(int id, Map<String,Object> user){
        return userDAO.updateItem(id, user);
    }
    */

    @Override
    public UserDto delete(long id){

        User aUser = iUserRepository.findById(id).orElseThrow(() -> new NotFoundError("User not found","User cloud not find in the list.",null));

        iUserRepository.delete(aUser);

        return userMapper.toUserDto(aUser);
    }

    @Override
    public List<UserDto> deleteBySalary(BigDecimal salary){

        List<User> users = iUserRepository.findBySalary(salary);

        for (User user : users) {
            iUserRepository.delete(user);
        }

        return users
            .stream()
            .map(userMapper::toUserDto)
            .toList();
    }

}
