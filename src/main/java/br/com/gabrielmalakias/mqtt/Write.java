package br.com.gabrielmalakias.mqtt;

import br.com.gabrielmalakias.converter.StringToOutputMessage;

import java.util.Optional;

public class Write {
    private final Client client;

    public Write(Client client) {
        this.client = client;
    }

    public void run(String message) {
        Optional.ofNullable(message)
                .map(this::toOutputMessage)
                .map(this::publish);

    }

    private OutputMessage toOutputMessage(String message) {
        return new StringToOutputMessage().convert(message);
    }

    private OutputMessage publish(OutputMessage outputMessage) {
        String topic = outputMessage.getType() + "/" + outputMessage.getIdentifier();
        client.publish(topic, outputMessage.getValue());
        return outputMessage;
    }

}
