package com.helena128.paymentmanager.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOffset;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentMessageConsumer {

    private final KafkaReceiver<String, String> kafkaReceiver;
    private final MessageProcessor processor;

    @PostConstruct

    public Disposable consumeMessage() {
        return kafkaReceiver.receive()
                .flatMap(record -> processor.processMessage(record.value())
                        .doOnSuccess(res -> {
                            ReceiverOffset offset = record.receiverOffset();
                            log.info("Successfully handled message with id={}", record.key());
                            offset.acknowledge();
                        })
                        .doOnError(ex -> log.error("Exception {} happened while processing msg with id = {}", ex.getMessage(), record.key()))
                )
                .subscribe();
    }
}
