package br.com.gabrielmalakias.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class OutputMessageHandler {
    private static String CLIENT_IDENTIFIER = "BITFROST_OUTPUT_CHANNEL";
    private static String DEFAULT_TOPIC = "sensor/system";

    private final Factory factory;

    @Autowired
    public OutputMessageHandler(Factory factory) {
        this.factory = factory;
    }

    public MessageHandler create() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(CLIENT_IDENTIFIER, factory.mqttPahoClientFactory());
        messageHandler.setAsync(Config.ASYNC);
        messageHandler.setDefaultTopic(DEFAULT_TOPIC);
        return messageHandler;
    }
}
