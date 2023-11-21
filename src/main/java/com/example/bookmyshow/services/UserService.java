package com.example.bookmyshow.services;

import com.example.bookmyshow.models.User;
import com.example.bookmyshow.repositories.UserRepo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Getter
@Setter
@Service
public class UserService {
    private UserRepo userRepo;
    //private BCryptPasswordEncoder brcyptpasswordencoder

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User signUp(String email, String password){

        Optional<User> optionalUser = userRepo.findByEmail(email);

        if(optionalUser.isPresent()){
            return logIn(email, password);
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername("");
        user.setBookings(new ArrayList<>());

        return userRepo.save(user);
    }

    public User logIn(String email, String password){
        Optional<User> optionalUser = userRepo.findByEmail(email);

        if( optionalUser.isEmpty()){
            throw new RuntimeException("invalid email");
        }

        User user = optionalUser.get();

        if(password.equals(user.getPassword())){
            return user;
        }else {
            throw new RuntimeException("password mismatch");
        }
    }
}
