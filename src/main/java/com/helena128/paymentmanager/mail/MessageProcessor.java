package com.helena128.paymentmanager.mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helena128.paymentmanager.model.PaymentMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

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
                .map(this::buildMessage)
                .flatMap(msg -> mailSender.sendEmail(msg, "hellogbel@gmail.com")); // TODO
    }

    private String buildMessage(final PaymentMessage msg) {
        /*Map<String, String> propertyMap = Map.of("{sender}", properties.getUsername(),
                "{recipient}", msg.getCardHolderInfo().getEmail(),
        "{orderId}", msg.getId(),
                "{sum}", msg.getSum().toString());
        String result = TEMPLATE;
        for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;*/
        return TEMPLATE;
    }

    @SneakyThrows
    private PaymentMessage readMessage(final String msg) {
        return objectMapper.readValue(msg, PaymentMessage.class);
    }
}
