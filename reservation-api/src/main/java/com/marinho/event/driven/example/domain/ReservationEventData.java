package com.marinho.event.driven.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ReservationEventData {

    private final ReservationEventType eventType;

    public ReservationEventData(ReservationEventType eventType) {
        this.eventType = eventType;
    }

    @JsonIgnore
    public ReservationEventType getEventType() {
        return eventType;
    }
}
