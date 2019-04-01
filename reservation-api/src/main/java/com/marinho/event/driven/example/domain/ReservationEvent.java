package com.marinho.event.driven.example.domain;

import org.joda.time.DateTime;

public class ReservationEvent {
    private final DateId dateId;
    private final long sequenceNumber;
    private final DateTime createOn;
    private final ReservationEventData eventData;

    public ReservationEvent(DateId dateId, long sequenceNumber, DateTime createOn, ReservationEventData eventData) {
        this.dateId = dateId;
        this.sequenceNumber = sequenceNumber;
        this.createOn = createOn;
        this.eventData = eventData;
    }

    public DateId getDateId() {
        return dateId;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public DateTime getCreateOn() {
        return createOn;
    }

    public ReservationEventData getEventData() {
        return eventData;
    }
}
