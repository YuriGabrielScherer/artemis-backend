package br.com.karate.converter;

import br.com.karate.model.graduation.athlete.AthleteGraduation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.karate.model.graduation.athlete.AthleteGraduationOutput.Dto;


@Component
public class AthleteGraduationConverter {

    @Autowired
    private AthleteConverter athleteConverter;

    public Dto toDto(AthleteGraduation entity) {
        final Dto dto = new Dto();

        dto.athlete = athleteConverter.toDto(entity.getAthlete());
        dto.belt = entity.getBelt().getBelt();
        if (entity.getGrade() != null) {
            dto.grade = entity.getGrade();
        }
        dto.situation = entity.getSituation();

        return dto;
    }

    public List<Dto> toDto(List<AthleteGraduation> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

}
