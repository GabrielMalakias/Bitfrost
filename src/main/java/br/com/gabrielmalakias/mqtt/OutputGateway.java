package br.com.gabrielmalakias.mqtt;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;

@Configuration
public class OutputGateway {
    @MessagingGateway(defaultRequestChannel = "outputMessageChannel")
    public interface Gateway {
        void sendToMqtt(String data);
    }
}
