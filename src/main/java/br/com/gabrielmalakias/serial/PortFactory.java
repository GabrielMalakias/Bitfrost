package br.com.gabrielmalakias.serial;

import br.com.gabrielmalakias.configuration.BitfrostConfiguration;
import gnu.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static br.com.gabrielmalakias.util.Optional.optional;

@Component
public class PortFactory {
    private final BitfrostConfiguration config;

    @Autowired
    public PortFactory(BitfrostConfiguration config) {
        this.config = config;
    }

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
            return portIdentifier.open(config.getSerialConfiguration().getPortConfiguration().getIdentifier(), 2000);
        } catch (PortInUseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private CommPortIdentifier portIsOwned (CommPortIdentifier portIdentifier) {
        if (portIdentifier.isCurrentlyOwned()) {
            return null;
        } else {
            return portIdentifier;
        }
    }

    private CommPortIdentifier getPortIdentifier() {
        try {
            return CommPortIdentifier.getPortIdentifier(config.getSerialConfiguration().getPortConfiguration().getFileDescriptor());
        } catch (NoSuchPortException e) {
            e.printStackTrace();
            return null;
        }
    }
}
