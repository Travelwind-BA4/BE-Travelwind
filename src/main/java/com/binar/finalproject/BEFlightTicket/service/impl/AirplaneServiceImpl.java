package com.binar.finalproject.BEFlightTicket.service.impl;

import com.binar.finalproject.BEFlightTicket.dto.AirplanesRequest;
import com.binar.finalproject.BEFlightTicket.dto.AirplanesResponse;
import com.binar.finalproject.BEFlightTicket.exception.DataAlreadyExistException;
import com.binar.finalproject.BEFlightTicket.exception.DataNotFoundException;
import com.binar.finalproject.BEFlightTicket.model.Airplanes;
import com.binar.finalproject.BEFlightTicket.model.PaymentMethods;
import com.binar.finalproject.BEFlightTicket.repository.AirplanesRepository;
import com.binar.finalproject.BEFlightTicket.service.AirplanesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AirplaneServiceImpl implements AirplanesService {
    @Autowired
    private AirplanesRepository airplanesRepository;

    @Override
    public AirplanesResponse insertAirplane(AirplanesRequest airplanesRequest) {
        Airplanes isAirplaneExist = airplanesRepository.findByName(airplanesRequest.getAirplaneName());
        if (isAirplaneExist == null) {
            Airplanes airplanes = airplanesRequest.toAirplanes();
            airplanesRepository.save(airplanes);
            return AirplanesResponse.build(airplanes);
        }
        else {
            throw new DataAlreadyExistException ("Airplane with this name already exist");
        }
    }

    @Override
    public AirplanesResponse searchAirplaneByName(String airplaneName) {
        Airplanes isAirplanes = airplanesRepository.findByName(airplaneName);
        if (isAirplanes != null) {
            return AirplanesResponse.build(isAirplanes);
        }
        else {
            throw new DataNotFoundException("Airplane not found");
        }
    }

    @Override
    public List<AirplanesResponse> getAllAirplane() {
        List<Airplanes> allAirplane = airplanesRepository.findAll();
        List<AirplanesResponse> allAirplaneResponse = new ArrayList<>();
        for (Airplanes airplanes : allAirplane){
            AirplanesResponse airplanesResponse = AirplanesResponse.build(airplanes);
            allAirplaneResponse.add(airplanesResponse);
        }
        return allAirplaneResponse;
    }

    @Override
    public AirplanesResponse updateAirplane(AirplanesRequest airplanesRequest, String airplaneName) {
        Airplanes isAirplanes = airplanesRepository.findByName(airplaneName);
        if (isAirplanes != null){
            if (airplanesRequest.getAirplaneName() != null)
            {
                Airplanes airplanes = airplanesRepository.findByName(airplanesRequest.getAirplaneName());
                if(airplanes == null || isAirplanes.getAirplaneName().equals(airplanesRequest.getAirplaneName()))
                    isAirplanes.setAirplaneName(airplanesRequest.getAirplaneName());
                else
                    throw new DataAlreadyExistException("Airplane with this name already exist");
            }
            if (airplanesRequest.getAirplaneType() != null)
                isAirplanes.setAirplaneType(airplanesRequest.getAirplaneType());
            airplanesRepository.save(isAirplanes);
            return AirplanesResponse.build(isAirplanes);
        }
        else
            throw new DataNotFoundException("Airplane not found");
    }

    @Override
    public Boolean deleteAirplane(String airplaneName) {
        Airplanes isAirplanes = airplanesRepository.findByName(airplaneName);
        if (isAirplanes != null){
            airplanesRepository.deleteById(isAirplanes.getAirplaneName());
            return true;
        }
        else {
            throw new DataNotFoundException("Airplane not found");
        }
    }
}
