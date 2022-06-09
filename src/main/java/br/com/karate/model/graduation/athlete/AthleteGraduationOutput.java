package br.com.karate.model.graduation.athlete;

import br.com.karate.enums.EnumAthleteGraduationSituation;
import br.com.karate.enums.EnumBelt;
import br.com.karate.model.athlete.AthleteOutput;
import br.com.karate.model.graduation.GraduationOutput;
import br.com.karate.model.graduation.grades.GraduationGrade;
import br.com.karate.model.graduation.grades.GraduationGradeOutput;

import java.util.List;

public class AthleteGraduationOutput {
    public static class Dto {
        public EnumBelt belt;
        public AthleteOutput.Dto athlete;
        public EnumAthleteGraduationSituation situation;
        public double grade;
    }

    public static class AthleteDto {

        public GraduationOutput.Dto graduation;
        public EnumBelt belt;
        public EnumAthleteGraduationSituation situation;
        public double grade;
        public List<GraduationGradeOutput.ProfessorGradeDto> gradeDetail;
    }
}
