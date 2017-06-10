package br.com.gabrielmalakias.mqtt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class Input {
    private static String CLIENT_IDENTIFIER = "BITFROST_INPUT_CLIENT";
    private static String ACTUATOR_CHANNEL = "actuator/+/send";
    private static String MESSAGE_CHANNEL_IDENTIFIER = "inputMessageChannel";

    @Bean
    public MessageChannel inputMessageChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = buildPahoDrivenChannelAdapter();
        adapter.setCompletionTimeout(Config.MQTT_COMPLETION_TIMEOUT);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(inputMessageChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "inputMessageChannel")
    public MessageHandler inputHandler() {
        return new InputMessageHandler();
    }

    private MqttPahoMessageDrivenChannelAdapter buildPahoDrivenChannelAdapter() {
         return new MqttPahoMessageDrivenChannelAdapter(Config.SERVER_URI,
                CLIENT_IDENTIFIER, ACTUATOR_CHANNEL);
    }
}
