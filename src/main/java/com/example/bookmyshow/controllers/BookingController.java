package com.example.bookmyshow.controllers;

import com.example.bookmyshow.DTOs.BookMovieRequestDto;
import com.example.bookmyshow.DTOs.BookMovieResponseDto;
import com.example.bookmyshow.exceptions.ShowSeatNotAvailableException;
import com.example.bookmyshow.models.Booking;
import com.example.bookmyshow.models.ResponseStatus;
import com.example.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
@Controller
public class BookingController {
    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookMovieResponseDto bookMovie(BookMovieRequestDto bookMovieRequestDto){
        BookMovieResponseDto response = new BookMovieResponseDto();
        try {
            Booking booking = bookingService.bookMovie(
                    bookMovieRequestDto.getUserId(),
                    bookMovieRequestDto.getShowId(),
                    bookMovieRequestDto.getShowSeatIds());

            response.setBookingId(booking.getId());
            response.setResponseStatus(ResponseStatus.CONFIRMED);
            response.setAmount(booking.getAmount());

        } catch (ShowSeatNotAvailableException e) {
            throw new RuntimeException(e);
        }catch (RuntimeException e){
            response.setResponseStatus(ResponseStatus.FAILURE);
        } catch (UserPrincipalNotFoundException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
