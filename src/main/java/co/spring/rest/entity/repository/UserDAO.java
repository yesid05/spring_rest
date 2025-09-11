package co.spring.rest.entity.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import co.spring.rest.entity.bo.User;
import co.spring.rest.error.CreatedError;
import co.spring.rest.error.NotFoundError;

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
                    (user) -> user.getId()==id
                )
            .findFirst()
            .orElseThrow(() -> new NotFoundError("User not found","User cloud not find in the list.",null));
    }

    public List<User> findBySalary(BigDecimal salary){
        List<User> userList = USERS
            .stream()
            .filter(
                (user) -> user.getSalary().equals(salary)
            )
            .toList();
        
        if(userList.isEmpty()){
            throw new NotFoundError("List user not found", "List of cloud users not found by salary.", null);
        }

        return userList;
            
    }

    public User add(User user){

        if(USERS.add(user))
            return user;
        else
            throw new CreatedError("User not created", "User cloud not created in the list.", null);
            
    }

    public User update(int id,User user){
        USERS
            .stream()
            .filter(
                (User u) -> 
                    u.getId() == id
            )
            .findFirst()
            .ifPresent(
                (User u) -> 
                    USERS.set(USERS.indexOf(u), user)
            );
        
        return user;
        
        
    }

    public User updateItem(int id, Map<String,Object> userMap){

        User user = this.findById(id);

        System.out.println(user);

        if(userMap.containsKey("name"))
            user.setName((String)userMap.get("name"));

        if(userMap.containsKey("lastName"))
            user.setLastName((String)userMap.get("lastName"));
        
        if(userMap.containsKey("birthDay"))
            user.setBirthDay( LocalDate.parse((String)userMap.get("birthDay")));

        if(userMap.containsKey("salary"))
            user.setSalary(BigDecimal.valueOf(Double.parseDouble(String.valueOf(userMap.get("salary")))));
        
        if(userMap.containsKey("active"))
            user.setActive((Boolean)userMap.get("active"));
        
        return this.update(id, user);

    }

    public User delete(int id){
        User u = this.findById(id);
        if(u!=null){
            USERS.remove(u);
        }

        return u;
    }

    public List<User> deleteBySalary(BigDecimal salary){
        List<User> listUser = this.findBySalary(salary);

        for (User user : listUser) {
            USERS.remove(user);
        }

        return listUser;
    }


}