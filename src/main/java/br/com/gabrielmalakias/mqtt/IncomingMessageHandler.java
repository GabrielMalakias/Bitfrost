package br.com.gabrielmalakias.mqtt;

import br.com.gabrielmalakias.serial.OutputFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import static br.com.gabrielmalakias.util.Optional.optional;

@Component
public class IncomingMessageHandler implements MessageHandler {

    private final OutputFlow outputFlow;
    private final ConversionService converter;

    @Autowired
    public IncomingMessageHandler(OutputFlow outputFlow, ConversionService converter) {
        this.converter = converter;
        this.outputFlow = outputFlow;
    }

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
        return outputFlow.write(message);
    }

    private String toSerial(MqttMessage mqttMessage) {
        return converter.convert(mqttMessage, String.class);
    }
}
