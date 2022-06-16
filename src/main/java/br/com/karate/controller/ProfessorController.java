package br.com.karate.controller;

import br.com.karate.controller.abstracts.CrudAbstractController;
import br.com.karate.converter.ProfessorConverter;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.professor.Professor;
import br.com.karate.model.professor.ProfessorInput;
import br.com.karate.model.professor.ProfessorOutput;
import br.com.karate.model.util.pageable.PageableDto;
import br.com.karate.model.util.pageable.PageableOutput;
import br.com.karate.service.graduation.GraduationService;
import br.com.karate.service.professor.ProfessorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/professor")
public class ProfessorController extends CrudAbstractController<Professor, ProfessorInput.Save, ProfessorOutput.Dto, ProfessorInput.Filter> {

    @Autowired
    private ProfessorService service;

    @Autowired
    private ProfessorConverter converter;

    @Autowired
    private GraduationService graduationService;

    @GetMapping("/listAvailableProfessorsToGraduation")
    @Transactional(readOnly = true)
    public ResponseEntity<PageableOutput> listAvailableProfessorsToGraduation(@RequestParam String pageable, @RequestParam("graduationCode") long graduationCode) throws JsonProcessingException {
        final PageableDto pageableDto = pageableConverter.toDto(pageable);
        final Graduation graduation = graduationService.findByCodeThrowsException(graduationCode);

        final Page<Professor> pagedResult = service.findAvailableProfessorsToGraduation(graduation, pageableDto);
        return ResponseEntity.ok(new PageableOutput(converter.toDto(pagedResult.getContent()), pagedResult.getTotalElements()));
    }
}
