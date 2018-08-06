package com.binaryfountain.airtraffic.dto;

import java.time.LocalDateTime;

import com.binaryfountain.airtraffic.enums.AircraftSize;
import com.binaryfountain.airtraffic.enums.AircraftType;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * The flight data transfer object
 */
@Data
@Builder
public class FlightDTO {

	@NonNull private String flightId;

    @NonNull private AircraftSize size;
    
    @NonNull private AircraftType type;
    
   private LocalDateTime queuedTime;

}
