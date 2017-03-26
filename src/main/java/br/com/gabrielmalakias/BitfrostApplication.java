package br.com.gabrielmalakias;

import br.com.gabrielmalakias.serial.core.Bridge;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;

//sudo ln -s /dev/ttyACM0 /dev/ttyS81

public class BitfrostApplication {
    public static void main(String[] args) throws NoSuchPortException, IOException, PortInUseException, UnsupportedCommOperationException {
        System.out.println("Started");
        new Bridge("/dev/ttyS81").connect();
    }
}
