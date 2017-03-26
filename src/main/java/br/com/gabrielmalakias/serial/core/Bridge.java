package br.com.gabrielmalakias.serial.core;

import br.com.gabrielmalakias.serial.command.Read;
import br.com.gabrielmalakias.serial.command.Write;
import gnu.io.*;

import java.io.IOException;

public class Bridge {
    private String portName;

    public Bridge(String portName) throws NoSuchPortException {
        this.portName = portName;
    }


    public void connect() throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException {
        CommPortIdentifier portIdentifier = getPortIdentifier(portName);

        if (portIdentifier.isCurrentlyOwned()) {
            System.out.print("Currently in use");
        } else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                new Thread(new Write(serialPort.getOutputStream())).start();
                new Thread(new Read(serialPort.getInputStream())).start();
            }
        }
    }

    public CommPortIdentifier getPortIdentifier(String portName) throws NoSuchPortException {
        return CommPortIdentifier.getPortIdentifier(portName);
    }
}
