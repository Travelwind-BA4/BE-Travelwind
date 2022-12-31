package com.binar.finalproject.BEFlightTicket.services;

import com.binar.finalproject.BEFlightTicket.dto.AirplanesRequest;
import com.binar.finalproject.BEFlightTicket.dummy.DataDummyAirplane;
import com.binar.finalproject.BEFlightTicket.exception.DataAlreadyExistException;
import com.binar.finalproject.BEFlightTicket.model.Airplanes;
import com.binar.finalproject.BEFlightTicket.repository.AirplanesRepository;
import com.binar.finalproject.BEFlightTicket.service.impl.AirplaneServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AirplaneServiceImplTest {

    @Mock
    AirplanesRepository airplanesRepository;

    @InjectMocks
    private AirplaneServiceImpl airplaneService;

    private DataDummyAirplane dataDummyAirplane;
    private List<AirplanesRequest> dataAirplaneRequest = new ArrayList<>();
    private List<Airplanes> dataAirplane = new ArrayList<>();

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
        dataDummyAirplane = new DataDummyAirplane();
        dataAirplaneRequest = dataDummyAirplane.getDATA_AIRPLANES_REQUEST();
        dataAirplane = dataDummyAirplane.getDATA_AIRPLANES();
    }

    @Test
    @DisplayName("[Positive] Add new airplane")
    void testPositiveInsertAirplane(){
        Mockito.when(airplanesRepository.findById(dataAirplaneRequest.get(0).getAirplaneName())).thenReturn(null);
        Mockito.when(airplanesRepository.save(dataAirplane.get(0))).thenReturn(dataAirplane.get(0));
        var actualValue = airplaneService.insertAirplane(dataAirplaneRequest.get(0));
        var expectedValue1 = dataAirplaneRequest.get(0).getAirplaneName();
        var expectedValue2 = dataAirplaneRequest.get(0).getAirplaneType();
        Assertions.assertNotNull(actualValue);
        Assertions.assertNotNull(expectedValue1);
        Assertions.assertNotNull(expectedValue2);
        Assertions.assertEquals(expectedValue1, actualValue.getAirplaneName());
        Assertions.assertEquals(expectedValue2, actualValue.getAirplaneType());
    }
    @Test
    @DisplayName("[Positive] Search airplane by name")
    void testPositiveSearchAirplaneByName(){
        String airplaneName = "JET123";
        Optional<Airplanes> airplanes = dataDummyAirplane.getAirplaneByName(airplaneName);
        Airplanes airplaneData = airplanes.get();
        Mockito.when(airplanesRepository.findById(airplaneName)).thenReturn(Optional.of(airplaneData));
        var actualValue = airplaneService.searchAirplaneByName(airplaneName);

        Assertions.assertNotNull(airplaneData);
        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(airplaneName, actualValue.getAirplaneName());
    }
    @Test
    @DisplayName("[Positive] Get all airplane")
    void testPositiveGetAllAirplane(){
        Mockito.when(airplanesRepository.findAll()).thenReturn(dataAirplane);
        var actualValue = airplaneService.getAllAirplane();
        var expectedSize = dataAirplane.size();
        var expectedValue1 = dataAirplane.get(0);
        var expectedValue2 = dataAirplane.get(1);
        var expectedValue3 = dataAirplane.get(2);
        var expectedValue4 = dataAirplane.get(3);
        var expectedValue5 = dataAirplane.get(4);

        Assertions.assertNotNull(actualValue);
        Assertions.assertNotNull(expectedValue1);
        Assertions.assertNotNull(expectedValue2);
        Assertions.assertNotNull(expectedValue3);
        Assertions.assertNotNull(expectedValue4);
        Assertions.assertNotNull(expectedValue5);
        Assertions.assertEquals(expectedSize, actualValue.size());

        Assertions.assertEquals(expectedValue1.getAirplaneName(), actualValue.get(0).getAirplaneName());
        Assertions.assertEquals(expectedValue1.getAirplaneType(), actualValue.get(0).getAirplaneType());

        Assertions.assertEquals(expectedValue2.getAirplaneName(), actualValue.get(1).getAirplaneName());
        Assertions.assertEquals(expectedValue2.getAirplaneType(), actualValue.get(1).getAirplaneType());

        Assertions.assertEquals(expectedValue3.getAirplaneName(), actualValue.get(2).getAirplaneName());
        Assertions.assertEquals(expectedValue3.getAirplaneType(), actualValue.get(2).getAirplaneType());

        Assertions.assertEquals(expectedValue4.getAirplaneName(), actualValue.get(3).getAirplaneName());
        Assertions.assertEquals(expectedValue4.getAirplaneType(), actualValue.get(3).getAirplaneType());

        Assertions.assertEquals(expectedValue5.getAirplaneName(), actualValue.get(4).getAirplaneName());
        Assertions.assertEquals(expectedValue5.getAirplaneType(), actualValue.get(4).getAirplaneType());
    }
    @Test
    @DisplayName("[Positive] Update airplane")
    void testPositiveUpdateAirplane(){
        AirplanesRequest dataUpdateAirplane = new AirplanesRequest();
        dataUpdateAirplane.setAirplaneName("JET456");
        dataUpdateAirplane.setAirplaneType("Pink");

        AirplanesRequest airplanesRequest = dataAirplaneRequest.get(1);
        Optional<Airplanes> airplanes = dataDummyAirplane.getAirplaneByName(airplanesRequest.getAirplaneName());

        Airplanes airplaneData = airplanes.get();

        Mockito.when(airplanesRepository.findById(airplanesRequest.getAirplaneName())).thenReturn(Optional.of(airplaneData));

        airplaneData.setAirplaneType(dataUpdateAirplane.getAirplaneType());

        Mockito.when(airplanesRepository.save(airplaneData)).thenReturn(airplaneData);

        var actualValue = airplaneService.updateAirplane(dataUpdateAirplane, airplanesRequest.getAirplaneName());

        Assertions.assertNotNull(airplaneData);
        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(dataUpdateAirplane.getAirplaneType(), actualValue.getAirplaneType());
    }
    @Test
    @DisplayName("[Positive] Delete airplane")
    void testPositiveDeleteAirplane(){
        String airplaneName = "JET123";

        Optional<Airplanes> airplanes = dataDummyAirplane.getAirplaneByName(airplaneName);

        Airplanes airplaneData = airplanes.get();

        Mockito.when(airplanesRepository.findById(airplaneName)).thenReturn(Optional.of(airplaneData));
        Mockito.doNothing().when(airplanesRepository).deleteById(airplaneData.getAirplaneName());

        var actualValue = airplaneService.deleteAirplane(airplaneName);
        var expectedValue = true;

        Assertions.assertNotNull(airplaneData);
        Assertions.assertEquals(expectedValue, actualValue);
    }
}
