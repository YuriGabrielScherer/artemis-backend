package br.com.karate.converter;

import br.com.karate.model.athlete.AthleteOutput;
import br.com.karate.model.graduation.grades.GraduationGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.com.karate.model.graduation.grades.GraduationGradeOutput.Dto;
import static br.com.karate.model.graduation.grades.GraduationGradeOutput.ProfessorGradeDto;

@Component
public class GraduationGradeConverter {

    @Autowired
    private AthleteConverter athleteConverter;

    @Autowired
    private ProfessorConverter professorConverter;

    public List<Dto> toDto(List<GraduationGrade> entities) {
        final Map<AthleteOutput.Dto, List<ProfessorGradeDto>> map = new HashMap<>();

        for (GraduationGrade graduation : entities) {
            final AthleteOutput.Dto athleteDto = athleteConverter.toSimpleDto(graduation.getAthlete());
            if (map.containsKey(athleteDto)) {
                map.get(athleteDto).add(toProfessorGradeDto(graduation));
            } else {
                final List<ProfessorGradeDto> listTemp = new ArrayList<>();
                listTemp.add(toProfessorGradeDto(graduation));
                map.put(athleteDto, listTemp);
            }
        }

        final List<Dto> output = new ArrayList<>();
        map.forEach((key, value) -> output.add(new Dto(key, value)));

        return output;
    }

    private ProfessorGradeDto toProfessorGradeDto(GraduationGrade grade) {
        final ProfessorGradeDto dto = new ProfessorGradeDto();

        dto.grade = grade.getGrade();
        dto.description = grade.getDescription();
        dto.professor = professorConverter.toSimpleDto(grade.getProfessor());

        return dto;
    }

}
