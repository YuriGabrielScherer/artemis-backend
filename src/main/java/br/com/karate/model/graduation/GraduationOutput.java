package br.com.karate.model.graduation;


import br.com.karate.enums.EnumGraduationSituation;
import br.com.karate.model.graduation.history.GraduationHistoryOutput;

import java.time.LocalDate;
import java.util.List;

public class GraduationOutput {

    public static class Dto {
        public long code;
        public String title;
        public String description;
        public String place;
        public LocalDate date;

        public EnumGraduationSituation situation;

        public List<GraduationHistoryOutput.Dto> history;
    }
}
