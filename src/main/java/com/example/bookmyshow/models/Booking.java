package com.example.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class Booking extends BaseModel{

    @ManyToMany
    private List<ShowSeat> showSeatList;
    // in the time of cancellation m:m

    @ManyToOne
    private User user;

    private double amount;

    @OneToMany
    private List<Payment> payments;

    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;
    // cardinaity with enums is always m:m

}
