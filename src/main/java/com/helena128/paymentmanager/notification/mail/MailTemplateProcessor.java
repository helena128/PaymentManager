package com.helena128.paymentmanager.notification.mail;

import com.helena128.paymentmanager.model.CardHolderInfo;
import com.helena128.paymentmanager.model.PaymentMessage;
import com.helena128.paymentmanager.notification.config.MailPropertiesConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailTemplateProcessor {
    
    private static final String TEMPLATE = """
            From: <{sender}
            To: <{recipientEmail}>
            Subject: Payment processed 
            Dear {recipientName},
            Your order {orderId} has been processed.
            Details of transaction: sum = {sum}, card used = {cardNumber}, date = {date} (UTC)
            """;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.from(ZoneOffset.UTC));

    private final MailPropertiesConfiguration properties;

    public String buildMailBody(final PaymentMessage msg) {
        Map<String, String> propertyMap = Map.of("{sender}", properties.getUsername(),
                "{recipientName}", Optional.ofNullable(msg.getCardHolderInfo()).map(CardHolderInfo::getName).orElse("user"),
                "{recipientEmail}", Optional.ofNullable(msg.getCardHolderInfo()).map(CardHolderInfo::getEmail).orElse("user"),
                "{orderId}", msg.getId(),
                "{sum}", msg.getSum().setScale(2, BigDecimal.ROUND_HALF_UP).toString(),
                "{cardNumber}", msg.getCardNumber(),
                "{date}", DATE_TIME_FORMATTER.format(msg.getCreatedDateTime()));
        String result = TEMPLATE;
        for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
