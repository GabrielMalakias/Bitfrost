package br.com.gabrielmalakias.converter;

import br.com.gabrielmalakias.mqtt.MqttMessage;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Bla implements Converter<MqttMessage, String> {
    @Override
    public String convert(MqttMessage source) {
        return null;
    }
}
