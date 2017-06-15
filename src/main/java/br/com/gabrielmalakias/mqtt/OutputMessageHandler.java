package br.com.gabrielmalakias.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class OutputMessageHandler {
    private static String CLIENT_IDENTIFIER = "BITFROST_OUTPUT_CHANNEL";
    private static String DEFAULT_TOPIC = "sensor/system";
    private final MqttPahoClientFactory clientFactory;

    @Autowired
    public OutputMessageHandler(MqttPahoClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    public MessageHandler create() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(CLIENT_IDENTIFIER, clientFactory);
        messageHandler.setAsync(Config.ASYNC);
        messageHandler.setDefaultTopic(DEFAULT_TOPIC);
        return messageHandler;
    }
}
