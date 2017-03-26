package br.com.gabrielmalakias.serial.command;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class Write implements Runnable {
    public final OutputStream output;

    public Write(OutputStream output) {
        this.output = output;
    }

    @Override
    public void run() {
        try {

            this.output.write("name:temperature;value:24.44|name:luminosity;value:79|".getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
