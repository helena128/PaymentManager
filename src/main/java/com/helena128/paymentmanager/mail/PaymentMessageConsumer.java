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
                .flatMap(record -> processor.processMessage(record.value()).map(res -> record))
                .subscribe(
                        record -> {
                            ReceiverOffset offset = record.receiverOffset();
                            // todo: handle message
                            log.info("Received message with id={}", record.key());
                            offset.acknowledge();
                        }
                );
    }
}
