package br.com.gabrielmalakias.configuration;

import br.com.gabrielmalakias.configuration.serial.MessageConfiguration;
import br.com.gabrielmalakias.configuration.serial.PortConfiguration;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

public class SerialConfiguration {
    @NestedConfigurationProperty
    private MessageConfiguration message;

    @NestedConfigurationProperty
    private PortConfiguration portConfiguration;

    public PortConfiguration getPortConfiguration() {
        return portConfiguration;
    }

    public void setPortConfiguration(PortConfiguration portConfiguration) {
        this.portConfiguration = portConfiguration;
    }

    public MessageConfiguration getMessage() {
        return message;
    }

    public void setMessage(MessageConfiguration message) {
        this.message = message;
    }
}
