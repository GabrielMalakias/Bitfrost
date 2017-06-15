package br.com.gabrielmalakias.config;

import br.com.gabrielmalakias.converter.MqttMessageToString;
import br.com.gabrielmalakias.mqtt.Config;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

@Configuration
public class BeanInitializer {
    @Bean
    public MqttMessageToString mqttMessageToString() {
        return new MqttMessageToString();
    }

    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setServerURIs(Config.SERVER_URI);
        return factory;
    }

    @Bean
    public IMqttClient mqttClient() {
        try {
            return mqttPahoClientFactory().getClientInstance("tcp://localhost:1883", "BITFROST_PUBLISHER");
        } catch (MqttException e) {
            return null;
        }
    }
}
