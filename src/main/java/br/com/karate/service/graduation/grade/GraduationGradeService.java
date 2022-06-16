package br.com.karate.service.graduation.grade;

import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.grades.GraduationGrade;
import br.com.karate.model.professor.Professor;

import java.util.List;

public interface GraduationGradeService {

    public GraduationGrade save(Graduation graduation, Athlete athlete, Professor professor, Double grade, String description);

    public List<GraduationGrade> findByGraduationAndAthlete(Graduation graduation, Athlete athlete);

    public List<GraduationGrade> listGradesByAthletes(Graduation graduation, List<Athlete> athletes);

    public List<GraduationGrade> listGradesByGraduations(Athlete athlete, List<Graduation> graduations);
    public void removeProfessors(Graduation graduation, List<Professor> professors);

    public List<Athlete> verifyAllGradesSet(Graduation graduation);

}
