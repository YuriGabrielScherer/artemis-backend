package br.com.karate.controller;

import br.com.karate.controller.abstracts.CrudAbstractController;
import br.com.karate.converter.*;
import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.athlete.AthleteOutput;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.GraduationOutput;
import br.com.karate.model.graduation.athlete.AthleteGraduation;
import br.com.karate.model.graduation.grades.GraduationGrade;
import br.com.karate.model.professor.Professor;
import br.com.karate.model.util.pageable.PageableDto;
import br.com.karate.model.util.pageable.PageableOutput;
import br.com.karate.service.graduation.GraduationService;
import br.com.karate.service.graduation.athlete.AthleteGraduationService;
import br.com.karate.service.graduation.grade.GraduationGradeService;
import br.com.karate.service.professor.ProfessorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.karate.model.graduation.GraduationInput.*;

@RestController
@RequestMapping("/graduation")
@Slf4j
public class GraduationController extends CrudAbstractController<Graduation, Save, GraduationOutput.Dto, Filter> {

    @Autowired
    private GraduationService service;
    @Autowired
    private GraduationConverter converter;
    @Autowired
    private AthleteGraduationService athleteGraduationService;
    @Autowired
    private AthleteGraduationConverter athleteGraduationConverter;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private ProfessorConverter professorConverter;
    @Autowired
    private GraduationGradeService graduationGradeService;
    @Autowired
    private GraduationGradeConverter graduationGradeConverter;
    @Autowired
    private AthleteConverter athleteConverter;

    @PostMapping("/updateStatus")
    public ResponseEntity updateStatus(@RequestBody @Validated UpdateStatus input) {
        service.updateStatus(input.code, input.situation);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/registerProfessors")
    public ResponseEntity registerProfessors(@RequestBody @Validated RegisterProfessors input) {
        service.registerProfessors(input.graduationCode, input.professorsCode);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/removeProfessors")
    public ResponseEntity removeProfessors(@RequestBody @Validated RegisterProfessors input) {
        service.removeProfessors(input.graduationCode, input.professorsCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listProfessors")
    @Transactional(readOnly = true)
    public ResponseEntity<PageableOutput> listProfessors(@RequestParam String pageable, @RequestParam("graduationCode") long graduationCode) throws JsonProcessingException {
        final PageableDto pageableDto = pageableConverter.toDto(pageable);
        final Graduation graduation = service.findByCodeThrowsException(graduationCode);
        final Page<Professor> pagedResult = professorService.findProfessorsByGraduation(graduation, pageableDto);
        return ResponseEntity.ok(new PageableOutput(professorConverter.toDto(pagedResult.getContent()), pagedResult.getTotalElements()));
    }

    @GetMapping("/listAthletes")
    @Transactional(readOnly = true)
    public ResponseEntity<PageableOutput> listAthletes(@RequestParam String pageable, @RequestParam("graduationCode") long graduationCode) throws JsonProcessingException {
        final PageableDto pageableDto = pageableConverter.toDto(pageable);
        final Graduation graduation = service.findByCode(graduationCode);
        final Page<AthleteGraduation> pagedResult = athleteGraduationService.findByGraduation(graduation, pageableDto);
        return ResponseEntity.ok(new PageableOutput(athleteGraduationConverter.toDto(pagedResult.getContent()), pagedResult.getTotalElements()));
    }

    @PostMapping("/removeAthletes")
    public ResponseEntity removeAthletes(@RequestBody @Validated RequestParticipation input) {
        service.removeAthletes(input.graduationCode, input.athletes);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/requestParticipation")
    public ResponseEntity requestParticipation(@RequestBody @Validated RequestParticipation input) {
        final List<String> errors = new ArrayList<>();
        for (long athleteCode : input.athletes) {
            try {
                service.requestParticipation(input.graduationCode, athleteCode);
            } catch (ResponseStatusException e) {
                errors.add(String.format("Erro ao cadastrar o atleta código %s. Mensagem: %s", athleteCode, e.getReason()));
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(errors);
    }

    @GetMapping("listGraduationGrades")
    @Transactional(readOnly = true)
    public ResponseEntity<PageableOutput> listGraduationGrades(@RequestParam String pageable, @RequestParam("graduationCode") long graduationCode) throws JsonProcessingException {
        final PageableDto pageableDto = pageableConverter.toDto(pageable);
        final Graduation graduation = service.findByCodeThrowsException(graduationCode);
        final Page<AthleteGraduation> athletes = athleteGraduationService.findByGraduation(graduation, pageableDto);
        final List<GraduationGrade> grades = graduationGradeService.listGradesByAthletes(graduation, athletes.toList().stream().map(AthleteGraduation::getAthlete).collect(Collectors.toList()));
        return ResponseEntity.ok(new PageableOutput(graduationGradeConverter.toDto(grades), athletes.getTotalElements()));
    }

    @PostMapping("/setGraduationGrades")
    public ResponseEntity setGraduationGrades(@RequestBody @Validated SetGraduationGradesInput payload) {

        for (AthleteProfessorGrade grade : payload.grades) {
            service.setGraduationGrade(payload.graduationCode, grade.athleteCode, grade.professorCode, grade.grade, grade.description);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("verifyAllGradesSet")
    @Transactional(readOnly = true)
    public ResponseEntity<List<AthleteOutput.Dto>> verifyAllGradesSet(@RequestParam long graduationCode) {

        final Graduation graduation = service.findByCodeThrowsException(graduationCode);
        final List<Athlete> athletes = graduationGradeService.verifyAllGradesSet(graduation);

        if (athletes.size() == 0) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        return ResponseEntity.ok(athleteConverter.toDto(athletes));
    }

    @GetMapping("countFutureGraduation")
    @Transactional(readOnly = true)
    public ResponseEntity countFutureGraduation() {
        final long count = service.countFutureGraduation();
        return ResponseEntity.ok(count);
    }
}
