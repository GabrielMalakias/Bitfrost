package br.com.gabrielmalakias.serial;

import gnu.io.SerialPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Component
public class Bridge {
    private final SerialPort serial;

    @Autowired
    public Bridge(SerialPort serial) {
        this.serial = serial;
    }


    public OutputStream getOutputStream() {
        try {
            return serial.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public InputStream getInputStream() {
        try {
            return serial.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

   /*
    private final SerialPort serialPort;
    private static Optional<Bridge> instance;
    private final InputStream inputStream;
    private final OutputStream outputStream;


    private Bridge(SerialPort serialPort, InputStream inputStream, OutputStream outputStream) {
        this.serialPort = serialPort;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    public static synchronized Optional<Bridge> getInstance() {
        if(instance == null) {
            PortFactory factory = new PortFactory(config);
            instance = factory.build()
                    .flatMap(s -> buildBridge(s));
        }

        return instance;
    }

    public boolean writeOnOutputStream(String message) {
        return Optional.ofNullable(getOutputStream())
                .map(out -> write(out, message))
                .orElse(false);
    }

    private boolean write(OutputStream stream, String message) {
        try {
            stream.write(message.getBytes());
            stream.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    private static Optional<Bridge> buildBridge(SerialPort serialPort) {
        try {
            return Optional.ofNullable(new Bridge(serialPort, serialPort.getInputStream(), serialPort.getOutputStream()));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
    */
}
