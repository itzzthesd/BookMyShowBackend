package com.example.bookmyshow.DTOs;

import lombok.Getter;
import lombok.Setter;
import com.example.bookmyshow.models.ResponseStatus;
@Getter
@Setter
public class BookMovieResponseDto {
    private Long bookingId;
    private double amount;
    private ResponseStatus responseStatus;
}
