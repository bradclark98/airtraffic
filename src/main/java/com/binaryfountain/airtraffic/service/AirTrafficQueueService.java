package com.binaryfountain.airtraffic.service;

import com.binaryfountain.airtraffic.domain.Flight;

/**
 * Service to air traffic control system
 */
public interface AirTrafficQueueService {

    /**
     * Enqueue from air traffic priority queue
     *
     * @param Flight being added to queued
     * @return Queued flight
     */
    public Flight enqueue(Flight flight);

    /**
     * Dequeue from air traffic priority queue
     *
     * @return flight being dequeued 
     */
    public Flight dequeue();

    /**
     * Initialize the air traffic priority queue
     */
    public void initialize();
    
}
