package com.example.bookmyshow.repositories;

import com.example.bookmyshow.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ShowSeatRepo extends JpaRepository<ShowSeat, Long> {

    Optional<ShowSeat> findAllbyId(Long aLong);

    @Override
    ShowSeat save(ShowSeat showSeat); // it can create an object if it is not present , or it
    // can update the object as well
    // they are call UPSERT
}
