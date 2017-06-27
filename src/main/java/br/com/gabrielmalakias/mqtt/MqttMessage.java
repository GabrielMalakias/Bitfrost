package br.com.gabrielmalakias.mqtt;

import org.springframework.messaging.Message;

import static br.com.gabrielmalakias.util.Optional.optional;
import static br.com.gabrielmalakias.util.String.EMPTY;

public class MqttMessage {
    private static String TOPIC_HEADER = "mqtt_topic";

    private final Message message;

    public MqttMessage(Message message) {
        this.message = message;
    }

    public String getPayload() {
        return optional(message.getPayload())
                .map(Object::toString)
                .orElse(EMPTY);
    }

    public String getTopic() {
        return optional(message.getHeaders().get(TOPIC_HEADER))
                .map(Object::toString)
                .orElse(EMPTY);
    }

    public String getIdentifier() {
        return optional(getInfoFromTopic(1))
                .map(Object::toString)
                .orElse(EMPTY);
    }

    private String getInfoFromTopic(int index) {
        return getTopic().split("\\/")[index];
    }

    public String getType() {
        return optional(getInfoFromTopic(0))
                .map(Object::toString)
                .orElse(EMPTY);
    }
}
