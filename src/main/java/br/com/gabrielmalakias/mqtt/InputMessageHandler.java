package br.com.gabrielmalakias.mqtt;

import br.com.gabrielmalakias.converter.MqttMessageToString;
import br.com.gabrielmalakias.serial.core.Bridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import static br.com.gabrielmalakias.util.Optional.optional;

public class InputMessageHandler implements MessageHandler {
    @Autowired
    private MqttMessageToString converter;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        sendToSerial(new MqttMessage(message));
    }

    private boolean sendToSerial(MqttMessage message) {
        return optional(toSerial(message))
                .map(Object::toString)
                .map(this::send)
                .orElse(false);
    }

    private boolean send(String message) {
        System.out.println("Sending message: " + message);
        return Bridge.getInstance()
                .map(bridge -> bridge.writeOnOutputStream(message))
                .orElse(false);
    }

    private String toSerial(MqttMessage mqttMessage) {
        return converter.convert(mqttMessage);
    }
}
