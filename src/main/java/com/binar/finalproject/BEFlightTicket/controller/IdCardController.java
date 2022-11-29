package com.binar.finalproject.BEFlightTicket.controller;

import com.binar.finalproject.BEFlightTicket.dto.*;
import com.binar.finalproject.BEFlightTicket.service.IdCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/id-card")
public class IdCardController {
    @Autowired
    IdCardService idCardService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('BUYER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> registerIdCard(@RequestBody IdCardRequest idCardRequest) {
        MessageModel messageModel = new MessageModel();

        IdCardResponse idCardResponse = idCardService.registerIdCard(idCardRequest);
        if(idCardResponse == null)
        {
            messageModel.setMessage("Failed register new id-card");
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Register new id-card");
            messageModel.setData(idCardResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @GetMapping("/get-all/traveler/{travelerId}")
    @PreAuthorize("hasAnyRole('BUYER')")
    public ResponseEntity<MessageModel> getTravelerIdCard(@PathVariable UUID travelerId){
        MessageModel messageModel = new MessageModel();
        try {
            List<IdCardResponse> idCardResponses = idCardService.searchTravelerListIdCard(travelerId);
            messageModel.setMessage("Success get id-card by traveler id : " + travelerId);
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(idCardResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get id-card by traveler id, " + travelerId + " not found");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("/get/{idCardNumber}")
    @PreAuthorize("hasAnyRole('BUYER')")
    public ResponseEntity<MessageModel> getIdCard(@PathVariable String idCardNumber){
        MessageModel messageModel = new MessageModel();
        try {
            IdCardResponse idCardResponses = idCardService.searchIdCard(idCardNumber);
            messageModel.setMessage("Success get id-card");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(idCardResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get id-card");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }
    
    @PutMapping("/update/{idCardNumber}")
    @PreAuthorize("hasAnyRole('BUYER')")
    public ResponseEntity<MessageModel> updateIdCard(@PathVariable String idCardNumber, @RequestBody IdCardRequest idCardRequest) {
        MessageModel messageModel = new MessageModel();
        IdCardResponse idCardResponse = idCardService.updateIdCard(idCardRequest, idCardNumber);

        if(idCardResponse == null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage("Failed update id-card with number : " + idCardNumber);
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Success update id-card with number : " + idCardNumber);
            messageModel.setData(idCardResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }
}
