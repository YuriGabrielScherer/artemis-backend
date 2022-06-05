package br.com.karate.model.professor;

import br.com.karate.model.person.PersonInput;
import br.com.karate.model.util.controller.input.FilterInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

public class ProfessorInput {

    public static class Save {

    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Filter extends FilterInput {
        public LocalDate since;
        public PersonInput.Filter person;
    }

}
