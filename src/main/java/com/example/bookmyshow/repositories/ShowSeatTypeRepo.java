package com.example.bookmyshow.repositories;

import com.example.bookmyshow.models.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatTypeRepo extends JpaRepository<ShowSeatType, Long> {
   List<ShowSeatType> findAllByShow(Long showId);
}
