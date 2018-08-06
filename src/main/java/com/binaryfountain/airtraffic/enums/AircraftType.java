package com.binaryfountain.airtraffic.enums;

/**
 * Aircraft Type enumerations, the order of the enumerations is important since the ordinal value is used by the PriorityQueue 
 */
public enum AircraftType {

    /**
     * Cargo Aircraft
     */
    CARGO,
    /**
     * Passenger Aircraft
     */
    PASSENGER,
    /**
     * VIP Aircraft
     */
    VIP,
    /**
     * Emergency Aircraft
     */
    EMERGENCY;

}
