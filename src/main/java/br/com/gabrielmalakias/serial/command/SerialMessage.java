package br.com.gabrielmalakias.serial.command;
import static br.com.gabrielmalakias.util.String.EMPTY;

public class SerialMessage {
    private final StringBuilder builder;
    private final String delimiter;

    public SerialMessage(String delimiter) {
        this.delimiter = delimiter;
        builder = new StringBuilder();
    }

    public String getMessage() {
        return builder.toString()
                .replace(delimiter, EMPTY);
    }

    public void append(String string) {
        this.builder.append(string);
    }
}
