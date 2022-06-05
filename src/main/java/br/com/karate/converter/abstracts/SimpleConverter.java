package br.com.karate.converter.abstracts;

import java.util.List;
import java.util.stream.Collectors;

public interface SimpleConverter<T, O> {

    public O toDto(T entity);
    public default List<O> toDto(List<T> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

}
