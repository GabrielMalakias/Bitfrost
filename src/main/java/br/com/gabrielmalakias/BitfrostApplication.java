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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
//            .map(b -> startSerialReaderProcess(b, client))
            .map(b -> startSerialWriterProcess(b));


        Process process = ctx.getBean(Process.class);
        process.run();


        try {
            client.connect();
            client.publish("sensor/system", new MqttMessage("Hello from Bitfrost 1".getBytes()));

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Service
    class Process {
        private final Read read;

        @Autowired
        Process(Read read) {
            this.read = read;
        }

        public void run() {
            startSerialRead();
        }

        @Async
        public void startSerialRead() {
            read.run();
        }
    }

    public static Bridge startSerialWriterProcess(Bridge bridge) {
        new Thread(new Write(bridge, "Test Writable")).start();
        return bridge;
    }
}
