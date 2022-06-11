package br.com.karate.converter;

import br.com.karate.converter.abstracts.AbstractConverter;
import br.com.karate.model.person.Person;
import br.com.karate.model.professor.Professor;
import br.com.karate.model.professor.ProfessorInput;
import br.com.karate.model.professor.ProfessorOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfessorConverter implements AbstractConverter<Professor, ProfessorInput.Save, ProfessorOutput.Dto, ProfessorInput.Filter> {

    @Autowired
    private PersonConverter personConverter;

    @Override
    public Professor toEntity(ProfessorInput.Save input) {
        final Professor output = new Professor();
        final Person person = new Person();
        person.setCode(input.code);
        output.setPerson(person);

        return output;
    }

    @Override
    public ProfessorOutput.Dto toDto(Professor entity) {
        final ProfessorOutput.Dto dto = new ProfessorOutput.Dto();
        dto.person = personConverter.toDto(entity.getPerson());
        return dto;
    }

    @Override
    public List<ProfessorOutput.Dto> toDto(List<Professor> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public ProfessorOutput.Dto toSimpleDto(Professor entity) {
        final ProfessorOutput.Dto dto = new ProfessorOutput.Dto();
        dto.person = personConverter.toSimpleDto(entity.getPerson());
        return dto;
    }

    @Override
    public List<ProfessorOutput.Dto> toSimpleDto(List<Professor> entities) {
        return entities.stream().map(this::toSimpleDto).collect(Collectors.toList());
    }

    @Override
    public ProfessorInput.Filter toFilter(String filterInput) {
        return null;
    }
}
