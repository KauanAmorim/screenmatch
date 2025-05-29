package br.com.kauanamorim.screenmatch.service;

public interface IDataCoverter {
    <T> T convert(String json, Class<T> clazz);
}
