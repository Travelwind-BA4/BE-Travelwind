package com.binar.finalproject.BEFlightTicket.service.impl;

import com.binar.finalproject.BEFlightTicket.dto.PassportRequest;
import com.binar.finalproject.BEFlightTicket.dto.PassportResponse;
import com.binar.finalproject.BEFlightTicket.model.*;
import com.binar.finalproject.BEFlightTicket.repository.CountriesRepository;
import com.binar.finalproject.BEFlightTicket.repository.PassportRepository;
import com.binar.finalproject.BEFlightTicket.repository.TravelerListRepository;
import com.binar.finalproject.BEFlightTicket.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PassportServiceImpl implements PassportService {
    @Autowired
    PassportRepository passportRepository;
    @Autowired
    TravelerListRepository travelerListRepository;
    @Autowired
    CountriesRepository countriesRepository;

    @Override
    public PassportResponse registerPassport(PassportRequest passportRequest) {
        try{
            Optional<TravelerList> travelerList = travelerListRepository.findById(passportRequest.getTravelerId());
            Optional<Countries> countries = countriesRepository.findById(passportRequest.getCountryCode());

            if(travelerList.isPresent())
            {
                if(countries.isPresent())
                {
                    Passport passport = passportRequest.toPassport(countries.get(), travelerList.get());

                    try {
                        passportRepository.save(passport);
                        return PassportResponse.build(passport);
                    }
                    catch(Exception exception)
                    {
                        return null;
                    }
                }
                else
                    return null;
            }
            else
                return null;
        }catch (Exception exception)
        {
            return null;
        }
    }

    @Override
    public PassportResponse searchTravelerListPassport(UUID travelerId) {
        Passport passport = passportRepository.findAllPassportByTravelerList(travelerId);
        return PassportResponse.build(passport);
    }

    @Override
    public PassportResponse searchPassport(UUID passportId) {
        Optional<Passport> passport = passportRepository.findById(passportId);
        return passport.map(PassportResponse::build).orElse(null);
    }

    @Override
    public PassportResponse updatePassport(PassportRequest passportRequest, UUID passportId) {
        Optional<Passport> isPassport = passportRepository.findById(passportId);
        String message = null;
        if (isPassport.isPresent()) {
            Passport passport = isPassport.get();
            passport.setPassportNumber(passportRequest.getPassportNumber());
            passport.setPassportExpiry(passportRequest.getPassportExpiry());
            Optional<Countries> countries = countriesRepository.findById(passportRequest.getCountryCode());
            if (countries.isPresent())
                passport.setCountriesPassport(countries.get());
            else
                message = "Countries with this code doesnt exist";

            if (message != null)
                return null;
            else {
                passportRepository.saveAndFlush(passport);
                return PassportResponse.build(passport);
            }
        }
        else
            return null;
    }
}
