package br.com.gabrielmalakias.mqtt;

import br.com.gabrielmalakias.configuration.BitfrostConfiguration;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.stereotype.Component;

@Component
public class Factory {
    private final BitfrostConfiguration config;

    @Autowired
    public Factory(BitfrostConfiguration config) {
        this.config = config;
    }

    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setServerURIs(config.getMqttServerURI());
        return factory;
    }

    @Bean
    public IMqttClient mqttClient() {
        try {
            return mqttPahoClientFactory().getClientInstance(config.getMqttServerURI(), "BITFROST_PUBLISHER");
        } catch (MqttException e) {
            return null;
        }
    }
}
