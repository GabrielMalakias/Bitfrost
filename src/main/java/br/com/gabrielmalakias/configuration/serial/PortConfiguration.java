package br.com.gabrielmalakias.configuration.serial;

public class PortConfiguration {
    private String identifier;
    private String fileDescriptor;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFileDescriptor() {
        return fileDescriptor;
    }

    public void setFileDescriptor(String fileDescriptor) {
        this.fileDescriptor = fileDescriptor;
    }
}
