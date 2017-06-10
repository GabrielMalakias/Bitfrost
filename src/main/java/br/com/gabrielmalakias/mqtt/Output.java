package br.com.gabrielmalakias.mqtt;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class Output {
    private static String CLIENT_IDENTIFIER = "BITFROST_OUTPUT_CHANNEL";

    private final OutputMessageHandler messageHandler;

    public Output(OutputMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Bean
    @ServiceActivator(inputChannel = "outputMessageChannel")
    public MessageHandler outbound() {
        return messageHandler.create();
    }

    @Bean
    public MessageChannel outputMessageChannel() {
        return new DirectChannel();
    }
}
