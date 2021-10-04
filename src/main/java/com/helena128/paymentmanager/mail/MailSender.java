package com.helena128.paymentmanager.mail;

import com.hubspot.smtp.client.SmtpClientResponse;
import com.hubspot.smtp.client.SmtpSessionConfig;
import com.hubspot.smtp.client.SmtpSessionFactory;
import com.hubspot.smtp.messages.MessageContent;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.smtp.DefaultSmtpRequest;
import io.netty.handler.codec.smtp.SmtpCommand;
import io.netty.handler.codec.smtp.SmtpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.smtp.SmtpCommand.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailSender {

    private final SmtpSessionFactory smtpSessionFactory;
    private final SmtpSessionConfig smtpSessionConfig;
    private final MailPropertiesConfiguration mailProperties;

    public Mono<Void> sendEmail(final String message, final String recipient) {
        return Mono.fromFuture(smtpSessionFactory.connect(smtpSessionConfig))
                .flatMap(connection -> doInSession(connection, req(EHLO, "smtp.gmail.com")))
                .flatMap(connection -> Mono.fromFuture(connection.getSession().startTls()))
                .flatMap(connection -> Mono.fromFuture(connection.getSession().authLogin(mailProperties.getUsername(), mailProperties.getPassword())))
                .flatMap(connection -> doInSession(connection, req(MAIL, "FROM:<" + mailProperties.getUsername() + ">")))
                .flatMap(connection -> doInSession(connection, req(RCPT, "TO:<" + recipient + ">")))
                .flatMap(connection -> doInSession(connection, req(DATA)))
                .map(connection -> connection.getSession()
                        .send(MessageContent.of(Unpooled.wrappedBuffer(message.getBytes(StandardCharsets.UTF_8)))))
                .flatMap(Mono::fromFuture)
                .flatMap(connection -> doInSession(connection, req(QUIT)))
                .flatMap(connection -> Mono.fromFuture(connection.getSession().close()))
                .doOnError(error -> log.error("Error happened, {}", error.getMessage()));
    }

    private static Mono<SmtpClientResponse> doInSession(SmtpClientResponse connection, SmtpRequest request) {
        return Mono.fromFuture(connection.getSession().send(request));
    }

    private static SmtpRequest req(SmtpCommand command, CharSequence... arguments) {
        return new DefaultSmtpRequest(command, arguments);
    }
}
