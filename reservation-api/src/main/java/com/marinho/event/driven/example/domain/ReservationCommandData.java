package com.marinho.event.driven.example.domain;

public abstract class ReservationCommandData {
    private final ReservationCommandType commandType;

    protected ReservationCommandData(ReservationCommandType commandType) {
        this.commandType = commandType;
    }

    public ReservationCommandType getCommandType() {
        return commandType;
    }
}
