package br.com.gabrielmalakias.configuration;

import br.com.gabrielmalakias.configuration.serial.MessageConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "bitfrost")
public class BitfrostConfiguration {
    @NestedConfigurationProperty
    private MqttConfiguration mqttConfiguration;

    @NestedConfigurationProperty
    private IncomingConfiguration incomingConfiguration;

    @NestedConfigurationProperty
    private SerialConfiguration serialConfiguration;

    public SerialConfiguration getSerialConfiguration() {
        return serialConfiguration;
    }

    public void setSerialConfiguration(SerialConfiguration serialConfiguration) {
        this.serialConfiguration = serialConfiguration;
    }

    public String getMqttServerURI() {
        return String.format("tcp://%s:%d", mqttConfiguration.getHost(), mqttConfiguration.getPort());
    }

    public IncomingConfiguration getIncomingConfiguration() {
        return incomingConfiguration;
    }

    public void setIncomingConfiguration(IncomingConfiguration incomingConfiguration) {
        this.incomingConfiguration = incomingConfiguration;
    }

    public MqttConfiguration getMqttConfiguration() {
        return this.mqttConfiguration;
    }

    public void setMqttConfiguration(MqttConfiguration mqttConfiguration) {
        this.mqttConfiguration = mqttConfiguration;
    }
}
