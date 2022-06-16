package br.com.karate.model.athlete;

import br.com.karate.enums.EnumBelt;
import br.com.karate.model.person.PersonInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AthleteInput {

    public static class Save {
        @NotNull(message = "Código da Pessoa é obrigatório.")
        @Min(value=1,message = "Código da Pessoa inválido.")
        public long code;

        @NotNull(message = "Data de início é obrigatória.")
        public LocalDate since;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Filter {
        public LocalDate since;
        public EnumBelt belt;
        public PersonInput.Filter person;
    }

}
