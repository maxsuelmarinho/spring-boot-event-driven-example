package com.marinho.event.driven.example.notificationsapp.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.marinho.event.driven.example.notificationsapp.transfer.domain.TransferMoneyDetails;
import com.marinho.event.driven.example.notificationsapp.transfer.events.EventNotificationChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;

@EnableBinding(EventNotificationChannel.class)
@Configuration
public class NotificationsConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(NotificationsConfiguration.class);

    @Bean
    IntegrationFlow integrationFlow(EventNotificationChannel eventNotificationChannel) {
        return IntegrationFlows.from(eventNotificationChannel.subscriptionOnMoneyTransferredChannel())
                .handle(TransferMoneyDetails.class, new GenericHandler<TransferMoneyDetails>() {
                    @Override
                    public Object handle(TransferMoneyDetails payload, MessageHeaders headers) {
                        logger.info("Message retrieved:");
                        logger.info(payload.toString());

                        logger.info("Notifying by preferred channels to customer with id: " + payload.getCustomerId());
                        return null;
                    }
                }).get();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return mapper;
    }
}
