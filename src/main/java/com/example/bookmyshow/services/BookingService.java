package com.example.bookmyshow.services;

import com.example.bookmyshow.exceptions.ShowNotFoundException;
import com.example.bookmyshow.exceptions.ShowSeatNotAvailableException;
import com.example.bookmyshow.exceptions.UserNotFoundException;
import com.example.bookmyshow.models.*;
import com.example.bookmyshow.repositories.BookingRepo;
import com.example.bookmyshow.repositories.ShowRepo;
import com.example.bookmyshow.repositories.ShowSeatRepo;
import com.example.bookmyshow.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

// ctrl + alt + o --> to remove redundant imports


@Service
public class BookingService {
    private UserRepo userRepo;
    private ShowSeatRepo showSeatRepo;
    private ShowRepo showRepo;
    private BookingRepo bookingRepo;
    private PriceCalculatorService priceCalculatorService;

    @Autowired
    public BookingService(UserRepo userRepo,
                          ShowSeatRepo showSeatRepo,
                          ShowRepo showRepo,
                          BookingRepo bookingRepo,
                          PriceCalculatorService priceCalculatorService) {
        this.userRepo = userRepo;
        this.showSeatRepo = showSeatRepo;
        this.showRepo = showRepo;
        this.bookingRepo = bookingRepo;
        this.priceCalculatorService = priceCalculatorService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long UserId, Long showId,
                             List<Long> showSeatId)
            throws UserPrincipalNotFoundException,
            ShowSeatNotAvailableException {

        // ----- take a lock -----------
        // get the user from user id
        Optional<User> optionalUser = userRepo.findById(UserId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("There is no user.");
        }
        User bookedBy = optionalUser.get();

        // get the show from the show id
        Optional< Show> optionalShow = showRepo.findById(showId);
        if(optionalShow.isEmpty()){
            throw new ShowNotFoundException("There is no show.");
        }
        Show show = optionalShow.get();

        // get the showseat from thr list
        List<ShowSeat> showSeats = showSeatRepo.findAllById(showSeatId);

        // check if all the seats are avaialable
        for(ShowSeat showSeat: showSeats){
            if(!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)){
                throw new ShowSeatNotAvailableException("there is no seat available.");
            }
        }


        // change the status to be locked
        List<ShowSeat> bookedShowSeats = new ArrayList<>();
        for(ShowSeat showSeat: showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            bookedShowSeats.add(showSeatRepo.save(showSeat));
        }


        // create the booking object
        Booking booking = new Booking();
        booking.setUser(bookedBy);
        booking.setBookingStatus(BookingStatus.IN_PROGRESS);
        booking.setPayments(new ArrayList<>());
        booking.setShowSeatList(bookedShowSeats);
        booking.setCreatedAt(new Date());
        booking.setLastModifiedAt(new Date());
        booking.setAmount(priceCalculatorService.CalculationBookingPrice(bookedShowSeats, show));

        Booking savedbooking = bookingRepo.save(booking);
        // change the status booked
        // return the booking object
        // -------- release the lock
        return savedbooking;
    }
}
