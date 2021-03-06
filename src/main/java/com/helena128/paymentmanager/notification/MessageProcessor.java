package com.helena128.paymentmanager.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helena128.paymentmanager.model.PaymentMessage;
import com.helena128.paymentmanager.notification.mail.MailSender;
import com.helena128.paymentmanager.notification.mail.MailTemplateProcessor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageProcessor {

    private final MailSender mailSender;
    private final ObjectMapper objectMapper;
    private final MailTemplateProcessor templateProcessor;

    public Mono<Void> processMessage(final String message) {
        return Mono.just(message)
                .map(this::readMessage)
                .filter(this::isValidMessage)
                .map(msg -> Tuples.of(msg, templateProcessor.buildMailBody(msg)))
                .flatMap(msg -> mailSender.sendEmail(msg.getT2(), msg.getT1().getCardHolderInfo().getEmail()));
    }

    private boolean isValidMessage(final PaymentMessage paymentMessage) {
        return Objects.nonNull(paymentMessage.getId()) && Objects.nonNull(paymentMessage.getSum())
                && Objects.nonNull(paymentMessage.getCardHolderInfo()) && Objects.nonNull(paymentMessage.getCardHolderInfo().getEmail());
    }

    @SneakyThrows
    private PaymentMessage readMessage(final String msg) {
        return objectMapper.readValue(msg, PaymentMessage.class);
    }
}
