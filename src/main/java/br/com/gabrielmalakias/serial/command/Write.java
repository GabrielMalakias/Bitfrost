package br.com.gabrielmalakias.serial.command;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class Write implements Runnable {
    private final OutputStream output;
    private final String message;

    public Write(OutputStream output, String message) {
        this.output = output;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            this.output.write(message.getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
