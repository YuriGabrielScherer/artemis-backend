package br.com.karate.repository.athlete;

import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.graduation.Graduation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AthleteCustomRepository {

    public Page<Athlete> findAvailableAthletesToGraduation(Graduation graduation, Pageable pageable);

}
