package com.binar.finalproject.BEFlightTicket.service;

import com.binar.finalproject.BEFlightTicket.dto.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ScheduleService {
    ScheduleResponse addSchedule(ScheduleRequest scheduleRequest);
    ScheduleResponse updateSchedule(ScheduleRequest scheduleRequest, UUID scheduleId);
    List<ScheduleResponse> searchAirplaneSchedule(String airplaneName);
    List<ScheduleResponse> searchRouteSchedule(UUID routeId);
    List<SearchScheduleResponse> searchAirplaneTicketSchedule(String arrivalAirport, String departureAirport, LocalDate departureDate);
    List<ScheduleResponse> getAllSchedule();
}
