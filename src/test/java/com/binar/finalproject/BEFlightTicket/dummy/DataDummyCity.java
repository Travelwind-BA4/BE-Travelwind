package com.binar.finalproject.BEFlightTicket.dummy;

import com.binar.finalproject.BEFlightTicket.model.Cities;

import java.util.List;

public class DataDummyCity {

    public static List<Cities> getCities(){
        Cities cities =  new Cities();
        Cities cities1 = new Cities();
        cities.setCityCode("SMG");
        cities.setCityName("semarang");
        cities1.setCityCode("BLI");
        cities1.setCityName("bali");
        return List.of(cities, cities1);
    }
}