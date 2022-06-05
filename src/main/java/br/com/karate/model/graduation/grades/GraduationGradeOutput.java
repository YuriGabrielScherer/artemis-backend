package br.com.karate.model.graduation.grades;

import br.com.karate.model.athlete.AthleteOutput;
import br.com.karate.model.professor.ProfessorOutput;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

public class GraduationGradeOutput {

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Dto {
        public AthleteOutput.Dto athlete;
        public List<ProfessorGradeDto> grades;
    }

    public static class ProfessorGradeDto {
        public ProfessorOutput.Dto professor;
        public Double grade;
        public String description;
    }
}
