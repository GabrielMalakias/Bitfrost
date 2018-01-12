package br.com.gabrielmalakias.mqtt;

import br.com.gabrielmalakias.converter.StringToOutputMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Publisher {
    private final Client client;

    @Autowired
    public Publisher(Client client) {
        this.client = client;
    }

    public boolean send(String message) {
        return Optional.ofNullable(message)
                .map(this::toOutputMessage)
                .map(this::publish)
                .get();
    }

    private OutputMessage toOutputMessage(String message) {
        return new StringToOutputMessage().convert(message);
    }

    private boolean publish(OutputMessage outputMessage) {
        String topic = outputMessage.getType() + "/" + outputMessage.getIdentifier();
        return client.publish(topic, outputMessage.getValue());
    }
}
