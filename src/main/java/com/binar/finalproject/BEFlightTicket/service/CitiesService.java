package com.binar.finalproject.BEFlightTicket.service;

import com.binar.finalproject.BEFlightTicket.dto.CitiesRequest;
import com.binar.finalproject.BEFlightTicket.dto.CitiesResponse;

import java.util.List;

public interface CitiesService {

    CitiesResponse addCity(CitiesRequest citiesRequest);
    List<CitiesResponse> searchAllCity();
    CitiesResponse updateCity(CitiesRequest citiesRequest, String cityName);
    Boolean deleteCity(String cityName);
    CitiesResponse searchCityByName(String cityName);
}