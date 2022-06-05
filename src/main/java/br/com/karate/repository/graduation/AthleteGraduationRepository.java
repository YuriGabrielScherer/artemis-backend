package br.com.karate.repository.graduation;

import br.com.karate.enums.EnumAthleteGraduationSituation;
import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.athlete.AthleteGraduation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AthleteGraduationRepository extends JpaRepository<AthleteGraduation, UUID> {

    public Optional<AthleteGraduation> findByAthleteAndGraduation(Athlete athlete, Graduation graduation);

    public AthleteGraduation findByGraduationAndAthletePersonCode(Graduation graduation, Long code);

    public Optional<AthleteGraduation> findByAthleteAndSituation(Athlete athlete, EnumAthleteGraduationSituation situation);

    public Page<AthleteGraduation> findByGraduation(Graduation graduation, Pageable pageable);

    public AthleteGraduation findFirstByAthleteAndSituationOrderByCreatedDateDesc(Athlete athlete, EnumAthleteGraduationSituation situation);

}
