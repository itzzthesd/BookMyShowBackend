package com.example.bookmyshow.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String m){
        super(m);
    }


}
