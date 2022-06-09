package br.com.karate.service.graduation.athlete;

import br.com.karate.enums.EnumAthleteGraduationSituation;
import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.athlete.AthleteGraduation;
import br.com.karate.model.util.pageable.PageableDto;
import org.springframework.data.domain.Page;

public interface AthleteGraduationService {

    public AthleteGraduation save(Graduation graduation, Athlete athlete, EnumAthleteGraduationSituation situation);

    public AthleteGraduation findByGraduationAndAthleteCode(Graduation graduation, Long code);

    public Page<AthleteGraduation> findByAthlete(Athlete athlete, PageableDto pageableDto);

    public Page<AthleteGraduation> findByGraduation(Graduation graduation, PageableDto pageableDto);

    public void setAthleteGrade(Graduation graduation, Athlete athlete, double grade);
}
