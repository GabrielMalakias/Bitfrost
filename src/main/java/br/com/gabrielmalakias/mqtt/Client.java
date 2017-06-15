package br.com.gabrielmalakias.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Client {
    private final IMqttClient client;

    public Client(IMqttClient client) {
        this.client = client;
    }

    public boolean publish(String topic, String message) {
        start();

        try {
            client.publish(topic, toMqttMessage(message));
        } catch (MqttException e) {
            e.printStackTrace();
        }

        return true;
    }

    private MqttMessage toMqttMessage(String message) {
        return new MqttMessage(message.getBytes());
    }

    private void start() {
        if(!client.isConnected())
            connect();
    }

    private boolean isConnected() {
        return client.isConnected();
    }

    private void connect() {
        try {
            client.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
