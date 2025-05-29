package br.com.kauanamorim.screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements  IDataCoverter {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T convert(String json, Class<T> clazz) {
        try {
            return this.mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
