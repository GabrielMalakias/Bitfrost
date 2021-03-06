package br.com.gabrielmalakias.mqtt;

import br.com.gabrielmalakias.configuration.BitfrostConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class InputConfiguration {
    private final BitfrostConfiguration config;
    private final MqttPahoClientFactory mqttFactory;
    private final IncomingMessageHandler messageHandler;

    @Autowired
    public InputConfiguration(BitfrostConfiguration config, MqttPahoClientFactory mqttFactory, IncomingMessageHandler messageHandler) {
        this.config = config;
        this.mqttFactory = mqttFactory;
        this.messageHandler = messageHandler;
    }

    @Bean
    public MessageChannel inputMessageChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = buildPahoDrivenChannelAdapter();
        adapter.setCompletionTimeout(config.getMqtt().getTimeout());
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(config.getMqtt().getQos());
        adapter.setOutputChannel(inputMessageChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "inputMessageChannel")
    public MessageHandler inputHandler() {
        return messageHandler;
    }

    private MqttPahoMessageDrivenChannelAdapter buildPahoDrivenChannelAdapter() {
        return new MqttPahoMessageDrivenChannelAdapter(config.getIncoming().getIdentifier(),
                mqttFactory,
                config.getIncoming().getTopic());
    }
}
