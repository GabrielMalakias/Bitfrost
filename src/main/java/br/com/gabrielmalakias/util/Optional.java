package br.com.gabrielmalakias.util;

public class Optional {
    public static java.util.Optional<?> optional(Object nullable) {
        return java.util.Optional.ofNullable(nullable);
    }
}
