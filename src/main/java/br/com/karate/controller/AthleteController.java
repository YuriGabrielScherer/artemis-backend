package br.com.karate.controller;

import br.com.karate.controller.abstracts.CrudAbstractController;
import br.com.karate.converter.AthleteConverter;
import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.athlete.AthleteInput;
import br.com.karate.model.athlete.AthleteOutput;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.util.pageable.PageableDto;
import br.com.karate.model.util.pageable.PageableOutput;
import br.com.karate.service.athlete.AthleteService;
import br.com.karate.service.graduation.GraduationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/athlete")
public class AthleteController extends CrudAbstractController<Athlete, AthleteInput.Save, AthleteOutput.Dto, AthleteInput.Filter> {

    @Autowired
    private GraduationService graduationService;

    @Autowired
    private AthleteService service;

    @Autowired
    private AthleteConverter converter;

    @GetMapping("/listAvailableAthletesToGraduation")
    @Transactional(readOnly = true)
    public ResponseEntity<PageableOutput> listAvailableProfessorsToGraduation(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("graduationCode") long graduationCode) {

        final Graduation graduation = graduationService.findByCodeThrowsException(graduationCode);

        final Page<Athlete> pagedResult = service.findAvailableAthletesToGraduation(graduation, new PageableDto(page, size));
        return ResponseEntity.ok(new PageableOutput(converter.toDto(pagedResult.getContent()), pagedResult.getTotalElements()));
    }
}
