package br.com.gabrielmalakias.converter;

import br.com.gabrielmalakias.mqtt.OutputMessage;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToOutputMessage implements Converter<String, OutputMessage> {
    private static String IDENTIFIER_TOKEN = "^(.*?(\bi\b)[^$]*)$";
    private static String VALUE_TOKEN = "^(.*?(\bv\b)[^$]*)$";
    private static String TYPE_TOKEN = "^(.*?(\bt\b)[^$]*)$";

    @Override
    public OutputMessage convert(String source) {
        return new OutputMessage(getIdentifierToken(source), getValueToken(source), getTypeToken(source));
    }

    private String getIdentifierToken(String source) {
        return getValueFromSource(source, 0);
    }

    private String getValueToken(String source) {
        return getValueFromSource(source, 1);
    }

    private String getTypeToken(String source) {
        return getValueFromSource(source, 2).replace("%", "");
    }

    private String getValueFromSource(String source, int index) {
        try {
            return source.split(";")[index].split(":")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error on InputFlow:" + source);
            return "";
        }
    }

}
