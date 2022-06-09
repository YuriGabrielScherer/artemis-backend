package br.com.karate.converter;

import br.com.karate.converter.abstracts.SimpleConverter;
import br.com.karate.model.graduation.athlete.AthleteGraduation;
import br.com.karate.model.graduation.grades.GraduationGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.karate.model.graduation.athlete.AthleteGraduationOutput.AthleteDto;
import static br.com.karate.model.graduation.athlete.AthleteGraduationOutput.Dto;


@Component
public class AthleteGraduationConverter implements SimpleConverter<AthleteGraduation, Dto> {

    @Autowired
    private AthleteConverter athleteConverter;

    @Autowired
    private GraduationConverter graduationConverter;

    @Autowired
    private GraduationGradeConverter gradeConverter;

    @Override
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

    public AthleteDto toAthleteDto(AthleteGraduation input, List<GraduationGrade> grades) {
        final AthleteDto dto = new AthleteDto();
        dto.grade = input.getGrade();
        dto.situation = input.getSituation();
        dto.belt = input.getBelt().getBelt();
        dto.graduation = graduationConverter.toDto(input.getGraduation());
        dto.gradeDetail = new ArrayList<>();
        for (GraduationGrade grade : grades) {
            dto.gradeDetail.add(gradeConverter.toProfessorGradeDto(grade));
        }

        return dto;
    }

    public List<AthleteDto> toAthleteDto(List<AthleteGraduation> graduations, List<GraduationGrade> grades) {
        List<AthleteDto> output = new ArrayList<>();
        for (AthleteGraduation athleteGraduation : graduations) {
            final List<GraduationGrade> graduationGrade = grades.stream().filter(grade -> grade.getGraduation() == athleteGraduation.getGraduation()).collect(Collectors.toList());
            output.add(toAthleteDto(athleteGraduation, graduationGrade));
        }
        return output;
    }


}
