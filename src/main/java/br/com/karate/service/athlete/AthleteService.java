package br.com.karate.service.athlete;

import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.athlete.AthleteInput;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.util.pageable.PageableDto;
import br.com.karate.service.abstracts.CrudAbstractService;
import org.springframework.data.domain.Page;

public interface AthleteService extends CrudAbstractService<Athlete, AthleteInput.Filter> {

    public Page<Athlete> findAvailableAthletesToGraduation(Graduation graduation, PageableDto pageable);

}
