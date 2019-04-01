package com.marinho.event.driven.example.domain;

public class CancelReservationData extends ReservationCommandData {
    private final String email;

    public CancelReservationData(String email) {
        super(ReservationCommandType.CANCEL);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
