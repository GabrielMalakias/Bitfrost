package br.com.gabrielmalakias.serial.command;

import java.io.IOException;
import java.io.InputStream;

public class Read implements Runnable {
    private final InputStream input;

    public Read(InputStream input) {
        this.input = input;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while ((len = this.input.read(buffer)) > -1) {
                System.out.print(new String(buffer, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
