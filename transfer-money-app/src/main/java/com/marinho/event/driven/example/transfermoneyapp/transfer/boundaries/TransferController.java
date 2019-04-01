package com.marinho.event.driven.example.transfermoneyapp.transfer.boundaries;

import com.marinho.event.driven.example.transfermoneyapp.transfer.domain.TransferMoneyDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {

    private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

    private final MessageChannel moneyTransferredChannel;

    public TransferController(MessageChannel moneyTransferredChannel) {
        this.moneyTransferredChannel = moneyTransferredChannel;
    }

    @PostMapping("/transfer")
    public void doTransfer(@RequestBody TransferMoneyDetails transferMoneyDetails) {
        logger.info("Transferring money with details: " + transferMoneyDetails);
        Message<TransferMoneyDetails> moneyTransferredEvent =
                MessageBuilder.withPayload(transferMoneyDetails).build();

        this.moneyTransferredChannel.send(moneyTransferredEvent);
    }
}
