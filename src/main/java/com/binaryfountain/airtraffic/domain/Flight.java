package com.binaryfountain.airtraffic.domain;

import java.time.LocalDateTime;

import com.binaryfountain.airtraffic.enums.AircraftSize;
import com.binaryfountain.airtraffic.enums.AircraftType;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * The flight data object
 */
@Data
@Builder
public class Flight implements Comparable<Flight> {

	@NonNull
	private String flightId;

	@NonNull
	private AircraftSize size;

	@NonNull
	private AircraftType type;

	@Builder.Default
	private LocalDateTime queuedTime = LocalDateTime.now();

	/**
	 * The compareTo defines the priority in the priority queue, order of precedence
	 * is type, size and queued timestamp.
	 */
	@Override
	public int compareTo(Flight flight) {

		// If flight type aren't equal prioritize on type
		if (this.getType() != flight.getType()) {
			return flight.getType().compareTo(this.getType());
		} else if (this.getSize() != flight.getSize()) {
			// If flight size isn't equal prioritize on size
			return flight.getSize().compareTo(this.getSize());
		} else {
			// prioritize the oldest queued flight
			return this.getQueuedTime().compareTo(flight.getQueuedTime());
		}
	}

}
