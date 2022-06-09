package br.com.karate.converter;

import br.com.karate.converter.abstracts.AbstractConverter;
import br.com.karate.model.graduation.Graduation;
import br.com.karate.model.graduation.GraduationOutput;
import br.com.karate.model.graduation.history.GraduationHistory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static br.com.karate.model.graduation.GraduationInput.Filter;
import static br.com.karate.model.graduation.GraduationInput.Save;

@Component
public class GraduationConverter implements AbstractConverter<Graduation, Save, GraduationOutput.Dto, Filter> {

    @Autowired
    private GraduationHistoryConverter historyConverter;

    @Override
    public Graduation toEntity(Save input) {
        final Graduation graduation = new Graduation();

        graduation.setTitle(input.title);
        graduation.setDescription(input.description);
        graduation.setPlace(input.place);
        graduation.setCode(input.code);
        graduation.setDate(input.date);

        return graduation;
    }

    @Override
    public GraduationOutput.Dto toDto(Graduation entity) {
        if (entity == null) {
            return null;
        }

        final GraduationOutput.Dto dto = toSimpleDto(entity);

        final Set<GraduationHistory> history = entity.getHistory();
        dto.history = historyConverter.toDto(history.stream().collect(Collectors.toList()));

        return dto;
    }

    @Override
    public List<GraduationOutput.Dto> toDto(List<Graduation> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public GraduationOutput.Dto toSimpleDto(Graduation entity) {
        if (entity == null) {
            return null;
        }
        final GraduationOutput.Dto dto = new GraduationOutput.Dto();
        dto.code = entity.getCode();
        dto.title = entity.getTitle();
        dto.description = entity.getDescription();
        dto.place = entity.getPlace();
        dto.date = entity.getDate();
        dto.situation = entity.getSituation().getSituation();

        return dto;
    }

    @Override
    public List<GraduationOutput.Dto> toSimpleDto(List<Graduation> entity) {
        return toDto(entity);
    }

    @Override
    public Filter toFilter(String filterInput) throws JsonProcessingException {
        if (filterInput == null || filterInput.length() == 0) {
            return new Filter();
        }

        final Filter output = new ObjectMapper().readValue(filterInput, Filter.class);
        return output;
    }
}
