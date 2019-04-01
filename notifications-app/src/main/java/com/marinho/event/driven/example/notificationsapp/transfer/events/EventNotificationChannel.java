package com.marinho.event.driven.example.notificationsapp.transfer.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface EventNotificationChannel {

    @Input
    SubscribableChannel subscriptionOnMoneyTransferredChannel();
}
