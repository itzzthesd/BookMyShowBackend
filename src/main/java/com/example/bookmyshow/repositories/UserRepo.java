package com.example.bookmyshow.repositories;

import com.example.bookmyshow.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<User, Long>{
    // sql query
    // JPA repo


    @Override
    Optional<User> findById(Long aLong);

    @Override
    User save(User user); // upsert method

    Optional<User> findByEmail(String email);
}
