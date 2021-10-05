package com.helena128.paymentmanager.notification.kafka;

import com.helena128.paymentmanager.config.KafkaPropertiesConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaymentConsumerConfiguration {

    @Bean
    public KafkaReceiver<String, String> kafkaReceiver(final KafkaPropertiesConfig kafkaProps) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProps.getBootstrapServers());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, kafkaProps.getConsumerClientId());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProps.getConsumerGroup());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        ReceiverOptions<String, String> receiverOptions = ReceiverOptions.create(props);
        receiverOptions.subscription(Collections.singleton(kafkaProps.getTopic()));
        return KafkaReceiver.create(receiverOptions);
    }

}
