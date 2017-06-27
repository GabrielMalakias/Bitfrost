package br.com.gabrielmalakias.serial;

import br.com.gabrielmalakias.configuration.BitfrostConfiguration;
import gnu.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static br.com.gabrielmalakias.util.Optional.optional;

@Component
public class PortFactory {
    private final BitfrostConfiguration config;

    @Autowired
    public PortFactory(BitfrostConfiguration config) {
        this.config = config;
    }

    @Bean
    @Scope("singleton")
    public SerialPort build() {
        return optional(getPortIdentifier())
                .map(pI -> portIsOwned((CommPortIdentifier) pI))
                .map(pI -> getCommPort(pI))
                .map(pI -> buildSerialPort(pI))
                .get();
    }

    private SerialPort buildSerialPort(CommPort commPort) {
        SerialPort serialPort = (SerialPort) commPort;

        try {
            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            return serialPort;
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    private CommPort getCommPort(CommPortIdentifier portIdentifier) {
        try {
            return portIdentifier.open(config.getSerial().getPort().getIdentifier(), 2000);
        } catch (PortInUseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private CommPortIdentifier portIsOwned(CommPortIdentifier portIdentifier) {
        if (portIdentifier.isCurrentlyOwned()) {
            return null;
        } else {
            return portIdentifier;
        }
    }

    private CommPortIdentifier getPortIdentifier() {
        try {
            return CommPortIdentifier.getPortIdentifier(config.getSerial().getPort().getFileDescriptor());
        } catch (NoSuchPortException e) {
            e.printStackTrace();
            return null;
        }
    }
}
