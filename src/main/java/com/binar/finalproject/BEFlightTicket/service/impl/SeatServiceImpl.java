package com.binar.finalproject.BEFlightTicket.service.impl;

import com.binar.finalproject.BEFlightTicket.dto.SeatRequest;
import com.binar.finalproject.BEFlightTicket.dto.SeatResponse;
import com.binar.finalproject.BEFlightTicket.model.Airplanes;
import com.binar.finalproject.BEFlightTicket.model.Seats;
import com.binar.finalproject.BEFlightTicket.repository.AirplanesRepository;
import com.binar.finalproject.BEFlightTicket.repository.SeatRepository;
import com.binar.finalproject.BEFlightTicket.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private AirplanesRepository airplanesRepository;

    @Override
    public SeatResponse addSeat(SeatRequest seatRequest) {
       try {
           Optional<Airplanes> airplanes = airplanesRepository.findById(seatRequest.getAirplane_name());
           if (airplanes.isPresent())
           {
               Seats seats = seatRequest.toSeats(airplanes.get());
               try {
                   seatRepository.save(seats);
                   return SeatResponse.build(seats);
               }
               catch (Exception exception)
               {
                   return null;
               }
           }
           else
               return null;
       }
       catch (Exception exception)
       {
           return null;
       }
    }

    @Override
    public SeatResponse searchSeatBySeatNumber(String seatNumber) {
        Seats seats = seatRepository.findByNumber(seatNumber);
        if (seats != null)
            return SeatResponse.build(seats);
        else
            return null;
    }

    @Override
    public List<SeatResponse> getAllSeat() {
        List<Seats> allSeat = seatRepository.findAll();
        List<SeatResponse> allSeatResponse = new ArrayList<>();
        for (Seats seats : allSeat)
        {
            SeatResponse seatResponse = SeatResponse.build(seats);
            allSeatResponse.add(seatResponse);
        }
        return allSeatResponse;
    }

    @Override
    public SeatResponse updateSeat(SeatRequest seatRequest, String seatNumber) {
        Seats seats = seatRepository.findByNumber(seatNumber);
        String message = null;
        if (seats != null)
        {
            if (seatRequest.getSeatNumber() != null)
                seats.setSeatNumber(seatRequest.getSeatNumber());
            else
                message = "Seat with number: "+seatNumber+" not found";
            if (seatRequest.getSeatType() != null)
                seats.setSeatType(seatRequest.getSeatType());
            Optional<Airplanes> airplanes = airplanesRepository.findById(seatRequest.getAirplane_name());
            if (airplanes.isPresent())
                seats.setAirplanesSeats(airplanes.get());
            else
                message = "Airplane with this name doesnt exist";
            if (message != null)
                return null;
            else
            {
                seatRepository.saveAndFlush(seats);
                return SeatResponse.build(seats);
            }
        }
        else
            return null;
    }

    @Override
    public List<SeatResponse> searchAirplaneSeat(String airplaneName) {
        List<Seats> allSeat = seatRepository.findAllSeatsByAirplaneName(airplaneName);
        List<SeatResponse> allSeatResponse = new ArrayList<>();
        for (Seats seats : allSeat)
        {
            SeatResponse seatResponse = SeatResponse.build(seats);
            allSeatResponse.add(seatResponse);
        }
        return allSeatResponse;
    }


}