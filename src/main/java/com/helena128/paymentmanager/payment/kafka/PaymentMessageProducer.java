package com.helena128.paymentmanager.payment.kafka;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.helena128.paymentmanager.model.PaymentMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentMessageProducer {

    private final KafkaSender<String, String> paymentMessageSender;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public Mono<String> sendPaymentMessage(final PaymentMessage paymentMessage) {
        return paymentMessageSender.send(
                Mono.just(paymentMessage)
                        .map(this::buildProducerRecord)
                        .map(rec -> SenderRecord.create(rec, paymentMessage.getId())))
                .collectList()
                .map(list -> paymentMessage.getId())
                .doOnNext(msgId -> log.info("Sent message with id={}", msgId)); // TODO: add retry
    }

    @SneakyThrows
    private ProducerRecord<String, String> buildProducerRecord(final PaymentMessage paymentMessage) {
        return new ProducerRecord<String, String>("payments", paymentMessage.getId(), // TODO: move opic name to configuration
                objectMapper.writeValueAsString(paymentMessage));
    }

}
