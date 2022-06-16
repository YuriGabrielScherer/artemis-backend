package br.com.karate.service.graduation.grade;

import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.grades.GraduationGrade;
import br.com.karate.model.professor.Professor;
import br.com.karate.repository.graduation.GraduationGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GraduationGradeServiceImpl implements GraduationGradeService {

    @Autowired
    private GraduationGradeRepository repository;

    @Override
    public GraduationGrade save(Graduation graduation, Athlete athlete, Professor professor, Double grade, String description) {
        if (grade < 0 || grade > 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Nota do atleta %s dada pelo professor %s precisa estar entre 0 e 10.", athlete.getPerson().getCode(), professor.getPerson().getCode()));
        }

        GraduationGrade graduationGrade = repository.findByGraduationAndProfessorAndAthlete(graduation, professor, athlete).orElse(null);

        if (graduationGrade == null) {
            graduationGrade = new GraduationGrade();
            graduationGrade.setGrade(grade);
        }

        if (grade > 0) {
            graduationGrade.setGrade(grade);
        }

        graduationGrade.setGraduation(graduation);
        graduationGrade.setAthlete(athlete);
        graduationGrade.setProfessor(professor);
        if (description != null && description.length() > 0) {
            graduationGrade.setDescription(description.trim());
        }

        return repository.save(graduationGrade);
    }

    @Override
    public List<GraduationGrade> findByGraduationAndAthlete(Graduation graduation, Athlete athlete) {
        return repository.findByGraduationAndAthlete(graduation, athlete);
    }

    @Override
    public List<GraduationGrade> listGradesByAthletes(Graduation graduation, List<Athlete> athletes) {
        return repository.findByGraduationAndAthleteIn(graduation, athletes);
    }

    @Override
    public List<GraduationGrade> listGradesByGraduations(Athlete athlete, List<Graduation> graduations) {
        return repository.findByAthleteAndGraduationIn(athlete, graduations);
    }
    @Override
    @Transactional
    public void removeProfessors(Graduation graduation, List<Professor> professors) {
        repository.deleteByGraduationAndProfessorIn(graduation, professors);
    }

    @Override
    public List<Athlete> verifyAllGradesSet(Graduation graduation) {

        List<GraduationGrade> withoutGrade = repository.findByGraduationAndGrade(graduation, 0.0);

        if (withoutGrade == null && withoutGrade.size() == 0) {
            return List.of();
        }

        return withoutGrade.stream().map(GraduationGrade::getAthlete).collect(Collectors.toList());
    }


}
