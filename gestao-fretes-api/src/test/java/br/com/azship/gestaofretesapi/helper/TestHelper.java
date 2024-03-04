package br.com.azship.gestaofretesapi.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;

public class TestHelper {

    private static final ObjectMapper mapper = getMapper();

    @SneakyThrows
    public static byte[] convertObjectToJsonBytes(Object object) {
        return mapper.writeValueAsBytes(object);
    }

    private static ObjectMapper getMapper() {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
