package com.helena128.paymentmanager.notification.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hubspot.smtp.client.SmtpSessionConfig;
import com.hubspot.smtp.client.SmtpSessionFactory;
import com.hubspot.smtp.client.SmtpSessionFactoryConfig;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
public class MailConfiguration {

    @Bean
    public SmtpSessionFactory smtpSessionFactory() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("niosmtpclient-%d").build();
        final SmtpSessionFactoryConfig config = SmtpSessionFactoryConfig.builder()
                .eventLoopGroup(new NioEventLoopGroup(4, threadFactory))
                .executor(Executors.newCachedThreadPool(threadFactory))
                .build();
        return new SmtpSessionFactory(config);
    }

    @Bean
    public SmtpSessionConfig smtpSessionConfig() {
        return SmtpSessionConfig.builder().remoteAddress(InetSocketAddress.createUnresolved("smtp.gmail.com", 587)).build();
    }
}
