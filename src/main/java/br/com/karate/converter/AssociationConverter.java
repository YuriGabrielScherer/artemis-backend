package br.com.karate.converter;

import br.com.karate.converter.abstracts.AbstractConverter;
import br.com.karate.model.association.Association;
import br.com.karate.model.association.AssociationInput;
import br.com.karate.model.association.AssociationOutput;
import br.com.karate.model.person.Person;
import br.com.karate.model.person.PersonOutput;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class AssociationConverter implements AbstractConverter<Association, AssociationInput.Save, AssociationOutput.Dto, AssociationInput.Save> {
    @Override
    public Association toEntity(AssociationInput.Save input) {
        final Association output = new Association();

        output.setCode(input.code);
        output.setName(input.name);
        output.setCity(input.city);
        final Person manager = new Person();
        manager.setCode(input.managerCode);
        output.setManager(manager);

        if (input.since != null) {
            output.setSince(input.since);
        }

        return output;
    }

    @Override
    public AssociationOutput.Dto toDto(Association input) {
        if (input == null) {
            return null;
        }

        final AssociationOutput.Dto dto = new AssociationOutput.Dto();

        dto.code = input.getCode();
        dto.name = input.getName();
        dto.city = input.getCity();
        dto.since = input.getSince();

        if (input.getManager() != null) {
            final PersonOutput.Dto managerDto = new PersonOutput.Dto();
            managerDto.code = input.getManager().getCode();
            managerDto.name = input.getManager().getName();
            managerDto.document = input.getManager().getDocument();

            dto.manager = managerDto;
        }
        return dto;
    }

    @Override
    public List<AssociationOutput.Dto> toDto(List<Association> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public AssociationOutput.Dto toSimpleDto(Association input) {
        Association association = input;
        association.setManager(null);
        return toDto(association);
    }

    @Override
    public List<AssociationOutput.Dto> toSimpleDto(List<Association> entities) {
        return entities.stream().map(this::toSimpleDto).collect(Collectors.toList());
    }

    @Override
    public AssociationInput.Save toFilter(String filterInput) {
        return null;
    }

}
