package br.com.karate.converter;

import static br.com.karate.model.athlete.AthleteInput.*;

import br.com.karate.converter.abstracts.AbstractConverter;
import br.com.karate.model.athlete.Athlete;
import br.com.karate.model.athlete.AthleteOutput;
import br.com.karate.model.person.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AthleteConverter implements AbstractConverter<Athlete, Save, AthleteOutput.Dto, Filter> {

    @Autowired
    private PersonConverter personConverter;

    @Override
    public Athlete toEntity(Save input) {
        final Athlete athlete = new Athlete();
        final Person person = new Person();
        person.setCode(input.code);
        athlete.setPerson(person);
        athlete.setSince(input.since);
        return athlete;
    }

    @Override
    public AthleteOutput.Dto toDto(Athlete athlete) {
        AthleteOutput.Dto dto = new AthleteOutput.Dto();

        dto.since = athlete.getSince();
        dto.belt = athlete.getCurrentBelt().getBelt();

        if (athlete.getPerson() != null) {
            dto.person = personConverter.toDto(athlete.getPerson());
        }

        return dto;
    }

    @Override
    public List<AthleteOutput.Dto> toDto(List<Athlete> input) {
        return input.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public AthleteOutput.Dto toSimpleDto(Athlete input) {
        final Athlete athlete = input;
        athlete.getPerson().setAssociation(null);
        return toDto(athlete);
    }

    @Override
    public List<AthleteOutput.Dto> toSimpleDto(List<Athlete> entities) {
        return entities.stream().map(this::toSimpleDto).collect(Collectors.toList());
    }

    @Override
    public Filter toFilter(String filterInput) throws JsonProcessingException {
        final Filter filter = new ObjectMapper().readValue(filterInput, Filter.class);
        return filter;
    }


}
