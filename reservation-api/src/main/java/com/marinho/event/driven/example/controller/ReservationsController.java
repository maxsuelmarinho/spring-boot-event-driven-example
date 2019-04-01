package com.marinho.event.driven.example.controller;

import com.marinho.event.driven.example.domain.*;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationsController {

    private final ReservationEventStore eventStore;

    public ReservationsController(ReservationEventStore eventStore) {
        this.eventStore = eventStore;
    }

    @RequestMapping(value = "reservation/{dateId}", method = RequestMethod.GET)
    public AvailabilityDto getAvailability(@PathVariable("dateId") String dateIdStr) {
        DateId dateId = DateId.parse(dateIdStr);
        DateTime today = DateTime.now();

        if (dateId.isEarlierThan(today)) {
            return new AvailabilityDto(dateId, 0);
        } else {
            List<ReservationEvent> events = eventStore.getEvents(dateId);
            DateAggregate date = new DateAggregate(dateId);
            date.apply(events);
            return new AvailabilityDto(dateId, date.getAvailableSeats());
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "reservation/{dateId}")
    public ResponseEntity<String> createReservation(
            @PathVariable("dateId") String dateIdStr,
            @RequestBody CreateReservationRequestDto requestDto) {
        ReservationCommandData commandData = new CreateReservationData(
                requestDto.getEmail(), requestDto.getName(), requestDto.getSeats());

        return handleCommand(dateIdStr, commandData);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "reservation/{dateId}")
    public ResponseEntity<String> cancelReservation(
            @PathVariable("dateId") String dateIdStr,
            @RequestBody CancelReservationRequestDto requestDto) {
        ReservationCommandData commandData =
                new CancelReservationData(requestDto.getEmail());
        return handleCommand(dateIdStr, commandData);
    }

    private ResponseEntity<String> handleCommand(String dateIdStr, ReservationCommandData commandData) {
        DateId dateId = DateId.parse(dateIdStr);

        ReservationCommand command = new ReservationCommand(dateId, DateTime.now(), commandData);

        ReservationCommandProcessor processor =
                new ReservationCommandProcessor(eventStore);

        try {
            processor.processCommand(command, DateTime.now());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (ReservationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
