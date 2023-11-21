package com.example.bookmyshow.controllers;

import com.example.bookmyshow.DTOs.SignUpRequestDto;
import com.example.bookmyshow.DTOs.SignUpResponseDto;
import com.example.bookmyshow.models.ResponseStatus;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.repositories.UserRepo;
import com.example.bookmyshow.services.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Getter
@Setter
@Controller
public class UserController {
    private UserRepo userRepo;
    private UserService userService;

    @Autowired
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto){
        Optional<User> optionalUser = userRepo.findByEmail(signUpRequestDto.getEmail());
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();

        if(optionalUser.isEmpty()){ // sign up
            try{
                User user = userService.signUp(signUpRequestDto.getEmail(),
                        signUpRequestDto.getPassword());
                signUpResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
                signUpResponseDto.setUserId(user.getId());

            } catch (Exception e){
                signUpResponseDto.setResponseStatus(ResponseStatus.FAILURE);
            }
            return signUpResponseDto;
        } else { // log in
            try{
               User user = userService.logIn(signUpRequestDto.getEmail(),
                        signUpRequestDto.getPassword());
                signUpResponseDto.setUserId(user.getId());
                signUpResponseDto.setResponseStatus(ResponseStatus.SUCCESS);

            } catch (Exception e){
                signUpResponseDto.setResponseStatus(ResponseStatus.FAILURE);
            }
            return signUpResponseDto;
        }

    }
}
