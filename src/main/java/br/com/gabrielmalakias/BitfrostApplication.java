package br.com.gabrielmalakias;
import br.com.gabrielmalakias.serial.InputFlow;
import br.com.gabrielmalakias.serial.command.Write;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
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

        MqttToSerialProcess mqttToSerialProcess = ctx.getBean(MqttToSerialProcess.class);
        mqttToSerialProcess.run();

        SerialToMqttProcess process = ctx.getBean(SerialToMqttProcess.class);
        process.run();
    }

    @Service
    class SerialToMqttProcess {
        private final InputFlow inputFlow;

        @Autowired
        SerialToMqttProcess(InputFlow inputFlow) {
            this.inputFlow = inputFlow;
        }

        @Async
        public void run() {
            inputFlow.start();
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
