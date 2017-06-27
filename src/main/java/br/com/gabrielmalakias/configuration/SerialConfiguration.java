package br.com.gabrielmalakias.configuration;

import br.com.gabrielmalakias.configuration.serial.MessageConfiguration;
import br.com.gabrielmalakias.configuration.serial.PortConfiguration;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

public class SerialConfiguration {
    @NestedConfigurationProperty
    private MessageConfiguration message;

    @NestedConfigurationProperty
    private PortConfiguration port;

    public PortConfiguration getPort() {
        return port;
    }

    public void setPort(PortConfiguration portConfiguration) {
        this.port = portConfiguration;
    }

    public MessageConfiguration getMessage() {
        return message;
    }

    public void setMessage(MessageConfiguration message) {
        this.message = message;
    }
}
