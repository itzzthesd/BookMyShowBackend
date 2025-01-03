package com.example.bookmyshow.repositories;

import com.example.bookmyshow.models.Show;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Function;
@Repository
public interface ShowRepo extends JpaRepository<Show, Long> {
    @Override
    Optional<Show> findById(Long aLong);
}
