package com.binar.finalproject.BEFlightTicket.controller;

import com.binar.finalproject.BEFlightTicket.dto.*;
import com.binar.finalproject.BEFlightTicket.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportsController {

    @Autowired
    private AirportService airportService;

    @PostMapping(value = "/add-airport", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> createAirports(@RequestBody AirportRequest airportRequest) {
        MessageModel messageModel = new MessageModel();
        AirportResponse airportResponse = airportService.addAirports(airportRequest);
        if(airportResponse == null) {
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            messageModel.setMessage("Failed to create Airports");
            return ResponseEntity.badRequest().body(messageModel);
        }
        else {
            messageModel.setStatus(HttpStatus.CREATED.value());
            messageModel.setMessage("Create new City");
            messageModel.setData(airportResponse);
            return ResponseEntity.ok().body(messageModel);

        }
    }

    @GetMapping(value = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageModel> getAllAirports() {
        MessageModel messageModel = new MessageModel();
        try {
            List<AirportResponse> getAllAirports = airportService.searchAllAirports();
            messageModel.setMessage("Success get all Airports");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(getAllAirports);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all Airports");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }
}
