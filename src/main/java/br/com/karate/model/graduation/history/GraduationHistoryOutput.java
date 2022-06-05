package br.com.karate.model.graduation.history;

import br.com.karate.enums.EnumGraduationSituation;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GraduationHistoryOutput {

    public static class Dto {
        public LocalDateTime date;
        public EnumGraduationSituation situation;
    }

}
