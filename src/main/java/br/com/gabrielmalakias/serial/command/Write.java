package br.com.gabrielmalakias.serial.command;

import br.com.gabrielmalakias.serial.Bridge;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class Write {

    public void run(String message) {
        Bridge bridge = Bridge.getInstance().get();

        try {
            bridge.getOutputStream().write(message.getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
