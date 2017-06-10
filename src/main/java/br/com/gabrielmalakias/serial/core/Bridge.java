package br.com.gabrielmalakias.serial.core;

import gnu.io.*;

import java.util.Optional;

public class Bridge {
    private final SerialPort serialPort;
    private static Optional<Bridge> instance;

    private Bridge(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    public static synchronized Optional<Bridge> getInstance() {
        if(instance == null) {
            SerialPortFactory factory = new SerialPortFactory();
            instance = factory.build()
                    .flatMap(s -> buildBridge(s));
        }

        return instance;
    }

    private static Optional<Bridge> buildBridge(SerialPort serialPort) {
        return Optional.ofNullable(new Bridge(serialPort));
    }
}
