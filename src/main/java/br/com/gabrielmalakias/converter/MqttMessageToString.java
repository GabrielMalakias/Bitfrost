package br.com.gabrielmalakias.converter;

import br.com.gabrielmalakias.mqtt.MqttMessage;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static br.com.gabrielmalakias.util.Optional.optional;
import static br.com.gabrielmalakias.util.String.EMPTY;

@Component
public class MqttMessageToString implements Converter<MqttMessage, String> {
    private StringBuilder builder;

    @Override
    public String convert(MqttMessage source) {
        builder = new StringBuilder();

        return optional(source)
                .map(o -> (MqttMessage) o )
                .map(this::appendIdentifier)
                .map(this::appendPayload)
                .map(this::appendType)
                .map(this::appendEnd)
                .map(Object::toString)
                .orElse(EMPTY);
    }

    public MqttMessage appendIdentifier(MqttMessage mqttMessage) {
        builder.append("i:");
        builder.append(mqttMessage.getIdentifier());
        return mqttMessage;
    }

    public MqttMessage appendPayload(MqttMessage mqttMessage) {
        builder.append(";v:");
        builder.append(mqttMessage.getPayload());
        return mqttMessage;
    }

    public MqttMessage appendType(MqttMessage mqttMessage) {
        builder.append(";t:");
        builder.append(mqttMessage.getType());
        return mqttMessage;
    }

    public StringBuilder appendEnd(MqttMessage mqttMessage) {
        builder.append("%");
        return builder;
    }
}
