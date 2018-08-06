package com.binaryfountain.airtraffic.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.binaryfountain.airtraffic.domain.Flight;
import com.binaryfountain.airtraffic.dto.FlightDTO;
import com.binaryfountain.airtraffic.service.AirTrafficQueueService;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Air Traffic Controller
 */
@RestController
@Slf4j
@AllArgsConstructor
public class AirTrafficController {

	private final AirTrafficQueueService airTrafficQueueService;

	/**
	 * Rest endpoint that receives new flights
	 *
	 * @param flight to be queued
	 */
	@ApiOperation(value = "Add flight to Air Traffic Queue")
	@PostMapping(path = "/enqueue")
	public ResponseEntity<FlightDTO> enqueue(@Valid @RequestBody FlightDTO flightParam) {
		// TODO Define MapStructs for Flight and Flight DTO
		Flight flight = Flight.builder().flightId(flightParam.getFlightId()).size(flightParam.getSize())
				.type(flightParam.getType()).build();
		log.trace("Reached enqueue() at AirTrafficController for flight {}", flight);
		Flight responseFlight = airTrafficQueueService.enqueue(flight);
		return new ResponseEntity<>(
				FlightDTO.builder().flightId(responseFlight.getFlightId()).size(responseFlight.getSize())
						.type(responseFlight.getType()).queuedTime(responseFlight.getQueuedTime()).build(),
				HttpStatus.OK);
	}

	/**
	 * Rest endpoint that processes next flight
	 *
	 * @return flight to dequeue
	 */
	@ApiOperation(value = "Process next flight in Queue")
	@GetMapping(value = "/dequeue")
	public ResponseEntity<Flight> dequeue() {
		log.trace("Reached dequeue() at AirTrafficController");
		Flight flight = airTrafficQueueService.dequeue();
		return new ResponseEntity<>(flight, HttpStatus.OK);
	}

	/**
	 * Rest endpoint that boots the flight queue
	 */
	@ApiOperation(value = "Restart air traffic control")
	@GetMapping(value = "/boot")
	public ResponseEntity<String> boot() {
		log.trace("Reached boot() at AirTrafficController");
		airTrafficQueueService.initialize();
		return new ResponseEntity<>("System Rebooted", HttpStatus.OK);
	}

}
