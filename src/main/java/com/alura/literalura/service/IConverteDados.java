package com.alura.literalura.service;

public interface IConverteDados {

    <T> T obterDados(String Json, Class<T> Clase);
}
