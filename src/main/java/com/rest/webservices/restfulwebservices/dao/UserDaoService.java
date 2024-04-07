package com.rest.webservices.restfulwebservices.dao;

import com.rest.webservices.restfulwebservices.entity.User;
import com.rest.webservices.restfulwebservices.exception.UserNotfoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int userCount = 0;

    static{
        users.add(new User(++userCount, "Adam", LocalDate.now().minusYears(30)));
        users.add(new User(++userCount, "EVE", LocalDate.now().minusYears(20)));
        users.add(new User(++userCount, "Jim", LocalDate.now().minusYears(70)));
    }

    public List<User> findAll() {
        return users;
    }



    public User findUser(int id){
        Optional<User> userWithId = users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();

        // Check if user with userId = 1 is present
        if (userWithId.isPresent()) {
            User foundUser = userWithId.get();
            System.out.println("User found: " + foundUser);
            return foundUser;
        } else {
            System.out.printf("User with userId = %d not found%n", id);
        }
        return null;
    }

    public User save(User user){
        user.setId(++userCount);
        users.add(user);
        return user;
    }

    public User findOne(int id){
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    public String deleteById(int id) throws UserNotfoundException {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        if(users.removeIf(predicate)){
            return "SuccessFully Deleted";
        }
        else{
            throw new UserNotfoundException("Sorry, no user found with the specified Id");
        }
    }
}
