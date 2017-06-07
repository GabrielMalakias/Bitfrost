package br.com.gabrielmalakias;

import br.com.gabrielmalakias.config.App;
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
        new Bridge("/dev/ttyS81").connect();
        App.MyGateway gateway =  ctx.getBean(App.MyGateway.class);
        for (int i =0; i < 10000; i++) {
            gateway.sendToMqtt("Hello Vodafone: " + i);
        }
    }
}
