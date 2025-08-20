package com.alura.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
@Component
public class ConverteDados implements IConverteDados {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public <T> T obterDados(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao desserializar JSON", e);

        }

    }
}
