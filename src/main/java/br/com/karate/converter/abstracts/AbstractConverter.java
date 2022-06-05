package br.com.karate.converter.abstracts;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface AbstractConverter<T, I, O, Filter> extends SimpleConverter<T, O> {
    T toEntity(I input);

    O toSimpleDto(T entity);

    List<O> toSimpleDto(List<T> entities);

    Filter toFilter(String filterInput) throws JsonProcessingException;

}
