package br.com.karate.controller.abstracts;

import br.com.karate.converter.PageableConverter;
import br.com.karate.converter.abstracts.AbstractConverter;
import br.com.karate.model.util.pageable.PageableDto;
import br.com.karate.model.util.pageable.PageableOutput;
import br.com.karate.service.abstracts.CrudAbstractService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class CrudAbstractController<T, I, O, Filter> extends AbstractController {

    @Autowired
    private CrudAbstractService<T, Filter> service;

    @Autowired
    protected PageableConverter pageableConverter;
    @Autowired
    private AbstractConverter<T, I, O, Filter> converter;

    @PostMapping("/save")
    public ResponseEntity<O> save(@RequestBody @Validated I input) {
        return ResponseEntity.ok(converter.toDto(service.save(converter.toEntity(input))));
    }

    @GetMapping("/list")
    @Transactional(readOnly = true)
    public ResponseEntity<PageableOutput> list(@RequestParam String pageable, @RequestParam(value = "filter", required = false) String filter) throws JsonProcessingException {
        final PageableDto pageableDto = pageableConverter.toDto(pageable);
        final Page<T> pagedResult = service.list(pageableDto, converter.toFilter(filter));
        return ResponseEntity.ok(new PageableOutput(converter.toDto(pagedResult.getContent()), pagedResult.getTotalElements()));
    }

    @GetMapping("/{code}")
    @Transactional(readOnly = true)
    public ResponseEntity<O> getByCode(@PathVariable long code) {
        final T entity = service.findByCode(code);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(converter.toDto(entity));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<O>> findAll() {
        return ResponseEntity.ok(converter.toDto(service.findAll()));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Boolean> delete(@PathVariable long code) {
        if (Boolean.FALSE.equals(service.delete(code))) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(Boolean.TRUE);
    }


}
