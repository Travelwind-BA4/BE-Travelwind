package com.binar.finalproject.BEFlightTicket.service.impl;

import com.binar.finalproject.BEFlightTicket.dto.TicketRequest;
import com.binar.finalproject.BEFlightTicket.dto.TicketResponse;
import com.binar.finalproject.BEFlightTicket.model.*;
import com.binar.finalproject.BEFlightTicket.repository.OrderRepository;
import com.binar.finalproject.BEFlightTicket.repository.SeatRepository;
import com.binar.finalproject.BEFlightTicket.repository.TicketRepository;
import com.binar.finalproject.BEFlightTicket.repository.TravelerListRepository;
import com.binar.finalproject.BEFlightTicket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TravelerListRepository travelerListRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public TicketResponse addTicket(TicketRequest ticketRequest) {
        try {
            Optional<TravelerList> travelerList = travelerListRepository.findById(ticketRequest.getTravelerId());
            Optional<Orders> orders = orderRepository.findById(ticketRequest.getOrderId());
            Optional<Seats> seats = seatRepository.findById(ticketRequest.getSeatId());

            if (travelerList.isPresent()) {
                if (orders.isPresent()) {
                    if (seats.isPresent()) {
                        Tickets tickets = ticketRequest.toTickets(travelerList.get(), orders.get(), seats.get());
                        try {
                            ticketRepository.save(tickets);
                            return TicketResponse.build(tickets);
                        } catch (Exception exception) {
                            return null;
                        }
                    }

                }
            } else
                return null;
        } catch (Exception exception) {
            return null;
        }
        return null;
    }


    @Override
    public List<TicketResponse> getAllTicket() {
        List<Tickets> allTicket = ticketRepository.findAll();
        List<TicketResponse> allTicketResponse = new ArrayList<>();
        for (Tickets tickets : allTicket) {
            TicketResponse ticketResponse = TicketResponse.build(tickets);
            allTicketResponse.add(ticketResponse);
        }
        return allTicketResponse;
    }

    @Override
    public TicketResponse updateTicket(TicketRequest ticketRequest, UUID ticketId) {
        Optional<Tickets> isTicket = ticketRepository.findById(ticketId);
        String message = null;
        if (isTicket.isPresent()) {
            Tickets tickets = isTicket.get();
            Optional<TravelerList> travelerList = travelerListRepository.findById(ticketRequest.getTravelerId());
            if (travelerList.isPresent())
                tickets.setTravelerListTicket(travelerList.get());
            else
                message = "Traveler with this id doesnt exist";
            Optional<Orders> orders = orderRepository.findById(ticketRequest.getOrderId());
            if (orders.isPresent())
                tickets.setOrdersTicket(orders.get());
            else
                message = "Order with this id doesnt exist";
            Optional<Seats> seats = seatRepository.findById(ticketRequest.getSeatId());
            if (seats.isPresent())
                tickets.setSeatsTicket(seats.get());
            else
                message = "Seat with this id doesnt exist";
            if (message != null)
                return null;
            else {
                ticketRepository.save(tickets);
                return TicketResponse.build(tickets);
            }
        } else
            return null;
    }

    @Override
    public TicketResponse searchTicketById(UUID ticketId) {
        Optional<Tickets> isTicket = ticketRepository.findById(ticketId);
        return isTicket.map(TicketResponse::build).orElse(null);

    }

    @Override
    public List<TicketResponse> searchOrderTicket(UUID orderId) {
        List<Tickets> allTicket = ticketRepository.getAllOrderTicket(orderId);
        List<TicketResponse> allTicketResponse = new ArrayList<>();
        for (Tickets tickets : allTicket)
        {
            TicketResponse ticketResponse = TicketResponse.build(tickets);
            allTicketResponse.add(ticketResponse);
        }
        return allTicketResponse;
    }

}