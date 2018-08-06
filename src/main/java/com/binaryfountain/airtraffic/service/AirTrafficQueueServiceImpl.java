package com.binaryfountain.airtraffic.service;

import java.util.concurrent.PriorityBlockingQueue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.binaryfountain.airtraffic.domain.Flight;

/**
 * Service that handles air traffic through a priority queue
 */
@Slf4j
@Service
public class AirTrafficQueueServiceImpl implements AirTrafficQueueService {

	// Threadsafe priority queue
	private static PriorityBlockingQueue<Flight> flightQueue = new PriorityBlockingQueue<>();

	@Override
	public Flight enqueue(Flight flight) {
		log.trace("Adding " + flight + " to flight queue");
		flightQueue.add(flight);
		return flight;
	}

	@Override
	public Flight dequeue() {
		Flight flight = flightQueue.poll();
		log.trace("Removing " + flight + " from flight queue");
		return flight;
	}

	@Override
	public void initialize() {
		flightQueue = new PriorityBlockingQueue<>();
	}
}
