package br.com.karate.service.athlete;

import br.com.karate.enums.EnumAthleteGraduationSituation;
import br.com.karate.enums.EnumBelt;
import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.athlete.AthleteInput;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.athlete.AthleteGraduation;
import br.com.karate.model.person.Person;
import br.com.karate.model.util.pageable.PageableDto;
import br.com.karate.repository.athlete.AthleteCustomRepository;
import br.com.karate.repository.athlete.AthleteRepository;
import br.com.karate.service.belt.BeltService;
import br.com.karate.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class AthleteServiceImpl implements AthleteService {

    @Autowired
    private AthleteRepository repository;

    @Autowired
    private AthleteCustomRepository customRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private BeltService beltService;

    @Override
    public Athlete save(Athlete input) {
        final Person person = personService.findByCodeThrowsException(input.getPerson().getCode());

        Athlete athlete = person.getAthlete();
        if (athlete == null) {
            athlete = new Athlete();

            final AthleteGraduation graduation = new AthleteGraduation();
            graduation.setBelt(beltService.findByBelt(EnumBelt.WHITE));
            graduation.setSituation(EnumAthleteGraduationSituation.APPROVED);
            athlete.addGraduation(graduation);
        }

        if (input.getSince().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de início precisa ser antes da data atual.");
        }

        athlete.setSince(input.getSince());
        athlete.setPerson(person);

        return repository.save(athlete);
    }

    @Override
    public List<Athlete> findAll() {
        return repository.findAll();
    }

    @Override
    public Athlete findByCode(long code) {
        final Person person = personService.findByCode(code);

        if (person == null) {
            return null;
        }

        return person.getAthlete();
    }

    @Override
    public Athlete findByCodeThrowsException(long code) {
        final Athlete athlete = personService.findByCodeThrowsException(code).getAthlete();
        if (athlete == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atleta não encontrado.");
        }
        return athlete;
    }

    @Override
    public Page<Athlete> list(PageableDto input, AthleteInput.Filter filter) {
        Pageable pageable = PageRequest.of(input.getPage(), input.getSize(), input.getDirection(), "since");  // TODO Ajustar properties
        return repository.findAll(pageable);
    }

    @Override
    public boolean delete(long code) {
        final Person person = personService.findByCodeThrowsException(code);
        final Athlete athlete = person.getAthlete();
        if (athlete == null) {
            return true;
        }

        repository.delete(athlete);
        return true;
    }

    @Override
    public Page<Athlete> findAvailableAthletesToGraduation(Graduation graduation, PageableDto input) {
        final Pageable pageable = PageRequest.of(input.getPage(), input.getSize(), input.getDirection(), "person.name");
        return customRepository.findAvailableAthletesToGraduation(graduation, pageable);
    }
}