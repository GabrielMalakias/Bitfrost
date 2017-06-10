package br.com.gabrielmalakias.serial.core;

import gnu.io.*;

import java.util.Optional;

import static br.com.gabrielmalakias.util.Optional.optional;

public class SerialPortFactory {
    private static String PORT_FILE_DESCRIPTOR = "/dev/ttyS81";
    private static String COMM_PORT_IDENTIFIER = "BITFROST_BRIDGE_SERIAL";

    public Optional<SerialPort> build() {
        return optional(getPortIdentifier())
                .map(pI -> portIsOwned((CommPortIdentifier) pI))
                .map(pI -> getCommPort(pI))
                .map(pI -> buildSerialPort(pI));
    }

    private static SerialPort buildSerialPort(CommPort commPort) {
        SerialPort serialPort = (SerialPort) commPort;

        try {
            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            return serialPort;
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static CommPort getCommPort(CommPortIdentifier portIdentifier) {
        try {
            return portIdentifier.open(COMM_PORT_IDENTIFIER, 2000);
        } catch (PortInUseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static CommPortIdentifier portIsOwned (CommPortIdentifier portIdentifier) {
        if (portIdentifier.isCurrentlyOwned()) {
            return null;
        } else {
            return portIdentifier;
        }
    }

    private static CommPortIdentifier getPortIdentifier() {
        try {
            return CommPortIdentifier.getPortIdentifier(PORT_FILE_DESCRIPTOR);
        } catch (NoSuchPortException e) {
            e.printStackTrace();
            return null;
        }
    }
}
