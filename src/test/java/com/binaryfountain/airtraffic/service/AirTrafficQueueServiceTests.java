package com.binaryfountain.airtraffic.service;

import java.time.LocalDateTime;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.binaryfountain.airtraffic.domain.Flight;
import com.binaryfountain.airtraffic.enums.AircraftSize;
import com.binaryfountain.airtraffic.enums.AircraftType;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AirTrafficQueueServiceTests {

	@Inject
	private AirTrafficQueueService airTrafficQueue;
	
	/**
	 * Test enqueue functionality
	 * 
	 * @throws Exception
	 */
	@Test
	public void enqueueTest() throws Exception {
		
        // Given
		LocalDateTime now = LocalDateTime.now();
		Flight flight = Flight.builder().flightId("AA-123").size(AircraftSize.SMALL).type(AircraftType.VIP).queuedTime(now).build();
		
        // When
		Flight queuedFlight = airTrafficQueue.enqueue(flight);
        
        // Then
		Assertions.assertThat(flight.equals(queuedFlight));
        
	}
	
	/**
	 * Test dequeue functionality
	 * 
	 * @throws Exception
	 */
	@Test
	public void dequeueTest() throws Exception {
		
        // Given
		LocalDateTime now = LocalDateTime.now();
		Flight flight = Flight.builder().flightId("AA-123").size(AircraftSize.SMALL).type(AircraftType.VIP).queuedTime(now).build();
		
        // When
		airTrafficQueue.enqueue(flight);
		Flight dequeuedFlight = airTrafficQueue.dequeue();
        
        // Then
		Assertions.assertThat(flight.equals(dequeuedFlight));
        
	}
	
	/**
	 * Test size priority functionality
	 * 
	 * @throws Exception
	 */
	@Test
	public void dequeueSizePriorityTest() throws Exception {
		
        // Given
		LocalDateTime now = LocalDateTime.now();
		Flight smallFlight = Flight.builder().flightId("AA-123").size(AircraftSize.SMALL).type(AircraftType.VIP).queuedTime(now).build();
		Flight largeFlight = Flight.builder().flightId("AA-123").size(AircraftSize.LARGE).type(AircraftType.VIP).queuedTime(now).build();
		
        // When
		airTrafficQueue.enqueue(smallFlight);
		airTrafficQueue.enqueue(largeFlight);
		Flight dequeuedFlight = airTrafficQueue.dequeue();
		
        // Then
		Assertions.assertThat(largeFlight.equals(dequeuedFlight));
        
	}
	
	/**
	 * Test size priority functionality
	 * 
	 * @throws Exception
	 */
	@Test
	public void dequeueTypePriorityTest() throws Exception {
		
        // Given
		LocalDateTime now = LocalDateTime.now();
		Flight cargoFlight = Flight.builder().flightId("FX-123").size(AircraftSize.SMALL).type(AircraftType.CARGO).queuedTime(now).build();
		Flight emergencyFlight = Flight.builder().flightId("EM-1").size(AircraftSize.SMALL).type(AircraftType.EMERGENCY).queuedTime(now).build();
		Flight vipFlight = Flight.builder().flightId("VP-1").size(AircraftSize.SMALL).type(AircraftType.VIP).queuedTime(now).build();
		Flight passengerFlight = Flight.builder().flightId("AA-123").size(AircraftSize.SMALL).type(AircraftType.PASSENGER).queuedTime(now).build();
		
        // When
		airTrafficQueue.enqueue(cargoFlight);
		airTrafficQueue.enqueue(emergencyFlight);
		airTrafficQueue.enqueue(vipFlight);
		airTrafficQueue.enqueue(passengerFlight);
		
		
        // Then
		Flight dequeuedFlight = airTrafficQueue.dequeue();
		Assertions.assertThat(emergencyFlight.equals(dequeuedFlight));
		dequeuedFlight = airTrafficQueue.dequeue();
		Assertions.assertThat(vipFlight.equals(dequeuedFlight));
		dequeuedFlight = airTrafficQueue.dequeue();
		Assertions.assertThat(passengerFlight.equals(dequeuedFlight));
		dequeuedFlight = airTrafficQueue.dequeue();
		Assertions.assertThat(cargoFlight.equals(dequeuedFlight));
	}
	
	/**
	 * Test size and type priority functionality
	 * 
	 * @throws Exception
	 */
	@Test
	public void dequeueSizeAndTypePriorityTest() throws Exception {
		
        // Given
		LocalDateTime now = LocalDateTime.now();
		Flight cargoFlight = Flight.builder().flightId("FX-123").size(AircraftSize.LARGE).type(AircraftType.CARGO).queuedTime(now).build();
		Flight emergencyFlight = Flight.builder().flightId("EM-1").size(AircraftSize.SMALL).type(AircraftType.EMERGENCY).queuedTime(now).build();
		
        // When
		airTrafficQueue.enqueue(cargoFlight);
		airTrafficQueue.enqueue(emergencyFlight);
		
		
        // Then
		Flight dequeuedFlight = airTrafficQueue.dequeue();
		Assertions.assertThat(emergencyFlight.equals(dequeuedFlight));
		dequeuedFlight = airTrafficQueue.dequeue();
		Assertions.assertThat(cargoFlight.equals(dequeuedFlight));
	}
	
	/**
	 * Test initialize functionality
	 * 
	 * @throws Exception
	 */
	@Test
	public void initializeTest() throws Exception {
		
        // Given
		LocalDateTime now = LocalDateTime.now();
		Flight cargoFlight = Flight.builder().flightId("FX-123").size(AircraftSize.LARGE).type(AircraftType.CARGO).queuedTime(now).build();
		Flight emergencyFlight = Flight.builder().flightId("EM-1").size(AircraftSize.SMALL).type(AircraftType.EMERGENCY).queuedTime(now).build();
		
        // When
		airTrafficQueue.enqueue(cargoFlight);
		airTrafficQueue.enqueue(emergencyFlight);
		airTrafficQueue.initialize();
		
		
        // Then
		Flight dequeuedFlight = airTrafficQueue.dequeue();
		Assertions.assertThat(dequeuedFlight == null);
	}
	
}
