package br.com.gabrielmalakias.serial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class OutputFlow {
    private final Bridge bridge;

    @Autowired
    public OutputFlow(Bridge bridge) {
        this.bridge = bridge;
    }

    public boolean write(String message) {
        return writeOnSerialAndFlush(bridge.getOutputStream(), message);
    }

    private boolean writeOnSerialAndFlush(OutputStream stream, String message) {
        try {
            stream.write(message.getBytes());
            stream.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
