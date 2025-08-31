package co.spring.rest.entity.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import co.spring.rest.entity.bo.User;

@Repository
public class UserDAO {

    private static List<User> USERS = new ArrayList<User>( List.of(
        new User(1, "Tony", "Stark", LocalDate.now(), BigDecimal.valueOf(200.2), true),
        new User(2, "Steve", "Rogers", LocalDate.now(), BigDecimal.valueOf(100.2), true),
        new User(3, "Natasha", "Romanoff", LocalDate.now(), BigDecimal.valueOf(100.2), true),
        new User(4, "Wanda", "Maximoff", LocalDate.now(), BigDecimal.valueOf(200.2), true)
    ));

    public List<User> getListUsers(){
        return USERS;
    }

    public User findById(int id){
        return USERS
            .stream()
            .filter(
                    (user) -> user.id()==id
                )
            .findFirst()
            .orElse(null);
    }

    public List<User> findBySalary(BigDecimal salary){
        return USERS
            .stream()
            .filter(
                (user) -> user.salary().equals(salary)
            )
            .toList();
            
    }

    public User add(User user){

        if(USERS.add(user))
            return user;
        else
            return null;
            
    }

    public User update(int id,User user){
        USERS
            .stream()
            .filter(
                (User u) -> 
                    u.id() == id
            )
            .findFirst()
            .ifPresent(
                (User u) -> 
                    USERS.set(USERS.indexOf(u), user)
            );
        
        return user;
        
        
    }


}