package br.com.gabrielmalakias;
import br.com.gabrielmalakias.serial.command.Read;
import br.com.gabrielmalakias.serial.command.Write;
import br.com.gabrielmalakias.serial.core.Bridge;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;

import java.io.IOException;

//sudo ln -s /dev/ttyACM0 /dev/ttyS81

@SpringBootApplication
@IntegrationComponentScan
public class BitfrostApplication {
    public static void main(String[] args) throws NoSuchPortException, IOException, PortInUseException, UnsupportedCommOperationException {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(BitfrostApplication.class)
                .web(false).run(args);

        IMqttClient client = ctx.getBean(IMqttClient.class);

        Bridge.getInstance()
            .map(b -> startSerialReaderProcess(b, client))
            .map(b -> startSerialWriterProcess(b));

        try {
            client.connect();
            client.publish("sensor/system", new MqttMessage("Hello from Bitfrost 1".getBytes()));

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static Bridge startSerialReaderProcess(Bridge bridge, IMqttClient client) {
        try {
            new Thread(new Read(bridge.getSerialPort().getInputStream(), client)).start();
            return bridge;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bridge startSerialWriterProcess(Bridge bridge) {
        try {
            new Thread(new Write(bridge.getSerialPort().getOutputStream(), "Test Writable")).start();
            return bridge;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
