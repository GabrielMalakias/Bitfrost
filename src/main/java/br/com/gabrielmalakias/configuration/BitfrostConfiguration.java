package br.com.gabrielmalakias.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "bitfrost")
public class BitfrostConfiguration {
    @NestedConfigurationProperty
    private MqttConfiguration mqtt;

    @NestedConfigurationProperty
    private IncomingConfiguration incoming;

    @NestedConfigurationProperty
    private SerialConfiguration serial;

    public SerialConfiguration getSerial() {
        return serial;
    }

    public void setSerial(SerialConfiguration serialConfiguration) {
        this.serial = serialConfiguration;
    }

    public String getMqttServerURI() {
        return String.format("tcp://%s:%d", mqtt.getHost(), mqtt.getPort());
    }

    public IncomingConfiguration getIncoming() {
        return incoming;
    }

    public void setIncoming(IncomingConfiguration incomingConfiguration) {
        this.incoming = incomingConfiguration;
    }

    public MqttConfiguration getMqtt() {
        return this.mqtt;
    }

    public void setMqtt(MqttConfiguration mqttConfiguration) {
        this.mqtt = mqttConfiguration;
    }
}
