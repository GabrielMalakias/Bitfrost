package br.com.gabrielmalakias.mqtt;

public class OutputMessage {
    private final String identifier;
    private final String value;
    private final String type;

    public OutputMessage(String identifier, String value, String type) {
        this.identifier = identifier;
        this.value = value;
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}
