package br.com.gabrielmalakias.serial.command;

import br.com.gabrielmalakias.mqtt.Client;
import br.com.gabrielmalakias.mqtt.Write;
import org.eclipse.paho.client.mqttv3.IMqttClient;

import java.io.IOException;
import java.io.InputStream;

public class Read implements Runnable {
    private final InputStream input;
    private final IMqttClient client;

    public Read(InputStream input, IMqttClient client) {
        this.input = input;
        this.client = client;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        int len = -1;
        StringBuilder str = new StringBuilder();
        String readed = new String();
        try {
            while ((len = this.input.read(buffer)) > -1) {
                readed = new String(buffer, 0, len);
                if(readed.contains("%")){
                    str.append(readed);
                    new Write(new Client(client)).run(str.toString().split("%")[0]);
                    System.out.println("Read: " + str.toString().replace("\n", ""));
                    str = new StringBuilder();
                } else {
                    str.append(readed);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
