package br.com.karate.service.abstracts;

import br.com.karate.model.util.pageable.PageableDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AbstractService<T, Filter> {

    public T save(T input);
    public List<T> findAll();

    public Page<T> list(PageableDto input, Filter filterInput);

}
