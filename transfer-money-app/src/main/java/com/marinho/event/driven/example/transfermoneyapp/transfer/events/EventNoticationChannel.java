package com.marinho.event.driven.example.transfermoneyapp.transfer.events;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface EventNoticationChannel {

    @Output
    MessageChannel moneyTransferredChannel();
}
