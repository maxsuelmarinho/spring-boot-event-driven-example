package com.marinho.event.driven.example.controller;

import com.marinho.event.driven.example.domain.DateId;

public class AvailabilityDto {

    private final DateId dateId;
    private final int availableSeats;

    public AvailabilityDto(DateId dateId, int availableSeats) {
        this.dateId = dateId;
        this.availableSeats = availableSeats;
    }

    public String getDate() {
        return dateId.toString();
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
}
