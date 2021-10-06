package com.helena128.paymentmanager.notification.kafka;

import com.helena128.paymentmanager.notification.MessageProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOffset;

@Component
@RequiredArgsConstructor
@Slf4j
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PaymentMessageConsumer implements CommandLineRunner {

    private final KafkaReceiver<String, String> kafkaReceiver;
    private final MessageProcessor processor;

    @Override
    public void run(String... args) throws Exception {
        consumeMessage();
    }

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
