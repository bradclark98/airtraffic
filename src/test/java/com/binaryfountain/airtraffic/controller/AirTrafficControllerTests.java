package com.binaryfountain.airtraffic.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.binaryfountain.airtraffic.domain.Flight;
import com.binaryfountain.airtraffic.enums.AircraftSize;
import com.binaryfountain.airtraffic.enums.AircraftType;
import com.binaryfountain.airtraffic.service.AirTrafficQueueService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test class for AirTrafficController class
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AirTrafficControllerTests {

	@Inject
	private MockMvc mvc;

	@Inject
	private ObjectMapper mapper;

	@MockBean
	private AirTrafficQueueService airTrafficQueueService;

	/**
	 * Tests the enqueue endpoint.
	 * 
	 * @throws Exception
	 */
	@Test
	public void enqueueTest() throws Exception {

		// Given
		Flight flight = Flight.builder().flightId("AA-123").size(AircraftSize.SMALL).type(AircraftType.VIP).build();
		Mockito.when(airTrafficQueueService.enqueue(Mockito.any(Flight.class))).thenReturn(flight);

		// When
		final ResultActions response = mvc.perform(
				post("/enqueue").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(flight)));

		// Then
		response.andExpect(status().isOk());
		verify(airTrafficQueueService, times(1)).enqueue(Mockito.any());

	}

	/**
	 * Tests enqueue with an invalid size
	 * 
	 * @throws Exception
	 */
	@Test
	public void enqueueInvalidSizeTest() throws Exception {

		// Given
		Flight flight = Flight.builder().flightId("AA-123").size(AircraftSize.SMALL).type(AircraftType.VIP).build();
		Mockito.when(airTrafficQueueService.enqueue(Mockito.any(Flight.class))).thenReturn(flight);
		// When
		final ResultActions response = mvc.perform(
				post("/enqueue").contentType(MediaType.APPLICATION_JSON).content("{\"flightId\":\"AA-123\",\"size\":\"MEDIUM\",\"type\":\"VIP\",\"queuedTime\":\"2018-08-05T20:57:18.835\"}"));

		// Then
		response.andExpect(status().is4xxClientError());
		verify(airTrafficQueueService, times(0)).enqueue(Mockito.any());

	}

	/**
	 * Tests enqueue endpoint with an invalid type
	 * @throws Exception
	 */
	@Test
	public void enqueueInvalidTypeTest() throws Exception {

		// Given
		Flight flight = Flight.builder().flightId("AA-123").size(AircraftSize.SMALL).type(AircraftType.VIP).build();
		Mockito.when(airTrafficQueueService.enqueue(Mockito.any(Flight.class))).thenReturn(flight);
		// When
		final ResultActions response = mvc.perform(
				post("/enqueue").contentType(MediaType.APPLICATION_JSON).content("{\"flightId\":\"AA-123\",\"size\":\"SMALL\",\"type\":\"MILITARY\",\"queuedTime\":\"2018-08-05T20:57:18.835\"}"));

		// Then
		response.andExpect(status().is4xxClientError());
		verify(airTrafficQueueService, times(0)).enqueue(Mockito.any());

	}

	/**
	 * Tests the dequeue end point
	 * 
	 * @throws Exception
	 */
	@Test
	public void dequeueTest() throws Exception {

		// Given
		Flight flight = Flight.builder().flightId("AA-123").size(AircraftSize.SMALL).type(AircraftType.VIP).build();
		Mockito.when(airTrafficQueueService.dequeue()).thenReturn(flight);

		// When
		final ResultActions response = mvc.perform(get("/dequeue"));

		// Then
		response.andExpect(status().isOk());
		verify(airTrafficQueueService, times(1)).dequeue();

	}

	/**
	 * Tests the Dequeue end point when nothing in the queue
	 * 
	 * @throws Exception
	 */
	@Test
	public void dequeueEmptyQueueTest() throws Exception {

		// Given
		Mockito.when(airTrafficQueueService.dequeue()).thenReturn(null);

		// When
		final ResultActions response = mvc.perform(get("/dequeue"));

		// Then
		response.andExpect(status().isOk());
		verify(airTrafficQueueService, times(1)).dequeue();

	}

	/**
	 * Test boot endpoint
	 * 
	 * @throws Exception
	 */
	@Test
	public void bootTest() throws Exception {

		// Given
		Mockito.doNothing().when(airTrafficQueueService).initialize();

		// When
		final ResultActions response = mvc.perform(get("/boot"));

		// Then
		response.andExpect(status().isOk());
		verify(airTrafficQueueService, times(1)).initialize();
	}
}
