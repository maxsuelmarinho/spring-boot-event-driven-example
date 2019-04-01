package com.marinho.event.driven.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RawEventsRepository extends MongoRepository<RawEvent, String> {

    List<RawEvent> findByDate(String date);
}
