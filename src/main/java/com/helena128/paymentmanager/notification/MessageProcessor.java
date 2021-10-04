package com.helena128.paymentmanager.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helena128.paymentmanager.notification.config.MailPropertiesConfiguration;
import com.helena128.paymentmanager.model.CardHolderInfo;
import com.helena128.paymentmanager.model.PaymentMessage;
import com.helena128.paymentmanager.notification.mail.MailSender;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageProcessor {

    private final MailSender mailSender;
    private final ObjectMapper objectMapper;
    private final MailPropertiesConfiguration properties;

    private static final String TEMPLATE = "From: <{sender}\r\n" +
            "To: <{recipient}>\r\n" +
            "Subject: Payment processed\r\n\r\n" +
            "Your order {orderId} has been processed, sum = {sum} ";

    public Mono<Void> processMessage(final String message) {
        return Mono.just(message)
                .map(this::readMessage)
                .filter(this::isValidMessage)
                .map(msg -> Tuples.of(msg, buildMessage(msg)))
                .flatMap(msg -> mailSender.sendEmail(msg.getT2(), msg.getT1().getCardHolderInfo().getEmail()));
    }

    private boolean isValidMessage(final PaymentMessage paymentMessage) {
        return Objects.nonNull(paymentMessage.getId()) && Objects.nonNull(paymentMessage.getSum())
                && Objects.nonNull(paymentMessage.getCardHolderInfo()) && Objects.nonNull(paymentMessage.getCardHolderInfo().getEmail());
    }

    private String buildMessage(final PaymentMessage msg) {
        Map<String, String> propertyMap = Map.of("{sender}", properties.getUsername(),
                "{recipient}", Optional.ofNullable(msg.getCardHolderInfo()).map(CardHolderInfo::getEmail).orElse("username"),
                "{orderId}", msg.getId(),
                "{sum}", msg.getSum().toString());
        String result = TEMPLATE;
        for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }

    @SneakyThrows
    private PaymentMessage readMessage(final String msg) {
        return objectMapper.readValue(msg, PaymentMessage.class);
    }
}
