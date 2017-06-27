package br.com.gabrielmalakias.serial.command;

import br.com.gabrielmalakias.serial.Bridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class Write {

    private final Bridge bridge;

    @Autowired
    public Write(Bridge bridge) {
        this.bridge = bridge;
    }

    public void run(String message) {
        try {
            bridge.getOutputStream().write(message.getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
