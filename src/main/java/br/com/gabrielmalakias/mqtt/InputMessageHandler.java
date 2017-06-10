package br.com.gabrielmalakias.mqtt;

import br.com.gabrielmalakias.serial.command.Write;
import br.com.gabrielmalakias.serial.core.Bridge;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.io.IOException;
import java.io.OutputStream;

import static br.com.gabrielmalakias.util.Optional.optional;

public class InputMessageHandler implements MessageHandler {
    private static String TOPIC_HEADER = "mqtt_topic";

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        System.out.println("[USGARD] Message: " + message.getPayload());
        sendToSerial(message);
    }

    private boolean sendToSerial(Message<?> message) {
        String payload = (String) message.getPayload();

        return optional(message.getHeaders().get(TOPIC_HEADER))
                .map(Object::toString)
                .map(topic -> toSerial(topic, payload))
                .map(this::send)
                .orElse(false);
    }

    private boolean send(String message) {
        //new Thread(new Write(getOutputStream(), message)).start();
        return true;
    }

    private String toSerial(String name, String value) {
        StringBuilder serialBuilder = new StringBuilder();
        serialBuilder.append("name:");
        serialBuilder.append(name);
        serialBuilder.append(";value:");
        serialBuilder.append(value);
        return serialBuilder.toString();
    }
}
