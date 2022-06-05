package br.com.karate.repository.graduation;

import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.grades.GraduationGrade;
import br.com.karate.model.professor.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GraduationGradeRepository extends JpaRepository<GraduationGrade, UUID> {

    public List<GraduationGrade> findByGraduationAndGrade(Graduation graduation, Double grade);
    public List<GraduationGrade> findByGraduationAndAthlete(Graduation graduation, Athlete athlete);

    public Optional<GraduationGrade> findByGraduationAndProfessorAndAthlete(Graduation graduation, Professor professor, Athlete athlete);

    public List<GraduationGrade> findByGraduationAndAthleteIn(Graduation graduation, List<Athlete> athletes);

    public void deleteByGraduationInAndProfessorIn(List<Graduation> graduations, List<Professor> professors);
}
