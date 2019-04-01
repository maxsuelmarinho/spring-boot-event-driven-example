package com.marinho.event.driven.example.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CancelReservationRequestDto {
    private final String email;

    @JsonCreator
    public CancelReservationRequestDto(@JsonProperty("email") String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
