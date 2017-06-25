package br.com.gabrielmalakias.util;

import br.com.gabrielmalakias.configuration.BitfrostConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class BitfrostLogger {
    private static final Logger INSTANCE = Logger.getLogger("BITFROST");

    public void log(Level level, java.lang.String msg) {
        INSTANCE.log(level, msg);
    }
}
