package br.com.gabrielmalakias.serial.command;

import br.com.gabrielmalakias.serial.core.Bridge;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class Write implements Runnable {
    private final Bridge bridge;
    private final String message;

    public Write(Bridge bridge, String message) {
        this.bridge = bridge;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            bridge.getOutputStream().write(message.getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
