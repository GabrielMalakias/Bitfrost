package br.com.gabrielmalakias.serial.core;

import gnu.io.*;

import java.io.IOException;
import java.io.OutputStream;
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

    public boolean writeOnOutputStream(String message) {
        return getOutputStream()
                .map(out -> write(out, message))
                .orElse(false);
    }

    private boolean write(OutputStream stream, String message) {
        try {
            stream.write(message.getBytes());
            stream.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private Optional<OutputStream> getOutputStream() {
        try {
            return Optional.ofNullable(this.getSerialPort().getOutputStream());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private static Optional<Bridge> buildBridge(SerialPort serialPort) {
        return Optional.ofNullable(new Bridge(serialPort));
    }
}
