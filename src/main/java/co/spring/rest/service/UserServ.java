package co.spring.rest.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import co.spring.rest.entity.bo.User;
import co.spring.rest.entity.dto.UserDto;
import co.spring.rest.entity.mapper.UserMapper;
import co.spring.rest.entity.repository.IUserRepository;
import co.spring.rest.error.CreatedError;
import co.spring.rest.error.NotFoundError;
import co.spring.rest.iservice.ICrudServ;
import co.spring.rest.iservice.IUserServ;

@Service
public class UserServ implements IUserServ{

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> getList(Integer pageNumber, Integer pageSize, String sort, String direction){
        
        Map<String, Object> mapUserDto = new HashMap<>();
        List<UserDto> listUserDto = null;

        if(sort == null)
            sort = "id";

        if(direction == null)
            direction = Direction.ASC.name();

        Direction d = (direction.equalsIgnoreCase(Direction.DESC.name())) ? Direction.DESC : Direction.ASC;

        if(pageNumber == null || pageSize == null){
            listUserDto = iUserRepository
                .findAll(Sort.by(d, sort))
                .stream()
                .map(userMapper::toUserDto)
                .toList();
            
            mapUserDto.put(ICrudServ.CONTENT, listUserDto);

        }else{

            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(d, sort));
            Page<User> userPage = iUserRepository.findAll(pageable);

            listUserDto = userPage
                .stream()
                .map(userMapper::toUserDto)
                .toList();

            mapUserDto.put(ICrudServ.CONTENT, listUserDto);
            mapUserDto.put(ICrudServ.NUMBER_PAGE, userPage.getNumber());
            mapUserDto.put(ICrudServ.SIZE_PAGE, userPage.getSize());
            mapUserDto.put(ICrudServ.NUMBER_OF_ELEMENTS, userPage.getNumberOfElements());
            mapUserDto.put(ICrudServ.TOTAL_PAGE, userPage.getTotalPages());
            mapUserDto.put(ICrudServ.TOTAL_ELEMENTS, userPage.getTotalElements());
            mapUserDto.put(ICrudServ.IS_FIRST, userPage.isFirst());
            mapUserDto.put(ICrudServ.IS_LAST, userPage.isLast());
            mapUserDto.put(ICrudServ.IS_EMPTY, userPage.isEmpty());

        }

        return mapUserDto;

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
