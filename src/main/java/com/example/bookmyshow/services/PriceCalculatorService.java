package com.example.bookmyshow.services;

import com.example.bookmyshow.models.Show;
import com.example.bookmyshow.models.ShowSeat;
import com.example.bookmyshow.models.ShowSeatType;
import com.example.bookmyshow.repositories.ShowSeatTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalculatorService {
    private ShowSeatTypeRepo showSeatTypeRepo;

    @Autowired
    public PriceCalculatorService(ShowSeatTypeRepo showSeatTypeRepo) {
        this.showSeatTypeRepo = showSeatTypeRepo;
    }

    public int CalculationBookingPrice(List<ShowSeat> showSeats, Show show){
        int amount = 0;
        List<ShowSeatType> showSeatTypes = showSeatTypeRepo.findAllByShow(show.getId());
        for(ShowSeat showseat: showSeats){
            for(ShowSeatType showSeatType: showSeatTypes){
                if(showseat.getSeat().getSeatType().equals(showSeatType.getSeatType())){
                    amount += showSeatType.getPrice();
                }
            }
            // inner for loop can be removed if we use hashmap of showseattype annd price
        }
        return amount;
    }
}
