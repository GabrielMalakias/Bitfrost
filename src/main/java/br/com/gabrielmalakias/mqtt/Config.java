package br.com.gabrielmalakias.mqtt;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    public static Integer MQTT_COMPLETION_TIMEOUT = 3000;
    public static String SERVER_URI = "tcp://localhost:1883";
    public static boolean ASYNC = true;
}
