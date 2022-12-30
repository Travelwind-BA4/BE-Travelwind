package com.binar.finalproject.BEFlightTicket.dto;

import com.binar.finalproject.BEFlightTicket.model.Cities;
import com.binar.finalproject.BEFlightTicket.model.Countries;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CitiesRequest {
    @NotEmpty(message = "City code is required.")
    private String cityCode;
    @NotEmpty(message = "City name is required.")
    private String cityName;
    @NotEmpty(message = "Countries is required.")
    private String countryCode;

    public Cities toCities(Countries countries) {
        return Cities.builder()
                .cityCode(this.cityCode)
                .cityName(this.cityName)
                .countriesCities(countries)
                .build();

    }
}
