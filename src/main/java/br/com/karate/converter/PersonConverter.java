package br.com.karate.converter;

import br.com.karate.converter.abstracts.AbstractConverter;
import br.com.karate.model.association.Association;
import br.com.karate.model.person.Person;
import br.com.karate.model.person.PersonOutput;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.karate.model.person.PersonInput.Filter;
import static br.com.karate.model.person.PersonInput.Save;

@Component
public class PersonConverter implements AbstractConverter<Person, Save, PersonOutput.Dto, Filter> {

    @Autowired
    private AssociationConverter associationConverter;

    @Override
    public PersonOutput.Dto toDto(Person input) {

        if (input == null) {
            return null;
        }

        final PersonOutput.Dto dto = new PersonOutput.Dto();

        dto.code = input.getCode();
        dto.name = input.getName();
        dto.birth = input.getBirth();
        dto.gender = input.getGender();
        dto.document = input.getDocument();
        if (input.getAssociation() != null) {
            dto.association = associationConverter.toSimpleDto(input.getAssociation());
        }

        return dto;
    }

    @Override
    public Person toEntity(Save input) {
        final Person output = new Person();

        output.setCode(input.code);
        output.setName(input.name);
        output.setGender(input.gender);
        output.setDocument(input.document);
        output.setBirth(input.birth);
        if (input.associationCode > 0) {
            final Association association = new Association();
            association.setCode(input.associationCode);
            output.setAssociation(association);
        }

        return output;
    }

    @Override
    public List<PersonOutput.Dto> toDto(List<Person> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public PersonOutput.Dto toSimpleDto(Person input) {
        Person person = input;
        person.setAssociation(null);
        return toDto(person);
    }

    @Override
    public List<PersonOutput.Dto> toSimpleDto(List<Person> entities) {
        return entities.stream().map(this::toSimpleDto).collect(Collectors.toList());
    }

    @Override
    public Filter toFilter(String filterInput) throws JsonProcessingException {
        if (filterInput == null || filterInput.length() == 0) {
            return new Filter();
        }

        final Filter filter = new ObjectMapper().readValue(filterInput, Filter.class);
        return filter;
    }


}
