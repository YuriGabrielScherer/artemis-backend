package br.com.karate.converter;

import br.com.karate.converter.abstracts.SimpleConverter;
import br.com.karate.model.graduation.history.GraduationHistory;
import org.springframework.stereotype.Component;

import static br.com.karate.model.graduation.history.GraduationHistoryOutput.Dto;

@Component
public class GraduationHistoryConverter implements SimpleConverter<GraduationHistory, Dto> {

    @Override
    public Dto toDto(GraduationHistory entity) {
        final Dto output = new Dto();
        output.date = entity.getCreatedDate();
        output.situation = entity.getSituation();
        return output;
    }
}
