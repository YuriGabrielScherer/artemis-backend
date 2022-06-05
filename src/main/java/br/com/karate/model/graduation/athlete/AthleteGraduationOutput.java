package br.com.karate.model.graduation.athlete;

import br.com.karate.enums.EnumAthleteGraduationSituation;
import br.com.karate.enums.EnumBelt;
import br.com.karate.model.athlete.AthleteOutput;

public class AthleteGraduationOutput {
    public static class Dto {
        public EnumBelt belt;
        public AthleteOutput.Dto athlete;
        public EnumAthleteGraduationSituation situation;
        public double grade;
    }
}
