package br.com.gabrielmalakias.serial;

import br.com.gabrielmalakias.configuration.BitfrostConfiguration;
import br.com.gabrielmalakias.mqtt.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class InputFlow {
    private final Integer MINUS_ONE = -1;
    private final String LOG_MESSAGE_FORMAT = "InputFlow: %s";

    private final Bridge bridge;
    private final Publisher publisher;
    private final BitfrostConfiguration config;

    @Autowired
    public InputFlow(Bridge bridge, BitfrostConfiguration config, Publisher publisher) {
        this.bridge = bridge;
        this.config = config;
        this.publisher = publisher;
    }

    public void start() {
        try {
            while (isBridgeAvailable()) {
                publish(getBufferedReader().readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isBridgeAvailable() {
        try {
            return bridge.getInputStream().available() > MINUS_ONE;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private BufferedReader getBufferedReader() {
        return new BufferedReader(new InputStreamReader(bridge.getInputStream()));
    }

    private boolean publish(String message) {
        log(message);
        return publisher.send(message);
    }

    private void log(String message) {
        System.out.println(String.format(LOG_MESSAGE_FORMAT, message));
    }
}
