package br.com.karate.controller;

import br.com.karate.controller.abstracts.CrudAbstractController;
import br.com.karate.converter.AthleteConverter;
import br.com.karate.converter.AthleteGraduationConverter;
import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.athlete.AthleteInput;
import br.com.karate.model.athlete.AthleteOutput;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.athlete.AthleteGraduation;
import br.com.karate.model.graduation.grades.GraduationGrade;
import br.com.karate.model.util.pageable.PageableDto;
import br.com.karate.model.util.pageable.PageableOutput;
import br.com.karate.service.athlete.AthleteService;
import br.com.karate.service.graduation.GraduationService;
import br.com.karate.service.graduation.athlete.AthleteGraduationService;
import br.com.karate.service.graduation.grade.GraduationGradeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/athlete")
public class AthleteController extends CrudAbstractController<Athlete, AthleteInput.Save, AthleteOutput.Dto, AthleteInput.Filter> {

    @Autowired
    private GraduationService graduationService;

    @Autowired
    private GraduationGradeService gradeService;

    @Autowired
    private AthleteGraduationService athleteGraduationService;

    @Autowired
    private AthleteService service;

    @Autowired
    private AthleteConverter converter;

    @Autowired
    private AthleteGraduationConverter athleteGraduationConverter;

    @GetMapping("/listAvailableAthletesToGraduation")
    @Transactional(readOnly = true)
    public ResponseEntity<PageableOutput> listAvailableAthletesToGraduation(@RequestParam String pageable, @RequestParam("graduationCode") long graduationCode) throws JsonProcessingException {
        final PageableDto pageableDto = pageableConverter.toDto(pageable);
        final Graduation graduation = graduationService.findByCodeThrowsException(graduationCode);

        final Page<Athlete> pagedResult = service.findAvailableAthletesToGraduation(graduation, pageableDto);
        return ResponseEntity.ok(new PageableOutput(converter.toDto(pagedResult.getContent()), pagedResult.getTotalElements()));
    }

    @GetMapping("/listGraduations")
    @Transactional(readOnly = true)
    public ResponseEntity<PageableOutput> listGraduations(@RequestParam String pageable, @RequestParam long athleteCode) throws JsonProcessingException {
        final PageableDto pageableDto = pageableConverter.toDto(pageable);
        final Athlete athlete = service.findByCodeThrowsException(athleteCode);

        final Page<AthleteGraduation> athleteGraduationPage = athleteGraduationService.findByAthlete(athlete, pageableDto);

        final List<Graduation> graduations = athleteGraduationPage.stream().map(AthleteGraduation::getGraduation).collect(Collectors.toList());

        final List<GraduationGrade> grades = gradeService.listGradesByGraduations(athlete, graduations);

        return ResponseEntity.ok(new PageableOutput(athleteGraduationConverter.toAthleteDto(athleteGraduationPage.getContent(), grades), athleteGraduationPage.getTotalElements()));
    }
}
