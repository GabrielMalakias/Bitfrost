package br.com.gabrielmalakias.serial.command;

import br.com.gabrielmalakias.mqtt.Client;
import br.com.gabrielmalakias.mqtt.Write;
import br.com.gabrielmalakias.serial.core.Bridge;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class Read implements Runnable {
    private final IMqttClient client;

    @Autowired
    public Read(IMqttClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        Bridge bridge = Bridge.getInstance().get();
        byte[] buffer = new byte[1024];
        int len = -1;
        StringBuilder str = new StringBuilder();
        String stringBuffer;
        try {
            while ((len = bridge.getInputStream().read(buffer)) > -1) {
                stringBuffer = new String(buffer, 0, len);
                if(stringBuffer.contains("%")){
                    str.append(stringBuffer);
                    new Write(new Client(client)).run(str.toString().split("%")[0]);

                    System.out.println("Read: " + str.toString().replace("\n", ""));
                    str = new StringBuilder();
                } else {
                    str.append(stringBuffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
