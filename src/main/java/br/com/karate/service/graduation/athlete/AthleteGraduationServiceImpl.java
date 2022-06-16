package br.com.karate.service.graduation.athlete;

import br.com.karate.enums.EnumAthleteGraduationSituation;
import br.com.karate.enums.EnumBelt;
import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.belt.Belt;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.athlete.AthleteGraduation;
import br.com.karate.model.util.pageable.PageableDto;
import br.com.karate.repository.graduation.AthleteGraduationRepository;
import br.com.karate.service.belt.BeltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class AthleteGraduationServiceImpl implements AthleteGraduationService {

    @Autowired
    private AthleteGraduationRepository repository;
    @Autowired
    private BeltService beltService;

    @Override
    public AthleteGraduation save(Graduation graduation, Athlete athlete, EnumAthleteGraduationSituation situation) {

        validateAthleteCanBeRegistered(athlete);

        AthleteGraduation athleteGraduation = repository.findByAthleteAndGraduation(athlete, graduation).orElse(null);
        if (athleteGraduation == null) {
            athleteGraduation = new AthleteGraduation();
            athleteGraduation.setAthlete(athlete);
            athleteGraduation.setGraduation(graduation);

            LocalDate lastAthleteGraduation = null;
            EnumBelt lastBelt = null;

            final AthleteGraduation lastGraduation = getLastAthleteGraduationApproved(athlete);
            if (lastGraduation == null || lastGraduation.getGraduation() == null) {
                lastAthleteGraduation = athlete.getSince();
                lastBelt = EnumBelt.WHITE;
            } else {
                lastAthleteGraduation = lastGraduation.getGraduation().getDate();
                lastBelt = lastGraduation.getBelt().getBelt();
            }

            final Belt belt = beltService.validateCanGraduateAndReturnNextBelt(lastBelt, lastAthleteGraduation, graduation.getDate());
            athleteGraduation.setBelt(belt);
        }

        athleteGraduation.setSituation(situation);

        return repository.save(athleteGraduation);
    }

    @Override
    public AthleteGraduation findByGraduationAndAthleteCode(Graduation graduation, Long code) {
        return repository.findByGraduationAndAthletePersonCode(graduation, code);
    }

    @Override
    public Page<AthleteGraduation> findByAthlete(Athlete athlete, PageableDto pageableDto) {
        return repository.findByAthlete(athlete, pageableDto.getPageable());
    }

    @Override
    public Page<AthleteGraduation> findByGraduation(Graduation graduation, PageableDto pageableDto) {
        return repository.findByGraduation(graduation, pageableDto.getPageable());
    }

    @Override
    public void setAthleteGrade(Graduation graduation, Athlete athlete, double grade) {
        final AthleteGraduation athleteGraduation = repository.findByAthleteAndGraduation(athlete, graduation).orElse(null);
        if (athleteGraduation == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Nota sendo atribuída a um atleta não vinculado a alguma exame");
        }

        if (grade >= 7) {
            athleteGraduation.setSituation(EnumAthleteGraduationSituation.APPROVED);
        } else if (grade > 0) {
            athleteGraduation.setSituation(EnumAthleteGraduationSituation.DISAPPROVED);
        } else {
            athleteGraduation.setSituation(EnumAthleteGraduationSituation.MISSING);
        }

        athleteGraduation.setGrade(grade);
        repository.save(athleteGraduation);
    }

    private void validateAthleteCanBeRegistered(Athlete athlete) {
        final AthleteGraduation athleteGraduation = repository.findByAthleteAndSituation(athlete, EnumAthleteGraduationSituation.REGISTERED).orElse(null);

        if (athleteGraduation != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Atleta já está vinculado a outro exame de graduação.");
        }
    }

    private AthleteGraduation getLastAthleteGraduationApproved(Athlete athlete) {
        return repository.findFirstByAthleteAndSituationAndGraduationNotNullOrderByGraduationDateDesc(athlete, EnumAthleteGraduationSituation.APPROVED);
    }

}
