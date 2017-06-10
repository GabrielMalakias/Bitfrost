package br.com.gabrielmalakias;

import br.com.gabrielmalakias.mqtt.OutputGateway;
import br.com.gabrielmalakias.serial.command.Read;
import br.com.gabrielmalakias.serial.command.Write;
import br.com.gabrielmalakias.serial.core.Bridge;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
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

        System.out.println("Started");

        Bridge.getInstance()
            .map(b -> startSerialReaderProcess(b))
            .map(b -> startSerialWriterProcess(b));

        OutputGateway.Gateway gateway =  ctx.getBean(OutputGateway.Gateway.class);
        for (int i =0; i < 10000; i++) {
            gateway.sendToMqtt("Hello Vodafone: " + i);
        }
    }

    public static Bridge startSerialReaderProcess(Bridge bridge) {
        try {
            new Thread(new Read(bridge.getSerialPort().getInputStream())).start();
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
