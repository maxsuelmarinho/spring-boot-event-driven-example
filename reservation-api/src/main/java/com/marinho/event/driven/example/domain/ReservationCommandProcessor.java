package com.marinho.event.driven.example.domain;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;

public class ReservationCommandProcessor {
    private final ReservationEventStore eventStore;

    public ReservationCommandProcessor(ReservationEventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void processCommand(ReservationCommand command, DateTime now)
        throws ReservationException {

        DateId dateId = command.getDateId();
        List<ReservationEvent> events = eventStore.getEvents(dateId);
        DateAggregate dateAggregate = new DateAggregate(dateId);
        dateAggregate.apply(events);

        List<ReservationEvent> newEvents = handle(command, dateAggregate, now);

        eventStore.persistEvents(newEvents);
    }

    private List<ReservationEvent> handle(ReservationCommand command, DateAggregate dateAggregate, DateTime now)
            throws ReservationException {

        ReservationCommandType commandType = command.getCommandData().getCommandType();
        List<ReservationEvent> newEvents = new LinkedList<>();

        if (command.getDateId().isEarlierThan(now)) {
            throw new ReservationException("This date is in the past");
        }

        if (ReservationCommandType.CREATE.equals(commandType)) {
            CreateReservationData data = (CreateReservationData) command.getCommandData();

            if (data.getSeats() > dateAggregate.getAvailableSeats()) {
                throw new ReservationException("Not enough seats available");
            }

            if (dateAggregate.reservationExists(data.getEmail())) {
                throw new ReservationException("You already have a reservation");
            }

            ReservationEventData eventData =
                    new ReservationCreatedData(data.getName(), data.getEmail(), data.getSeats());

            long newSequenceNumber = dateAggregate.getSequenceNumber() + 1;
            ReservationEvent event = new ReservationEvent(command.getDateId(), newSequenceNumber, now, eventData);

            newEvents.add(event);
            return newEvents;
        } else if (ReservationCommandType.CANCEL.equals(commandType)) {
            CancelReservationData data = (CancelReservationData) command.getCommandData();

            if (!dateAggregate.reservationExists(data.getEmail())) {
                throw new ReservationException("Reservation does not exist");
            }

            ReservationEventData eventData =
                    new ReservationCancelledData(data.getEmail());
            long newSequenceNumber = dateAggregate.getSequenceNumber() + 1;
            ReservationEvent event =
                    new ReservationEvent(command.getDateId(), newSequenceNumber, now, eventData);
            newEvents.add(event);
            return newEvents;
        }

        throw new RuntimeException("Unsupported command type: " + command.getCommandData().getCommandType());
    }
}
