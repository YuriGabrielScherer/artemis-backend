package br.com.karate.model.professor;

import br.com.karate.model.person.PersonInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ProfessorInput {

    public static class Save {
        @NotNull(message = "Código da Pessoa é obrigatório.")
        @Min(value=1,message = "Código da Pessoa inválido.")
        public long code;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Filter {
        public LocalDate since;
        public PersonInput.Filter person;
    }

}
