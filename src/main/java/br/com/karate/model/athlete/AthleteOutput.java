package br.com.karate.model.athlete;

import br.com.karate.enums.EnumBelt;
import br.com.karate.model.person.PersonOutput;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

public class AthleteOutput {

    @EqualsAndHashCode
    public static class Dto {
        public LocalDate since;
        public EnumBelt currentBelt;
        public PersonOutput.Dto person;
    }

}
