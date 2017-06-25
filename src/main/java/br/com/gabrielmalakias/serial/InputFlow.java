package br.com.gabrielmalakias.serial;

import br.com.gabrielmalakias.configuration.BitfrostConfiguration;
import br.com.gabrielmalakias.mqtt.Publisher;
import br.com.gabrielmalakias.serial.command.SerialMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static br.com.gabrielmalakias.util.String.EMPTY;

@Component
public class InputFlow {
    private final Integer MINUS_ONE = -1;
    private final String LOG_MESSAGE_FORMAT = "InputFlow: %s";

    private final BitfrostConfiguration config;
    private final Publisher publisher;
    private StringBuilder builder;

    @Autowired
    public InputFlow(BitfrostConfiguration config, Publisher publisher) {
        this.config = config;
        this.publisher = publisher;
    }

    public void start() {
        Bridge bridge = Bridge.getInstance().get();

        SerialMessage serialMessage = createNewSerialMessage();

        byte[] buffer = new byte[1024];
        int len;
        String stringBuffer;

        try {
            BufferedReader bf = null;
            while (bridge.getInputStream().available() > MINUS_ONE ) {
                new BufferedReader(new InputStreamReader(bridge.getInputStream()));
                String s = bf.readLine();
            }



            while ((len = bridge.getInputStream().read(buffer)) > MINUS_ONE) {
                stringBuffer = new String(buffer, 0, len);

                if(containsMessageDelimiter(stringBuffer)){
                    serialMessage.append(stringBuffer);
                    publish(serialMessage);

                    serialMessage = createNewSerialMessage();
                } else {
                    serialMessage.append(stringBuffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SerialMessage createNewSerialMessage() {
        return new SerialMessage(config.getSerialConfiguration().getMessage().getDelimiter());
    }

    private boolean containsMessageDelimiter(String message) {
        return message.contains(config.getSerialConfiguration().getMessage().getDelimiter());
    }

    private boolean publish(SerialMessage serialMessage) {
        return publisher.send(serialMessage.getMessage());
    }

    private boolean sendToMqtt(String message) {
        log(message);
        return publisher.send(message);
    }

    private void log(String message) {
        System.out.println(String.format(LOG_MESSAGE_FORMAT, message));
    }
}
