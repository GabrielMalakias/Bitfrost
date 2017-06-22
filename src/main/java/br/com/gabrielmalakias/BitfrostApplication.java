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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.Async;
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

//        Bridge.getInstance()
//            .map(b -> startSerialReaderProcess(b, client))
//            .map(b -> startSerialWriterProcess(b));

        MqttToSerialProcess mqttToSerialProcess = ctx.getBean(MqttToSerialProcess.class);
        mqttToSerialProcess.run();

        SerialToMqttProcess process = ctx.getBean(SerialToMqttProcess.class);
        process.run();


        try {
            client.connect();
            client.publish("sensor/system", new MqttMessage("Hello from Bitfrost 1".getBytes()));

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Service
    class SerialToMqttProcess {
        private final Read read;

        @Autowired
        SerialToMqttProcess(Read read) {
            this.read = read;
        }

        @Async
        public void run() {
            read.run();
        }
    }

    @Service
    class MqttToSerialProcess {
        private final Write write;

        @Autowired
        MqttToSerialProcess(Write write) {
            this.write = write;
        }

        @Async
        public void run() {
            System.out.println("Its writing!!");
            write.run("Its writing!!!");
        }
    }
}
