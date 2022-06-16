package br.com.karate.controller;

import br.com.karate.controller.abstracts.AbstractController;
import br.com.karate.converter.BeltConverter;
import br.com.karate.converter.PageableConverter;
import br.com.karate.model.belt.Belt;
import br.com.karate.model.belt.BeltInput;
import br.com.karate.model.util.pageable.PageableDto;
import br.com.karate.model.util.pageable.PageableOutput;
import br.com.karate.service.belt.BeltService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("belt")
public class BeltController extends AbstractController {
    @Autowired
    private BeltService service;
    @Autowired
    private PageableConverter pageableConverter;
    @Autowired
    private BeltConverter converter;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody @Validated BeltInput.Save payload) {
        return ResponseEntity.ok(converter.toDto(service.save(converter.toEntity(payload))));
    }

    @GetMapping("/list")
    @Transactional(readOnly = true)
    public ResponseEntity<PageableOutput> list(@RequestParam String pageable) throws JsonProcessingException {
        final PageableDto pageableDto = pageableConverter.toDto(pageable);
        final Page<Belt> pagedResult = service.list(pageableDto);
        return ResponseEntity.ok(new PageableOutput(converter.toDto(pagedResult.getContent()), pagedResult.getTotalElements()));
    }
}
